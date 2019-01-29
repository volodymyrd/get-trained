package online.gettrained.batch.jobs.notif;

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
public class NotificationJobLauncher extends BaseJobLauncher {

  private static final Logger LOG = LoggerFactory.getLogger(NotificationJobLauncher.class);

  private final Job notificationJob;

  @Autowired
  public NotificationJobLauncher(JobLauncher jobLauncher,
      @Qualifier("notificationJob") Job notificationJob) {
    super(jobLauncher);
    this.notificationJob = notificationJob;
  }

  @Scheduled(cron = "${berize.scheduling.cron.notification:0 */1 * * * *}")
  public void cronJob() {
    LOG.info("Try start the 'notificationJob'...");
    run(notificationJob);
  }
}
