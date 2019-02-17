package online.gettrained.backend.services.blob;

import static online.gettrained.backend.utils.BlobDataUtils.contentTypeToBlobType;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import online.gettrained.backend.domain.blob.BlobData;
import online.gettrained.backend.domain.blob.BlobData.Type;
import online.gettrained.backend.dto.BlobDataDto;
import online.gettrained.backend.props.ApplicationCommonProperties;
import online.gettrained.backend.repositories.blob.BlobDataRepository;
import online.gettrained.backend.utils.CommonUtils;
import online.gettrained.backend.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
@Service
public class BlobDataServiceImpl implements BlobDataService {

  private static final Logger LOG = LoggerFactory.getLogger(BlobDataServiceImpl.class);

  private static final long IMAGE_SIZE_SHOULD_COMPRESS = 1000000;

  private final ApplicationContext applicationContext;
  private final ApplicationCommonProperties applicationCommonProperties;
  private final BlobDataRepository blobDataRepository;

  public BlobDataServiceImpl(ApplicationContext applicationContext,
      ApplicationCommonProperties applicationCommonProperties,
      BlobDataRepository blobDataRepository) {
    this.applicationContext = applicationContext;
    this.applicationCommonProperties = applicationCommonProperties;
    this.blobDataRepository = blobDataRepository;
  }

  @Override
  public List<BlobData> rowMediaToBlobMedia(String rowMediaName, List<byte[]> rowMediaData) {
    return prepare(
        rowMediaData.stream().map(d -> new MultipartFile() {
          @Override
          public String getName() {
            return rowMediaName;
          }

          @Override
          public String getOriginalFilename() {
            return rowMediaName;
          }

          @Override
          public String getContentType() {
            return null;
          }

          @Override
          public boolean isEmpty() {
            return false;
          }

          @Override
          public long getSize() {
            return d.length;
          }

          @Override
          public byte[] getBytes() {
            return d;
          }

          @Override
          public InputStream getInputStream() {
            return null;
          }

          @Override
          public void transferTo(File dest) throws IllegalStateException {

          }
        }).collect(Collectors.toList()),
        true,
        false);
  }

  @Override
  public List<BlobData> prepare(List<MultipartFile> files, Boolean extractMd,
      boolean publicAccess) {
    return files.stream().map(f -> {
      BlobData blobData = new BlobData();
      blobData.setName(f.getOriginalFilename() != null ? f.getOriginalFilename() : f.getName());
      String contentType = f.getContentType();
      Type type = null;
      byte[] data;
      try {
        data = f.getBytes();
      } catch (IOException e) {
        throw new RuntimeException("Error reading bytes data from multipart file.");
      }
      if (contentType != null) {
        type = contentTypeToBlobType(blobData.getName(), f.getContentType());
      }

      blobData.setOwner(SecurityUtils.getCurrentUser());
      blobData.setSize((long) data.length);
      blobData.setContentType(contentType);
      blobData.setType(type);
      blobData.setExtractMetaData(extractMd);
      blobData.setPublicAccess(publicAccess);
      if (blobData.getType() == BlobData.Type.IMAGE) {
        try {
          BufferedImage bufferedImage = getBufferedImage(data);
          if (f.getSize() > IMAGE_SIZE_SHOULD_COMPRESS) {
            try {
              data = compressImage(bufferedImage, f.getContentType(), f.getSize());
              blobData.setSize((long) data.length);
              blobData.setHeight(bufferedImage.getHeight());
              blobData.setWidth(bufferedImage.getWidth());
            } catch (Exception ex) {
              LOG.error("Error compressing the image file", ex);
              data = f.getBytes();
            }
          }
        } catch (Exception e) {
          LOG.error("Error saving file with name " + f.getOriginalFilename(), e);
        }
      }
      blobData.setObject(data);
      return blobData;
    }).collect(Collectors.toList());
  }

  @Override
  public List<BlobDataDto> save(List<MultipartFile> files, Boolean extractMd) {
    return save(files, extractMd, false);
  }

  @Override
  public List<BlobDataDto> save(File file, String contentType, boolean extractMd) {
    Objects.requireNonNull(file, "Parameter 'file' must be set!");

    MultipartFile multipartFile = new MultipartFile() {
      @Override
      public String getName() {
        return file.getName();
      }

      @Override
      public String getOriginalFilename() {
        return file.getName();
      }

      @Override
      public String getContentType() {
        return contentType;
      }

      @Override
      public boolean isEmpty() {
        return false;
      }

      @Override
      public long getSize() {
        return file.length();
      }

      @Override
      public byte[] getBytes() throws IOException {
        return Files.readAllBytes(Paths.get(file.toURI()));
      }

      @Override
      public InputStream getInputStream() {
        return null;
      }

      @Override
      public void transferTo(File dest) throws IllegalStateException {

      }
    };

    return save(CommonUtils.immutableListOf(multipartFile), extractMd);
  }

  @Override
  public List<BlobDataDto> save(List<MultipartFile> files, Boolean extractMd,
      boolean publicAccess) {
    long start = System.currentTimeMillis();

    List<BlobData> blobDataList = prepare(files, extractMd, publicAccess);
    try {
      List<BlobDataDto> blobDataDtoList = getSpringProxy().save(blobDataList);

      LOG.debug("The file(s) was uploaded for {}sec.",
          (System.currentTimeMillis() - start) / 1000.0);

      return blobDataDtoList;
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  @Transactional
  public List<BlobDataDto> save(List<BlobData> blobDataList) {
    if (blobDataList == null || blobDataList.size() == 0) {
      return Collections.emptyList();
    }

    return blobDataRepository.saveAll(blobDataList)
        .stream()
        .map(b ->
            new BlobDataDto(
                b.getId(),
                b.getName(),
                b.getSize(),
                b.getType(),
                b.getContentType(),
                getFileUrl(b.getId()),
                b.getWidth(),
                b.getHeight())
        )
        .collect(Collectors.toList());
  }

  private BufferedImage getBufferedImage(byte[] input) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(input);
    //Create the buffered image
    BufferedImage bufferedImage = ImageIO.read(inputStream);
    inputStream.close();
    return bufferedImage;
  }

  private byte[] compressImage(BufferedImage bufferedImage, String MIMEType, long size)
      throws IOException {

    long start = System.currentTimeMillis();

    float imageQuality = 1.0F * IMAGE_SIZE_SHOULD_COMPRESS / size;

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    //Get image writers
    Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType(MIMEType);

    if (!imageWriters.hasNext()) {
      throw new IllegalStateException("Writers Not Found!!");
    }

    ImageWriter imageWriter = imageWriters.next();
    ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
    imageWriter.setOutput(imageOutputStream);

    ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

    //Set the compress quality metrics
    imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    imageWriteParam.setCompressionQuality(imageQuality);

    //Created image
    imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);

    // close all streams
    outputStream.close();
    imageOutputStream.close();
    imageWriter.dispose();

    LOG.info("The image was compressed for {}s", (System.currentTimeMillis() - start) / 1000.0);

    return outputStream.toByteArray();
  }

  @Override
  public Optional<BlobData> findBlobDataWithoutBlobObjectById(long blobId) {
    Object[][] blobDataObject = blobDataRepository.findOneWithoutBlob(blobId);
    if (blobDataObject != null
        && blobDataObject.length > 0
        && blobDataObject[0] != null
        && blobDataObject[0][0] != null) {
      BlobData blobData = new BlobData();
      blobData.setId((Long) blobDataObject[0][0]);
      blobData.setDateCreate((Date) blobDataObject[0][1]);
      blobData.setDateUpdate((Date) blobDataObject[0][2]);
      blobData.setName((String) blobDataObject[0][3]);
      blobData.setSize((Long) blobDataObject[0][4]);
      blobData.setType((Type) blobDataObject[0][5]);
      blobData.setContentType((String) blobDataObject[0][6]);
      blobData.setVersion((Integer) blobDataObject[0][7]);
      blobData.setKeyInStorage((String) blobDataObject[0][8]);
      return Optional.of(blobData);
    } else {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public Optional<BlobData> findBlobDataById(long blobId) {
    return blobDataRepository.findById(blobId);
  }

  /**
   * Delete a blob object from a database.
   */
  @Override
  @Transactional
  public void deleteBlobDataById(Long blobId) {
    LOG.debug("Try deleting the blobData by id:{}", blobId);
    Optional<BlobData> blobData = findBlobDataWithoutBlobObjectById(blobId);
    if (blobData.isPresent()) {
      blobDataRepository.deleteById(blobId);
    }
  }

  @Override
  public List<BlobData> findAll() {
    List<Object[]> data = blobDataRepository.findAllWithoutBlob();
    return data.stream().map(d -> {
      BlobData blobData = new BlobData();
      blobData.setId((Long) d[0]);
      blobData.setDateCreate((Date) d[1]);
      blobData.setDateUpdate((Date) d[2]);
      blobData.setName((String) d[3]);
      blobData.setSize((Long) d[4]);
      blobData.setType((BlobData.Type) d[5]);
      blobData.setContentType((String) d[6]);
      blobData.setVersion((Integer) d[7]);
      return blobData;
    }).collect(Collectors.toList());
  }

  @Override
  public String getFileUrl(long fileId) {
    return COMMON_PATH + "/" + fileId;
  }

  @Override
  public Optional<Long> getBlobIdByUrl(String fileUrl) {
    if (fileUrl != null && !fileUrl.isEmpty()) {
      try {
        Long id = Long.parseLong(fileUrl.substring(fileUrl.lastIndexOf("/") + 1));
        Optional<BlobData> blobData = findBlobDataWithoutBlobObjectById(id);
        if (blobData.isPresent()) {
          return Optional.of(blobData.get().getId());
        }
      } catch (Exception ex) {
        //ignore it
      }
    }
    return Optional.empty();
  }

  @Override
  public Type getFileType(Long audioId) {
    return blobDataRepository.getType(audioId);
  }

  /**
   * Use proxy to hit transaction.
   */
  private BlobDataService getSpringProxy() {
    return applicationContext.getBean(BlobDataService.class);
  }
}
