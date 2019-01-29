package online.gettrained.batch.jobs.notif;

import online.gettrained.batch.jobs.BaseJobConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class NotificationJobConfig extends BaseJobConfig {

  @Autowired
  public NotificationJobConfig(
      BatchConfigurer batchConfigurer,
      ThreadPoolTaskScheduler threadPoolTaskScheduler,
      JobBuilderFactory jobs,
      StepBuilderFactory steps) {
    super(batchConfigurer, threadPoolTaskScheduler, jobs, steps);
  }

  @Bean
  public Job notificationJob(
      @Qualifier("step0DeleteErrors") Step step0DeleteErrors,
      @Qualifier("step1ReadQueue") Step step1ReadQueue,
      @Qualifier("step2ProcessQueue") Step step2ProcessQueue) {
    return jobs.get("notificationJob")
        .start(step0DeleteErrors)
        .next(step1ReadQueue)
        .next(step2ProcessQueue)
        .build();
  }

  @Bean
  protected Step step0DeleteErrors(DeleteErrorsTasklet tasklet) {
    return steps.get("step0DeleteErrors")
        .tasklet(tasklet)
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  protected Step step1ReadQueue(ReadQueueTasklet tasklet) {
    return steps.get("step1ReadQueue")
        .tasklet(tasklet)
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  protected Step step2ProcessQueue(ProcessQueueTasklet tasklet) {
    return steps.get("step2ProcessQueue")
        .tasklet(tasklet)
        .allowStartIfComplete(true)
        .build();
  }
}
