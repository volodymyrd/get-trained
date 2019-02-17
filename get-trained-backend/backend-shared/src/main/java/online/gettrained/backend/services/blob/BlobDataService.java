package online.gettrained.backend.services.blob;

import java.io.File;
import java.util.List;
import java.util.Optional;
import online.gettrained.backend.domain.blob.BlobData;
import online.gettrained.backend.domain.blob.BlobData.Type;
import online.gettrained.backend.dto.BlobDataDto;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
public interface BlobDataService {

  String COMMON_PATH = "common/utils/file/get";
  String COMMON_PATH_FOR_FULL_FILE = "common/utils/fullFile/get";

  List<BlobData> rowMediaToBlobMedia(String rowMediaName, List<byte[]> rowMediaData);

  List<BlobData> prepare(List<MultipartFile> files, Boolean extractMd, boolean publicAccess);

  List<BlobDataDto> save(List<MultipartFile> files, Boolean extractMd);

  List<BlobDataDto> save(File file, String contentType, boolean extractMd);

  List<BlobDataDto> save(List<MultipartFile> files, Boolean extractMd, boolean publicAccess);

  List<BlobDataDto> save(List<BlobData> blobDataList);

  Optional<BlobData> findBlobDataWithoutBlobObjectById(long blobId);

  Optional<BlobData> findBlobDataById(long blobId);

  void deleteBlobDataById(Long blobId);

  List<BlobData> findAll();

  String getFileUrl(long fileId);

  Optional<Long> getBlobIdByUrl(String fileUrl);

  Type getFileType(Long audioId);
}
