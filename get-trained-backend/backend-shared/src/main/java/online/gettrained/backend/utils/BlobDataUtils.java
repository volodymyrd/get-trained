package online.gettrained.backend.utils;

import static online.gettrained.backend.domain.blob.BlobData.Type.AUDIO;
import static online.gettrained.backend.domain.blob.BlobData.Type.EXCEL;
import static online.gettrained.backend.domain.blob.BlobData.Type.IMAGE;
import static online.gettrained.backend.domain.blob.BlobData.Type.PDF;
import static online.gettrained.backend.domain.blob.BlobData.Type.POWERPOINT;
import static online.gettrained.backend.domain.blob.BlobData.Type.TXT;
import static online.gettrained.backend.domain.blob.BlobData.Type.UNDEFINED;
import static online.gettrained.backend.domain.blob.BlobData.Type.VIDEO;
import static online.gettrained.backend.domain.blob.BlobData.Type.WORD;

import online.gettrained.backend.domain.blob.BlobData.Type;

/**
 * Utils
 */
public final class BlobDataUtils {

  public static Type contentTypeToBlobType(String fileName, String contentType) {
    if (contentType.startsWith("text")) {
      return TXT;
    }

    if ("application/pdf".equals(contentType)) {
      return PDF;
    }

    if (contentType.startsWith("image")) {
      return IMAGE;
    }

    if (contentType.startsWith("video")) {
      return VIDEO;
    }

    if (contentType.startsWith("audio")) {
      return AUDIO;
    }

    if ("application/msword".equals(contentType)
        || "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        .equals(contentType)) {
      return WORD;
    }

    if ("application/vnd.ms-excel".equals(contentType)
        || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        .equals(contentType)) {
      return EXCEL;
    }

    if ("application/vnd.ms-powerpoint".equals(contentType)
        || "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        .equals(contentType)
        || "application/vnd.openxmlformats-officedocument.presentationml.slideshow"
        .equals(contentType)) {
      return POWERPOINT;
    }

    return UNDEFINED;
  }

  private BlobDataUtils() {
  }
}
