package online.gettrained.backend.dto;

import java.io.Serializable;
import online.gettrained.backend.domain.blob.BlobData.Type;

/**
 * Blob data dto.
 */
public final class BlobDataDto implements Serializable {

  private static final long serialVersionUID = 3941028518346557027L;

  private final Long id;
  private final String name;
  private final Long size;
  private final Type type;
  private final String contentType;
  private final String url;
  private final Integer width;
  private final Integer height;
  private final String keyInStorage;
  private final String samplesStorageKey;
  private final String leftSamplesStorageKey;
  private final String rightSamplesStorageKey;

  public BlobDataDto(
      Long id,
      String name,
      Long size,
      Type type,
      String contentType,
      String url,
      Integer width,
      Integer height,
      String keyInStorage,
      String samplesStorageKey,
      String leftSamplesStorageKey,
      String rightSamplesStorageKey) {
    this.id = id;
    this.name = name;
    this.size = size;
    this.type = type;
    this.contentType = contentType;
    this.url = url;
    this.width = width;
    this.height = height;
    this.keyInStorage = keyInStorage;
    this.samplesStorageKey = samplesStorageKey;
    this.leftSamplesStorageKey = leftSamplesStorageKey;
    this.rightSamplesStorageKey = rightSamplesStorageKey;
  }

  public BlobDataDto(
      Long id,
      String name,
      Long size,
      Type type,
      String contentType,
      String url,
      Integer width,
      Integer height) {
    this(id,
        name,
        size,
        type,
        contentType,
        url,
        width,
        height,
        null,
        null,
        null,
        null);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getSize() {
    return size;
  }

  public Type getType() {
    return type;
  }

  public String getContentType() {
    return contentType;
  }

  public String getUrl() {
    return url;
  }

  public Integer getWidth() {
    return width;
  }

  public Integer getHeight() {
    return height;
  }


  public String getKeyInStorage() {
    return keyInStorage;
  }

  public String getSamplesStorageKey() {
    return samplesStorageKey;
  }

  public String getLeftSamplesStorageKey() {
    return leftSamplesStorageKey;
  }

  public String getRightSamplesStorageKey() {
    return rightSamplesStorageKey;
  }
}
