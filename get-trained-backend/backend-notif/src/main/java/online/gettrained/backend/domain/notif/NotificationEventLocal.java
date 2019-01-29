package online.gettrained.backend.domain.notif;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Notification event localization
 */
@Embeddable
public class NotificationEventLocal implements Serializable {

  private static final long serialVersionUID = -8868258354316630644L;

  public NotificationEventLocal() {
  }

  public NotificationEventLocal(String description) {
    this.description = description;
  }

  @JsonIgnore
  @Column(name = "DESCRIPTION", nullable = false, length = 300)
  private String description;

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
    if (!(o instanceof NotificationEventLocal)) {
      return false;
    }
    NotificationEventLocal that = (NotificationEventLocal) o;
    return Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description);
  }

  @Override
  public String toString() {
    return "NotificationEventLocal{" +
        "description='" + description + '\'' +
        '}';
  }
}
