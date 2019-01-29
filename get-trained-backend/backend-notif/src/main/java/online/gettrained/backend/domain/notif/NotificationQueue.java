package online.gettrained.backend.domain.notif;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import online.gettrained.backend.domain.BaseEntity;
import online.gettrained.backend.domain.localization.Language;

/**
 * The queue of the notifications
 */
@Entity
@Table(name = "NOTIF_QUEUE", indexes = {
    @Index(name = "I_EVENT_CHANNEL", columnList = "EVENT, CHANNEL"),
    @Index(name = "I_EXPIRE_DATE", columnList = "EXPIRE_DATE"),
    @Index(name = "NOTIF_QUEUE_I_STATUS", columnList = "STATUS")
})
public class NotificationQueue extends BaseEntity {

  private static final long serialVersionUID = 8188784547787549586L;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "EVENT", nullable = false)
  private NotificationEvent event;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "CHANNEL", nullable = false)
  private NotificationChannel channel;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "LANG_CODE", nullable = false)
  private Language language;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", nullable = false, length = 12)
  private ENotificationStatus status;

  /**
   * Comma separated list of addresses
   */
  @Column(name = "ADDRESS_TO", nullable = false, columnDefinition = "TEXT")
  private String addressTo;

  @Column(name = "ADDRESS_FROM")
  private String addressFrom;

  @Column(name = "TITLE", length = 300)
  private String title;

  @Column(name = "TEXT_MESSAGE", nullable = false, columnDefinition = "TEXT")
  private String message;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "EXPIRE_DATE", nullable = false)
  private Date expireDate;

  @Column(name = "ERROR_MESSAGE", length = 400)
  private String errorMessage;

  @Column(name = "ERROR_ATTEMPTS")
  private Integer errorAttempts;

  public NotificationEvent getEvent() {
    return event;
  }

  public void setEvent(NotificationEvent event) {
    this.event = event;
  }

  public NotificationChannel getChannel() {
    return channel;
  }

  public void setChannel(NotificationChannel channel) {
    this.channel = channel;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public ENotificationStatus getStatus() {
    return status;
  }

  public void setStatus(ENotificationStatus status) {
    this.status = status;
  }

  public String getAddressTo() {
    return addressTo;
  }

  public void setAddressTo(String addressTo) {
    this.addressTo = addressTo;
  }

  public String getAddressFrom() {
    return addressFrom;
  }

  public void setAddressFrom(String addressFrom) {
    this.addressFrom = addressFrom;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Integer getErrorAttempts() {
    return errorAttempts;
  }

  public void setErrorAttempts(Integer errorAttempts) {
    this.errorAttempts = errorAttempts;
  }
}
