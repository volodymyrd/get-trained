package online.gettrained.backend.repositories.activities;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.requireNonNull;
import static online.gettrained.backend.utils.CommonUtils.immutableSetOf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Query;
import online.gettrained.backend.constraints.SelectOption;
import online.gettrained.backend.constraints.SelectOption.ParametrizedSQLConstraint;
import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import online.gettrained.backend.domain.activities.Activity;
import online.gettrained.backend.domain.activities.Trainer.Status;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for {@link Activity}.
 */
@Repository
public class ActivityDAO extends BaseRepository {

  @SuppressWarnings("unchecked")
  public Page<Activity> findAllWithCountTrainers(FrontendActivityConstraint constraint) {
    final Set<String> sortedColumns = immutableSetOf("activityId", "name", "trainers");

    requireNonNull(constraint, "Parameter 'constraint' must be set.");
    requireNonNull(constraint.getPageable(), "Parameter 'constraint.pageable' must be set.");
    checkArgument(
        !isNullOrEmpty(constraint.getLangCode()), "Parameter 'constraint.langCode' must be set.");

    StringBuilder whereClause = new StringBuilder();
    Map<String, Object> parameters = new HashMap<>();

    ParametrizedSQLConstraint tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoActivityNames(), "a.name");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    Query dataQuery = getEntityManager().createNativeQuery(
        "SELECT a.ID AS activityId, a.ICON AS icon, a.DEFAULT_NAME AS dname, "
            + " l.LOCAL_TEXT AS name, COUNT(t.*) AS trainers "
            + " FROM ACT_ACTIVITIES a "
            + " LEFT JOIN ACT_TRAINERS t ON a.ID=t.REF_ACTIVITY_ID AND t.STATUS=:tStatus "
            + " LEFT JOIN LOCAL_LOCALIZATION l ON a.LOCAL_KEY_NAME=l.LOCAL_KEY AND l.LANG=:langCode "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause)
            + " GROUP BY activityId, dname, icon, name "
            + buildOrderByClause(constraint.getPageable().getSort()));

    Query countQuery = getEntityManager()
        .createNativeQuery("SELECT COUNT(*) FROM ACT_ACTIVITIES a "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause));

    dataQuery.setParameter("tStatus", Status.VERIFIED.name());
    dataQuery.setParameter("langCode", constraint.getLangCode());
    parameters.forEach(dataQuery::setParameter);
    parameters.forEach(countQuery::setParameter);

    Page<Activity> page = new Page<>();
    page.setData(((List<Object[]>) dataQuery
        .setFirstResult((int) constraint.getPageable().getOffset())
        .setMaxResults(constraint.getPageable().getPageSize())
        .getResultList())
        .stream().map(r -> {
          Activity activity = new Activity();
          activity.setActivityId(((Number) r[0]).longValue());
          activity.setIcon((String) r[1]);
          activity.setName((String) (r[3] != null ? r[3] : r[2]));
          activity.setNumberOfTrainers(((Number) r[4]).intValue());
          return activity;
        }).collect(Collectors.toList()));
    page.setSortedColumns(sortedColumns);
    return page;
  }
}
