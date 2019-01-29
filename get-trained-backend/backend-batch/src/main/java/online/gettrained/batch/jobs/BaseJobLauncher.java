package online.gettrained.batch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import java.util.Date;

/**
 * Basic job
 */
public abstract class BaseJobLauncher {

  protected final JobLauncher jobLauncher;

  public BaseJobLauncher(JobLauncher jobLauncher) {
    this.jobLauncher = jobLauncher;
  }

  protected void run(Job job) {
    try {
      JobParametersBuilder builder = new JobParametersBuilder();
      builder.addDate("date", new Date());
      jobLauncher.run(job, builder.toJobParameters());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
