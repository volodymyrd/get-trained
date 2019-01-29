package online.gettrained.backend.domain.blob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import online.gettrained.backend.domain.BaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 *
 */
@Entity
@Table(name = "BLOB_DATA")
public class BlobData extends BaseEntity {

  private static final long serialVersionUID = -1428362344021023872L;

  public enum Type {
    UNDEFINED, TXT, PDF, IMAGE, AUDIO, VIDEO, WORD, EXCEL, POWERPOINT, ARCHIVE;
  }

  public enum ImageType {
    jpg, png, tiff
  }

  public BlobData() {
  }

  public BlobData(String name, Long size, String contentType, byte[] transientObject) {
    this.name = name;
    this.size = size;
    this.contentType = contentType;
    this.transientObject = transientObject;
  }

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REF_USER_ID")
  private User owner;

  @Column(name = "NAME")
  private String name;

  @Column(name = "SIZE")
  private Long size;

  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE", nullable = false, length = 15)
  private Type type;

  @Column(name = "CONTENT_TYPE", nullable = false)
  private String contentType;

  @Column(name = "WIDTH")
  private Integer width;

  @Column(name = "HEIGHT")
  private Integer height;

  @Column(name = "EXTRACT_MD")
  private Boolean extractMetaData;

  @JsonIgnore
  @Lob
  @Column(name = "OBJECT")
  private byte[] object;

  @JsonIgnore
  @Column(name = "KEY_IN_STORAGE", length = 400)
  private String keyInStorage;

  /**
   * Use for sync in FileStorageCacheTasklet.
   */
  @JsonIgnore
  @Column(name = "NAME_IN_LOCAL_CACHE", length = 50)
  private String nameInLocalStorageCache;

  @Column(name = "PUBLIC_ACCESS")
  private Boolean publicAccess;

  @JsonIgnore
  @Transient
  private byte[] transientObject;

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Boolean getExtractMetaData() {
    return extractMetaData;
  }

  public void setExtractMetaData(Boolean extractMetaData) {
    this.extractMetaData = extractMetaData;
  }

  public byte[] getObject() {
    return object;
  }

  public byte[] get() {
    return object != null ? object : transientObject;
  }

  public void setObject(byte[] object) {
    this.object = object;
  }

  public String getKeyInStorage() {
    return keyInStorage;
  }

  public void setKeyInStorage(String keyInStorage) {
    this.keyInStorage = keyInStorage;
  }

  public String getNameInLocalStorageCache() {
    return nameInLocalStorageCache;
  }

  public void setNameInLocalStorageCache(String nameInLocalStorageCache) {
    this.nameInLocalStorageCache = nameInLocalStorageCache;
  }

  public Boolean getPublicAccess() {
    return publicAccess;
  }

  public void setPublicAccess(Boolean publicAccess) {
    this.publicAccess = publicAccess;
  }

  public void setTransientObject(byte[] transientObject) {
    this.transientObject = transientObject;
  }
}
