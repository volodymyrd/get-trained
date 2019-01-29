package online.gettrained.backend.services.admin;


import java.util.Date;

public interface JobsService {

  void deleteJobExecutionsByJobExecutionId(Long id);

  void deleteJobExecutionsByJobName(String jobName);

  int deleteAllOldJobs(Date threshold);
}
