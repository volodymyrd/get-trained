package online.gettrained.backend.domain.notif;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The notification event groups.
 */
@Entity
@Table(name = "NOTIF_EVENT_GROUPS")
public class NotificationEventGroup implements Serializable {

  private static final long serialVersionUID = -6777773177514930085L;

  public enum Code {
    SYSTEM,
  }

  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "CODE", length = 50)
  private Code code;

  @JsonIgnore
  @Column(name = "LOCAL_KEY_NAME", nullable = false, length = 200)
  private String localKeyName;

  @JsonIgnore
  @Column(name = "LOCAL_KEY_DESCRIPTION", nullable = false, length = 200)
  private String localKeyDescription;

  @JsonIgnore
  @Column(name = "DEFAULT_NAME", nullable = false, length = 50)
  private String defaultName;

  @JsonIgnore
  @Column(name = "DEFAULT_DESCRIPTION", nullable = false, length = 100)
  private String defaultDescription;

  @Transient
  private String name;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private String description;

  public Code getCode() {
    return code;
  }

  public void setCode(Code code) {
    this.code = code;
  }

  public String getLocalKeyName() {
    return localKeyName;
  }

  public void setLocalKeyName(String localKeyName) {
    this.localKeyName = localKeyName;
  }

  public String getLocalKeyDescription() {
    return localKeyDescription;
  }

  public void setLocalKeyDescription(String localKeyDescription) {
    this.localKeyDescription = localKeyDescription;
  }

  public String getDefaultName() {
    return defaultName;
  }

  public void setDefaultName(String defaultName) {
    this.defaultName = defaultName;
  }

  public String getDefaultDescription() {
    return defaultDescription;
  }

  public void setDefaultDescription(String defaultDescription) {
    this.defaultDescription = defaultDescription;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NotificationEventGroup)) {
      return false;
    }
    NotificationEventGroup that = (NotificationEventGroup) o;
    return code == that.code;
  }

  @Override
  public int hashCode() {

    return Objects.hash(code);
  }

  @Override
  public String toString() {
    return "NotificationEventGroup{" + "code=" + code
        + ", defaultName='" + defaultName + '\''
        + ", defaultDescription='" + defaultDescription + '\''
        + '}';
  }
}
