package online.gettrained.backend.repositories.activities;

import static java.util.Objects.requireNonNull;
import static online.gettrained.backend.utils.CommonUtils.immutableSetOf;
import static online.gettrained.backend.utils.DateUtils.getShortDateFormat;

import java.util.Date;
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

    whereClause.append(buildSQLClause(whereClause, "p.REF_CONNECTION_ID=:connectionId"));
    parameters.put("connectionId", constraint.getConnectionId());

    whereClause.append(buildSQLClause(whereClause, "p.TRAINEE_ID=:traineeUserId"));
    parameters.put("traineeUserId", constraint.getTraineeUserId());

    ParametrizedSQLConstraint tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoDateMeasures(), "p.DATE_MEASURE");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    Query dataQuery = getEntityManager().createNativeQuery(
        "SELECT p.ID AS traineeProfileId, p.REF_CONNECTION_ID AS connectionId, "
            + " p.TRAINEE_ID AS traineeUserId, p.DATE_MEASURE AS measure "
            //+ ", p.CALF AS calf, p.CHEST AS chest, p.FOREARM AS forearm, p.HIPS AS hips, "
            //+ " p.INNER_THIGH AS inner_thigh, p.NECK AS neck, p.WAIST AS waist, p.WRIST AS wrist "
            + " FROM ACT_FITNESS_TRAINEE_PROFILES p "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause)
            + buildOrderByClause(constraint.getPageable().getSort()));

    Query countQuery = getEntityManager()
        .createNativeQuery("SELECT COUNT(*) FROM ACT_FITNESS_TRAINEE_PROFILES p "
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
          profile.setMeasure(getShortDateFormat().format((Date) r[3]));
//          profile.setCalf(((Number) r[4]).longValue());
//          profile.setChest(((Number) r[5]).longValue());
//          profile.setForearm(((Number) r[6]).longValue());
//          profile.setHips(((Number) r[7]).longValue());
//          profile.setInnerThigh(((Number) r[8]).longValue());
//          profile.setNeck(((Number) r[9]).longValue());
//          profile.setWaist(((Number) r[10]).longValue());
//          profile.setWrist(((Number) r[11]).longValue());
          return profile;
        }).collect(Collectors.toList()));
    page.setSortedColumns(sortedColumns);
    return page;
  }
}
