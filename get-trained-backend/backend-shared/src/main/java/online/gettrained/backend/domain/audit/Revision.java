package online.gettrained.backend.domain.audit;

import online.gettrained.backend.audit.UserRevisionListener;
import online.gettrained.backend.domain.user.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

/**
 * Extends  default revision with username.
 */
@Entity
@Table(name = "REVINFO")
@RevisionEntity(UserRevisionListener.class)
public class Revision extends DefaultRevisionEntity {

  private static final long serialVersionUID = 6175402848462026407L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_change_id")
  private User userChange;

  public User getUserChange() {
    return userChange;
  }

  public void setUserChange(User userChange) {
    this.userChange = userChange;
  }
}
