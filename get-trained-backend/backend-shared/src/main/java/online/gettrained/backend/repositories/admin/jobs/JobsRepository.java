package online.gettrained.backend.repositories.admin.jobs;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import online.gettrained.backend.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Volodymyr Dotsenko on 8/3/17.
 */
@Repository
public class JobsRepository extends BaseRepository {

  @SuppressWarnings("unchecked")
  public void deleteJobExecutionsByJobName(String jobName) {
    String sql = "SELECT JOB_INSTANCE_ID FROM BATCH_JOB_INSTANCE WHERE JOB_NAME=:jobName";
    List<BigInteger> instanceIds = getEntityManager().createNativeQuery(sql)
        .setParameter("jobName", jobName).getResultList();
    instanceIds.forEach(instanceId -> {
      String sql1 = "SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION WHERE JOB_INSTANCE_ID=:id";
      List<BigInteger> executionIds = getEntityManager().createNativeQuery(sql1)
          .setParameter("id", instanceId).getResultList();
      executionIds.forEach(id -> deleteJobExecutionsByJobExecutionId(id.longValue()));
    });
  }

  public void deleteJobExecutionsByJobExecutionId(Long id) {
    String sql = "DELETE FROM BATCH_STEP_EXECUTION_CONTEXT WHERE STEP_EXECUTION_ID " +
        " IN (SELECT STEP_EXECUTION_ID FROM BATCH_STEP_EXECUTION WHERE JOB_EXECUTION_ID = :id);";
    getEntityManager().createNativeQuery(sql).setParameter("id", id).executeUpdate();

    sql = "DELETE FROM BATCH_STEP_EXECUTION WHERE JOB_EXECUTION_ID = :id";
    getEntityManager().createNativeQuery(sql).setParameter("id", id).executeUpdate();

    sql = "DELETE FROM BATCH_JOB_EXECUTION_CONTEXT WHERE JOB_EXECUTION_ID = :id";
    getEntityManager().createNativeQuery(sql).setParameter("id", id).executeUpdate();

    sql = "DELETE FROM BATCH_JOB_EXECUTION_PARAMS WHERE JOB_EXECUTION_ID = :id";
    getEntityManager().createNativeQuery(sql).setParameter("id", id).executeUpdate();

    sql = "DELETE FROM BATCH_JOB_EXECUTION WHERE JOB_EXECUTION_ID = :id";
    getEntityManager().createNativeQuery(sql).setParameter("id", id).executeUpdate();
  }

  @SuppressWarnings("unchecked")
  public List<BigInteger> findAllOldJobIds(Date threshold) {
    return ((List<BigInteger>) getEntityManager().createNativeQuery(
        "SELECT JOB_EXECUTION_ID FROM BATCH_JOB_EXECUTION WHERE CREATE_TIME < :threshold")
        .setParameter("threshold", threshold)
        .getResultList());
  }
}
