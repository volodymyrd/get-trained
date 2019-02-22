package online.gettrained.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Base entity with last change information with non-deletable records.
 */
@MappedSuperclass
public abstract class AuditableNonDeletableBaseEntity extends AuditableBaseEntity {

  private static final long serialVersionUID = -2245085718922827908L;

  @JsonIgnore
  @Column(name = "DATE_DELETED")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateDeleted;

  @Column(name = "DELETED")
  private boolean deleted;

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
