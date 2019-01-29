package online.gettrained.backend.domain;

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

  public User getUserLastChanged() {
    return userLastChanged;
  }

  public void setUserLastChanged(User userLastChanged) {
    this.userLastChanged = userLastChanged;
  }
}
