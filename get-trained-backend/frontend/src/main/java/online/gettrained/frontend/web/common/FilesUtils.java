package online.gettrained.frontend.web.common;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import online.gettrained.backend.domain.blob.BlobData;
import online.gettrained.backend.dto.BlobDataDto;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.blob.BlobDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/common/utils")
public class FilesUtils {

  private static final Logger LOG = LoggerFactory.getLogger(FilesUtils.class);

  private final AuthService authService;
  private final BlobDataService blobDataService;

  public FilesUtils(AuthService authService, BlobDataService blobDataService) {
    this.authService = authService;
    this.blobDataService = blobDataService;
  }

  /**
   * Upload a single file
   * <p>
   * Example $ curl -F file=@"Path_to_file"  http://localhost:8081/common/utils/upload/
   *
   * @param multipartFile - file
   * @return String response
   */
  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(
      @RequestParam("file") MultipartFile multipartFile,
      @RequestParam(value = "extractMd", required = false) Boolean extractMd) {

    LOG.debug("Single file upload!");

    if (multipartFile.isEmpty()) {
      return new ResponseEntity<>("Please select a file!", HttpStatus.OK);
    }

    try {
      List<BlobDataDto> dtos = blobDataService
          .save(Collections.singletonList(multipartFile), extractMd);

      if (dtos.size() == 0) {
        return ResponseEntity.badRequest().build();
      }

      return ResponseEntity.ok(dtos);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/files")
  public ResponseEntity<String> listFiles() {
    StringBuilder builder = new StringBuilder();
    builder.append("<table>");
    blobDataService.findAll().forEach(f -> {
      builder.append("<tr>");
      builder.append("<td>");
      builder.append("<a href='/common/utils/file/get/");
      builder.append(f.getId());
      builder.append("'>");
      builder.append(f.getName());
      builder.append("</a>");
      builder.append("</td>");
      builder.append("<td>");
      builder.append(f.getType());
      builder.append("</td>");
      builder.append("<td>");
      builder.append("<a href='/common/utils/file/delete/");
      builder.append(f.getId());
      builder.append("'>");
      builder.append("delete");
      builder.append("</a>");
      builder.append("</td>");
      builder.append("</tr>");
    });
    builder.append("</table>");

    return ResponseEntity.ok(builder.toString());
  }

  @GetMapping("/file/get/{id}")
  public void getFile(@PathVariable("id") Long fileId,
      HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    BlobData blobData = getBlobData(fileId);
    if (blobData == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    new MultipartFileSender(blobData, request, response).serveResource();
  }

  @GetMapping("/fullFile/get/{id}")
  public ResponseEntity<Resource> getFullFile(@PathVariable("id") Long fileId) {
    BlobData blobData = getBlobData(fileId);
    if (blobData == null) {
      return ResponseEntity.badRequest().build();
    }

    return getFullFile(blobData);
  }

  @GetMapping("/file/delete/{id}")
  public ResponseEntity<String> deleteFile(@PathVariable("id") Long fileId) {
    BlobData blobData = getBlobData(fileId);
    if (blobData == null) {
      return ResponseEntity.badRequest().build();
    }

    try {
      blobDataService.deleteBlobDataById(fileId);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>("Successfully deleted", new HttpHeaders(), HttpStatus.OK);
  }

  public static ResponseEntity<Resource> getFullFile(BlobData blobData) {
    ByteArrayResource resource = new ByteArrayResource(blobData.get());
    HttpHeaders headers = new HttpHeaders();
    //headers.add("Content-Disposition", "inline; filename=\"report.png\"");
    //headers.setContentType(MediaType.parseMediaType(blobDataOptional.get().getType()));
    return ResponseEntity.ok()
        .headers(headers)
        .contentLength(blobData.getSize())
        .contentType(MediaType.parseMediaType(blobData.getContentType()))
        .body(resource);
  }

  private BlobData getBlobData(long fileId) {
    Optional<BlobData> blobDataOptional = blobDataService.findBlobDataById(fileId);
    if (!blobDataOptional.isPresent()) {
      return null;
    }

    BlobData blobData = blobDataOptional.get();

    if (blobData.getPublicAccess() == null || !blobData.getPublicAccess()) {
      authService.getCurrentUserOrException();
    }

    return blobDataOptional.get();
  }
}
