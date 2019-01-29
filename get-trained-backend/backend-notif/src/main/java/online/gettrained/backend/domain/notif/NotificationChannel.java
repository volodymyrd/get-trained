package online.gettrained.backend.domain.notif;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The notification channels.
 */
@Entity
@Table(name = "NOTIF_CHANNELS")
public class NotificationChannel implements Serializable {

  private static final long serialVersionUID = -1466072829021297508L;

  public enum Code {
    SMS,
    EMAIL,
    PUSH,
    SYSTEM
  }

  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "CHANNEL", nullable = false, length = 12)
  private Code channel;

  @Column(name = "DESCRIPTION", nullable = false, length = 100)
  private String description;

  public Code getChannel() {
    return channel;
  }

  public void setChannel(Code channel) {
    this.channel = channel;
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
    if (!(o instanceof NotificationChannel)) {
      return false;
    }
    NotificationChannel that = (NotificationChannel) o;
    return channel == that.channel;
  }

  @Override
  public int hashCode() {
    return Objects.hash(channel);
  }

  @Override
  public String toString() {
    return "NotificationChannel{" +
        "code=" + channel +
        '}';
  }
}
