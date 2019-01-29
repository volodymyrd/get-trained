package online.gettrained.batch.jobs.metrics;

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
public class MetricsJobConfig extends BaseJobConfig {

  @Autowired
  public MetricsJobConfig(
      BatchConfigurer batchConfigurer,
      ThreadPoolTaskScheduler threadPoolTaskScheduler,
      JobBuilderFactory jobs,
      StepBuilderFactory steps) {
    super(batchConfigurer, threadPoolTaskScheduler, jobs, steps);
  }

  @Bean
  public Job sendMetricsJob(@Qualifier("step1SendMetrics") Step step1SendMetrics) {
    return jobs.get("sendMetricsJob").start(step1SendMetrics).build();
  }

  @Bean
  protected Step step1SendMetrics(SendMetricsTasklet tasklet) {
    return steps.get("step1SendMetrics")
        .tasklet(tasklet)
        .allowStartIfComplete(true)
        .build();
  }
}
