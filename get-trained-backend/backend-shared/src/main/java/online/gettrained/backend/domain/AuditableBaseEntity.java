package online.gettrained.backend.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import online.gettrained.backend.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Base entity with last change information.
 */
@MappedSuperclass
public abstract class AuditableBaseEntity extends BaseEntity {

  private static final long serialVersionUID = -2245085718922827908L;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_LAST_CHANGED_ID")
  private User userLastChanged;

  @JsonIgnore
  @Column(name = "DATE_DELETED")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateDeleted;

  @Column(name = "DELETED")
  private boolean deleted;

  public User getUserLastChanged() {
    return userLastChanged;
  }

  public void setUserLastChanged(User userLastChanged) {
    this.userLastChanged = userLastChanged;
  }

  public Date getDateDeleted() {
    return dateDeleted;
  }

  public void setDateDeleted(Date dateDeleted) {
    this.dateDeleted = dateDeleted;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
