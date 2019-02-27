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
import online.gettrained.backend.domain.activities.TrainerConnections;
import online.gettrained.backend.domain.activities.TrainerConnections.Status;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.repositories.BaseRepository;
import online.gettrained.backend.services.blob.BlobDataService;
import org.springframework.stereotype.Repository;

/**
 * DAO for {@link TrainerConnections}.
 */
@Repository
public class TrainerConnectionsDAO extends BaseRepository {

  private final BlobDataService blobDataService;

  public TrainerConnectionsDAO(BlobDataService blobDataService) {
    this.blobDataService = blobDataService;
  }

  @SuppressWarnings("unchecked")
  public Page<TrainerConnections> findAll(FrontendActivityConstraint constraint) {
    final Set<String> sortedColumns = immutableSetOf("trainerFullName", "traineeFullName, status");

    requireNonNull(constraint, "Parameter 'constraint' must be set.");
    requireNonNull(constraint.getPageable(), "Parameter 'constraint.pageable' must be set.");

    StringBuilder whereClause = new StringBuilder();
    Map<String, Object> parameters = new HashMap<>();

    ParametrizedSQLConstraint tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoConnectionsStatuses(), "c.STATUS");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoTrainerIds(), "c.REF_TRAINER_ID");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoUserTrainerIds(), "u_trainer.ID");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    tempSql = SelectOption
        .toParametrizedSQLConstraint(constraint.getSoUserTraineeIds(), "u_trainee.ID");
    if (!tempSql.isEmpty()) {
      whereClause.append(buildSQLClause(whereClause, tempSql.getSql()));
      parameters.putAll(tempSql.getParameters());
    }

    Query dataQuery = getEntityManager().createNativeQuery(
        "SELECT c.ID AS cId, trainers.ID AS tainerId, c.STATUS AS status, "
            + " u_trainer.ID AS userTrainerId, "
            + " p_trainer.FULL_NAME AS trainerfullName, p_trainer.BLOB_DATA_ID AS trainerLogo,"
            + " u_trainee.ID AS userTraineeId, "
            + " p_trainee.FULL_NAME AS traineefullName, p_trainee.BLOB_DATA_ID AS traineeLogo "
            + " FROM ACT_TRAINER_CONNECTIONS c "
            + " INNER JOIN ACT_TRAINERS trainers ON c.REF_TRAINER_ID=trainers.ID "
            + " INNER JOIN USR_USERS u_trainer ON c.REF_TRAINER_USER_ID=u_trainer.ID "
            + " INNER JOIN USR_PROFILES p_trainer ON u_trainer.REF_PROFILE_ID=p_trainer.ID "
            + " INNER JOIN USR_USERS u_trainee ON c.REF_TRAINEE_USER_ID=u_trainee.ID "
            + " INNER JOIN USR_PROFILES p_trainee ON u_trainee.REF_PROFILE_ID=p_trainee.ID "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause)
            + buildOrderByClause(constraint.getPageable().getSort()));

    Query countQuery = getEntityManager()
        .createNativeQuery("SELECT COUNT(c.*) FROM ACT_TRAINER_CONNECTIONS c "
            + " INNER JOIN USR_USERS u_trainer ON c.REF_TRAINER_USER_ID=u_trainer.ID "
            + " INNER JOIN USR_USERS u_trainee ON c.REF_TRAINEE_USER_ID=u_trainee.ID "
            + (whereClause.length() == 0 ? "" : " WHERE " + whereClause));

    parameters.forEach(dataQuery::setParameter);
    parameters.forEach(countQuery::setParameter);

    Page<TrainerConnections> page = new Page<>();
    page.setCount(((Number) countQuery.getSingleResult()).longValue());
    page.setData(((List<Object[]>) dataQuery
        .setFirstResult((int) constraint.getPageable().getOffset())
        .setMaxResults(constraint.getPageable().getPageSize())
        .getResultList())
        .stream().map(r -> {
          TrainerConnections connections = new TrainerConnections();
          connections.setConnectionId(((Number) r[0]).longValue());
          connections.setTrainerId(((Number) r[1]).longValue());
          connections.setStatus(Status.valueOf((String) r[2]));
          connections.setTrainerUserId(((Number) r[3]).longValue());
          connections.setTrainerFullName((String) r[4]);
          if (r[5] != null) {
            connections.setTrainerLogoUrl(blobDataService.getFileUrl(((Number) r[5]).intValue()));
          }
          connections.setTraineeUserId(((Number) r[6]).longValue());
          connections.setTraineeFullName((String) r[7]);
          if (r[8] != null) {
            connections.setTraineeLogoUrl(blobDataService.getFileUrl(((Number) r[8]).intValue()));
          }
          return connections;
        }).collect(Collectors.toList()));
    page.setSortedColumns(sortedColumns);
    return page;
  }
}
