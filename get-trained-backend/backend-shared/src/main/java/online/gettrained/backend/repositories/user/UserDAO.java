package online.gettrained.backend.repositories.user;

import java.util.Date;
import java.util.List;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for {@link User}.
 */
@Repository
public class UserDAO extends BaseRepository {

  @SuppressWarnings("unchecked")
  public List<Object[]> findAllUsersWithProfileRecentlyCreatedOrChanged(Date date) {
    return getEntityManager().createNativeQuery(
        "SELECT u.ID u_id, p.FIRST_NAME, p.LAST_NAME FROM USR_USERS u "
            + " INNER JOIN USR_PROFILES p ON p.ID=u.REF_PROFILE_ID "
            + " WHERE p.DATE_CREATE>=:date OR p.DATE_UPDATE >=:date")
        .setParameter("date", date)
        .getResultList();
  }
}
