package online.gettrained.backend.repositories.notif;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Query;
import online.gettrained.backend.constraints.NotificationEventConstraint;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.domain.notif.NotificationEvent.Code;
import online.gettrained.backend.domain.notif.NotificationEvent.Scope;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Notification event DAO
 */
@Repository
public class NotificationEventDAO extends BaseRepository {

  @SuppressWarnings("unchecked")
  public Page<NotificationEvent> findAllByConstraint(NotificationEventConstraint constraint) {
    StringBuilder whereClause = new StringBuilder("");
    if (constraint.getGroupCodes() != null && !constraint.getGroupCodes().isEmpty()) {
      whereClause.append(" AND e.REF_GROUP_CODE=:groups ");
    }
    Query dataQuery = getEntityManager().createNativeQuery(
        "SELECT e.EVENT, e.SCOPE, e.DEFAULT_DESCRIPTION, l.DESCRIPTION "
            + " FROM NOTIF_EVENTS e LEFT JOIN NOTIF_EVENT_LOCALS l ON e.EVENT=l.EVENT AND l.LANG_CODE=:langCode "
            + " WHERE e.SCOPE=:scope "
            + whereClause
            + " ORDER BY e.EVENT ");
    Query countQuery = getEntityManager().createNativeQuery(
        "SELECT COUNT(e.EVENT) FROM NOTIF_EVENTS e WHERE e.SCOPE=:scope " + whereClause);

    Page<NotificationEvent> page = new Page<>();
    dataQuery.setParameter("scope", constraint.getScope().name());
    dataQuery.setParameter("langCode", constraint.getLangCode());
    countQuery.setParameter("scope", constraint.getScope().name());

    if (constraint.getGroupCodes() != null && !constraint.getGroupCodes().isEmpty()) {
      Set<String> groups = constraint.getGroupCodes().stream().map(Enum::name)
          .collect(Collectors.toSet());
      dataQuery.setParameter("groups", groups);
      countQuery.setParameter("groups", groups);
    }

    page.setCount(((BigInteger) countQuery.getSingleResult()).longValue());
    page.setData(((List<Object[]>) dataQuery
        .setFirstResult((int) constraint.getPageable().getOffset())
        .setMaxResults(constraint.getPageable().getPageSize())
        .getResultList())
        .stream().map(o -> {
          NotificationEvent event = new NotificationEvent();
          event.setEvent(Code.valueOf((String) o[0]));
          event.setScope(Scope.valueOf((String) o[1]));
          event.setDescription((String) (o[3] != null ? o[3] : o[2]));
          return event;
        }).collect(Collectors.toList()));
    return page;
  }
}
