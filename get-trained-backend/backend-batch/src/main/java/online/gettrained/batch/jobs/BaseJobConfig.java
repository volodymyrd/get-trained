package online.gettrained.batch.jobs;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Basic job config
 */
public abstract class BaseJobConfig {

  protected final BatchConfigurer batchConfigurer;
  protected final JobBuilderFactory jobs;
  protected final StepBuilderFactory steps;

  public BaseJobConfig(
      BatchConfigurer batchConfigurer,
      ThreadPoolTaskScheduler threadPoolTaskScheduler,
      JobBuilderFactory jobs,
      StepBuilderFactory steps) {
    this.batchConfigurer = batchConfigurer;
    this.jobs = jobs;
    this.steps = steps;
    configureJobLauncher(threadPoolTaskScheduler);
  }

  private void configureJobLauncher(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
    try {
      ((SimpleJobLauncher) batchConfigurer.getJobLauncher())
          .setTaskExecutor(threadPoolTaskScheduler);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
