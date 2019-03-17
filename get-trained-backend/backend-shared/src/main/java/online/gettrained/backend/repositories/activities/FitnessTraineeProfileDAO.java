package online.gettrained.backend.repositories.activities;

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
import online.gettrained.backend.constraints.frontend.activities.FrontendTraineeProfileConstraint;
import online.gettrained.backend.domain.activities.FitnessTraineeProfile;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for {@link FitnessTraineeProfile}.
 */
@Repository
public class FitnessTraineeProfileDAO extends BaseRepository {

  @SuppressWarnings("unchecked")
  public Page<FitnessTraineeProfile> findAll(FrontendTraineeProfileConstraint constraint) {
    final Set<String> sortedColumns = immutableSetOf("measure");

    requireNonNull(constraint, "Parameter 'constraint' must be set.");
    requireNonNull(constraint.getPageable(), "Parameter 'constraint.pageable' must be set.");

    StringBuilder whereClause = new StringBuilder();
    Map<String, Object> parameters = new HashMap<>();

    ParametrizedSQLConstraint tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoDateMeasures(), "p.DATE_MEASURE");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    Query dataQuery = getEntityManager().createNativeQuery(
        "SELECT c.ID AS cId, trainers.ID AS tainerId, c.STATUS AS status "
            + " FROM ACT_FITNESS_TRAINEE_PROFILES p "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause)
            + buildOrderByClause(constraint.getPageable().getSort()));

    Query countQuery = getEntityManager()
        .createNativeQuery("SELECT COUNT(c.*) FROM ACT_FITNESS_TRAINEE_PROFILES p "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause));

    parameters.forEach(dataQuery::setParameter);
    parameters.forEach(countQuery::setParameter);

    Page<FitnessTraineeProfile> page = new Page<>();
    page.setCount(((Number) countQuery.getSingleResult()).longValue());
    page.setData(((List<Object[]>) dataQuery
        .setFirstResult((int) constraint.getPageable().getOffset())
        .setMaxResults(constraint.getPageable().getPageSize())
        .getResultList())
        .stream().map(r -> {
          FitnessTraineeProfile profile = new FitnessTraineeProfile();
          profile.setTraineeProfileId(((Number) r[0]).longValue());
          profile.setConnectionId(((Number) r[1]).longValue());
          profile.setTraineeUserId(((Number) r[2]).longValue());
          return profile;
        }).collect(Collectors.toList()));
    page.setSortedColumns(sortedColumns);
    return page;
  }
}
