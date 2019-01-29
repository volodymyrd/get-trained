package online.gettrained.backend.audit;

import online.gettrained.backend.domain.audit.Revision;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.utils.SecurityUtils;
import org.hibernate.envers.RevisionListener;

/**
 * Adds an user to the revision.
 */
public class UserRevisionListener implements RevisionListener {

  @Override
  public void newRevision(Object revisionEntity) {
    Revision rev = (Revision) revisionEntity;
    User user = SecurityUtils.getCurrentUser();
    if (user != null) {
      rev.setUserChange(user);
    }
  }
}
