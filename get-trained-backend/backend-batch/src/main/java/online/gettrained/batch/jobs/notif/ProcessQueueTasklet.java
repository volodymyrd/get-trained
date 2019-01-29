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
 * Processes the notification queue
 */
@Component
public class ProcessQueueTasklet implements Tasklet {

  private static final Logger LOG = LoggerFactory.getLogger(ProcessQueueTasklet.class);

  private final NotificationService notificationService;

  public ProcessQueueTasklet(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    LOG.info("Started the task...");
    notificationService.findAllQueuesByStatusInAndExpireDateGreaterThan(
        CommonUtils.immutableListOf(ENotificationStatus.PROCESSING),
        new Date()).forEach(e -> {
      try {
        LOG.info("Start handling a task for {} event and for {} channel",
            e.getEvent().getEvent(),
            e.getChannel().getChannel());
        notificationService.processQueue(e);
      } catch (Exception ex) {
        LOG.error("Error processing a notification task", ex);
      }
    });
    LOG.info("Finished the task");
    return RepeatStatus.FINISHED;
  }
}
