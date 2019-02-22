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
import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.repositories.BaseRepository;
import online.gettrained.backend.services.blob.BlobDataService;
import org.springframework.stereotype.Repository;

/**
 * DAO for {@link Trainer}.
 */
@Repository
public class TrainerDAO extends BaseRepository {

  private final BlobDataService blobDataService;

  public TrainerDAO(BlobDataService blobDataService) {
    this.blobDataService = blobDataService;
  }

  @SuppressWarnings("unchecked")
  public Page<Trainer> findAll(FrontendActivityConstraint constraint) {
    final Set<String> sortedColumns = immutableSetOf("trainerId", "fullName");

    requireNonNull(constraint, "Parameter 'constraint' must be set.");
    requireNonNull(constraint.getPageable(), "Parameter 'constraint.pageable' must be set.");

    StringBuilder whereClause = new StringBuilder();
    Map<String, Object> parameters = new HashMap<>();

    ParametrizedSQLConstraint tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoTrainerStatuses(), "t.STATUS");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoTrainerVisibilities(), "t.VISIBILITY");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoActivityIds(), "t.REF_ACTIVITY_ID");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    Query dataQuery = getEntityManager().createNativeQuery(
        "SELECT t.ID AS tainerId, u.ID AS userId, "
            + " p.FULL_NAME AS fullName, p.BLOB_DATA_ID AS logo "
            + " FROM ACT_TRAINERS t "
            //+ " INNER JOIN ACT_ACTIVITIES a ON t.REF_ACTIVITY_ID=a.ID "
            + " INNER JOIN USR_USERS u ON t.REF_USER_ID=u.ID "
            + " INNER JOIN USR_PROFILES p ON u.REF_PROFILE_ID=p.ID "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause)
            + buildOrderByClause(constraint.getPageable().getSort()));

    Query countQuery = getEntityManager()
        .createNativeQuery("SELECT COUNT(t.*) FROM ACT_TRAINERS t "
            + " INNER JOIN USR_USERS u ON t.REF_USER_ID=u.ID "
            + " INNER JOIN USR_PROFILES p ON u.REF_PROFILE_ID=p.ID "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause));

    parameters.forEach(dataQuery::setParameter);
    parameters.forEach(countQuery::setParameter);

    Page<Trainer> page = new Page<>();
    page.setCount(((Number) countQuery.getSingleResult()).longValue());
    page.setData(((List<Object[]>) dataQuery
        .setFirstResult((int) constraint.getPageable().getOffset())
        .setMaxResults(constraint.getPageable().getPageSize())
        .getResultList())
        .stream().map(r -> {
          Trainer trainer = new Trainer();
          trainer.setTrainerId(((Number) r[0]).longValue());
          trainer.setTrainerUserId(((Number) r[1]).longValue());
          trainer.setFullName((String) r[2]);
          if (r[3] != null) {
            trainer.setLogoUrl(blobDataService.getFileUrl(((Number) r[3]).intValue()));
          }
          return trainer;
        }).collect(Collectors.toList()));
    page.setSortedColumns(sortedColumns);
    return page;
  }
}
