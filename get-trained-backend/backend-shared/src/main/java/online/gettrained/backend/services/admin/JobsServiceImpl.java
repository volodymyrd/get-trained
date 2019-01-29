package online.gettrained.backend.services.admin;

import java.math.BigInteger;
import java.util.Date;
import online.gettrained.backend.props.ApplicationCommonProperties;
import online.gettrained.backend.repositories.admin.jobs.JobsRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobsServiceImpl implements JobsService {

  private final ApplicationCommonProperties applicationCommonProperties;
  private final ApplicationContext applicationContext;
  private final JobsRepository jobsRepository;

  public JobsServiceImpl(
      ApplicationCommonProperties applicationCommonProperties,
      ApplicationContext applicationContext,
      JobsRepository jobsRepository) {
    this.applicationCommonProperties = applicationCommonProperties;
    this.applicationContext = applicationContext;
    this.jobsRepository = jobsRepository;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteJobExecutionsByJobExecutionId(Long id) {
    jobsRepository.deleteJobExecutionsByJobExecutionId(id);
  }

  @Override
  @Transactional
  public void deleteJobExecutionsByJobName(String jobName) {
    jobsRepository.deleteJobExecutionsByJobName(jobName);
  }

  @Override
  @Transactional
  public int deleteAllOldJobs(Date threshold) {
    int count = 0;
    for (BigInteger id : jobsRepository.findAllOldJobIds(threshold)) {
      applicationContext.getBean(JobsService.class)
          .deleteJobExecutionsByJobExecutionId(id.longValue());
      count++;
    }
    return count;
  }
}
