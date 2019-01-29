package online.gettrained.batch.jobs.metrics;

import online.gettrained.batch.jobs.BaseJobLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "user.prop.scheduling.enable", havingValue = "true", matchIfMissing = true)
public class MetricsJobLauncher extends BaseJobLauncher {

  private static final Logger LOG = LoggerFactory.getLogger(MetricsJobLauncher.class);

  private final Job sendMetricsJob;

  @Autowired
  public MetricsJobLauncher(JobLauncher jobLauncher,
      @Qualifier("sendMetricsJob") Job sendMetricsJob) {
    super(jobLauncher);
    this.sendMetricsJob = sendMetricsJob;
  }

  @Scheduled(cron = "${berize.scheduling.cron.system_metrics:0 0 */1 * * *}")
  public void cronJob() {
    LOG.info("Try start the 'sendMetricsJob'...");
    run(sendMetricsJob);
  }
}
