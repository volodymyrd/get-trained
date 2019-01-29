package online.gettrained.batch.config;

import online.gettrained.backend.props.ApplicationCommonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulingConfigurerConfiguration implements SchedulingConfigurer {

  private final ApplicationCommonProperties applicationCommonProperties;

  public SchedulingConfigurerConfiguration(
      ApplicationCommonProperties applicationCommonProperties) {
    this.applicationCommonProperties = applicationCommonProperties;
  }

  @Bean
  public ThreadPoolTaskScheduler taskSchedulerPool() {
    Integer poolSize = applicationCommonProperties.getBatchPoolSize();
    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.setThreadGroupName("batchSchedulerPool");
    taskScheduler.setPoolSize(poolSize != null && poolSize > 0 ? poolSize : 5);
    taskScheduler.initialize();
    return taskScheduler;
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setTaskScheduler(taskSchedulerPool());
  }
}
