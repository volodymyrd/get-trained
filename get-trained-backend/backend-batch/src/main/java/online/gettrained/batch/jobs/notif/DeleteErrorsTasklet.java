package online.gettrained.batch.jobs.notif;

import online.gettrained.backend.domain.notif.ENotificationStatus;
import online.gettrained.backend.domain.notif.NotificationQueue;
import online.gettrained.backend.services.notif.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * Deletes all message with an {@link ENotificationStatus#ERROR} status and {@link
 * NotificationQueue#errorAttempts} greater than given.
 */
@Component
public class DeleteErrorsTasklet implements Tasklet {

  private static final Logger LOG = LoggerFactory.getLogger(DeleteErrorsTasklet.class);

  private final NotificationService notificationService;

  public DeleteErrorsTasklet(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    LOG.info("Started the task...");
    try {
      int deleted = notificationService.deleteAllWithStatusErrorAndErrorAttemptsGreaterThan(10);
      LOG.info("Deleted {} message(s) with an error", deleted);
    } catch (Exception ex) {
      LOG.error("Error deleting messages with an error", ex);
    }
    LOG.info("Finished the task");
    return RepeatStatus.FINISHED;
  }
}
