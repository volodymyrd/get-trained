package online.gettrained.batch.jobs.notif;

import java.util.Date;
import online.gettrained.backend.domain.notif.ENotificationStatus;
import online.gettrained.backend.services.notif.NotificationService;
import online.gettrained.backend.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * Reads the notification queue with status {@link ENotificationStatus#NEW} and {@link
 * ENotificationStatus#ERROR} and changes their statuses to {@link ENotificationStatus#PROCESSING}.
 */
@Component
public class ReadQueueTasklet implements Tasklet {

  private static final Logger LOG = LoggerFactory.getLogger(ReadQueueTasklet.class);

  private final NotificationService notificationService;

  public ReadQueueTasklet(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    LOG.info("Started the task...");
    notificationService.findAllQueuesByStatusInAndExpireDateGreaterThan(
        CommonUtils.immutableListOf(ENotificationStatus.NEW, ENotificationStatus.ERROR),
        new Date()).forEach(e -> {
      try {
        e.setStatus(ENotificationStatus.PROCESSING);
        notificationService.saveQueue(e);
      } catch (Exception ex) {
        LOG.error("Error changing of the status", ex);
      }
    });
    LOG.info("Finished the task");
    return RepeatStatus.FINISHED;
  }
}
