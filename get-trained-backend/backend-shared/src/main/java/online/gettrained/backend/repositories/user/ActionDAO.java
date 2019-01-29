package online.gettrained.backend.repositories.user;

import online.gettrained.backend.domain.user.UserAction.Id;
import online.gettrained.backend.repositories.BaseRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * Action dao
 */
@Repository
public class ActionDAO extends BaseRepository {

  public boolean isAllowedForUser(Long userId, List<Id> actionIds) {
    if (userId == null || actionIds == null || actionIds.isEmpty()) {
      throw new IllegalArgumentException("Parameters must set");
    }
    return ((BigInteger) getEntityManager().createNativeQuery(
        "SELECT COUNT(ura.ACTION_ID) FROM USR_ROLES_ACTIONS ura "
            + " INNER JOIN USR_USERS_ROLES ur ON ur.REF_ROLE_ID=ura.REF_ROLE_ID "
            + " WHERE ur.REF_USER_ID=:userId AND ura.ACTION_ID IN :actionIds")
        .setParameter("userId", userId)
        .setParameter("actionIds", buildActionNamesSet(actionIds))
        .getSingleResult()).intValue() > 0;
  }

  public boolean isAllowedForUser(Long companyId, Long userId, List<Id> actionIds) {
    if (companyId == null || userId == null || actionIds == null || actionIds.isEmpty()) {
      throw new IllegalArgumentException("Parameters must set");
    }
    return ((BigInteger) getEntityManager().createNativeQuery(
        "SELECT COUNT(ura.ACTION_ID) FROM USR_ROLES_ACTIONS ura "
            + " INNER JOIN ORG_MEMBERS_ROLES mr ON mr.REF_ROLE_ID=ura.REF_ROLE_ID "
            + " INNER JOIN ORG_MEMBERS m ON m.ID=mr.REF_MEMBER_ID "
            + " WHERE m.REF_COMPANY_ID=:companyId AND m.REF_USER_ID=:userId AND ura.ACTION_ID IN :actionIds")
        .setParameter("companyId", companyId)
        .setParameter("userId", userId)
        .setParameter("actionIds", buildActionNamesSet(actionIds))
        .getSingleResult()).intValue() > 0;
  }

  public boolean isAllowedForMember(Long memberId, List<Id> actionIds) {
    if (memberId == null || actionIds == null || actionIds.isEmpty()) {
      throw new IllegalArgumentException("Parameters must set");
    }
    return ((BigInteger) getEntityManager().createNativeQuery(
        "SELECT COUNT(ura.ACTION_ID) FROM USR_ROLES_ACTIONS ura "
            + " INNER JOIN ORG_MEMBERS_ROLES mr ON mr.REF_ROLE_ID=ura.REF_ROLE_ID "
            + " INNER JOIN ORG_MEMBERS m ON m.ID=mr.REF_MEMBER_ID "
            + " WHERE m.ID=:memberId AND ura.ACTION_ID IN :actionIds")
        .setParameter("memberId", memberId)
        .setParameter("actionIds", buildActionNamesSet(actionIds))
        .getSingleResult()).intValue() > 0;
  }

  private Set<String> buildActionNamesSet(List<Id> actionIds) {
    Set<String> set = actionIds.stream().map(Enum::name).collect(Collectors.toSet());
    set.add(Id.BERIZE_ALL.name());
    return set;
  }
}
