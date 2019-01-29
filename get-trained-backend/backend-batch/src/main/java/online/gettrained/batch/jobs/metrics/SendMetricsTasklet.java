package online.gettrained.batch.jobs.metrics;


import static online.gettrained.backend.domain.localization.Language.DEFAULT_CODE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.domain.notif.templates.MergeTag;
import online.gettrained.backend.domain.notif.templates.MessageTemplateLocal;
import online.gettrained.backend.props.ApplicationCommonProperties;
import online.gettrained.backend.services.admin.metrics.MetricsService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.services.notif.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

/**
 * Read and put metrics to send
 */
@Component
public class SendMetricsTasklet implements Tasklet {

  private static final Logger LOG = LoggerFactory.getLogger(SendMetricsTasklet.class);

  private final ApplicationCommonProperties applicationCommonProperties;
  private final NotificationService notificationService;
  private final MetricsService metricsService;
  private final LocalizationService localizationService;

  public SendMetricsTasklet(
      ApplicationCommonProperties applicationCommonProperties,
      NotificationService notificationService,
      MetricsService metricsService,
      LocalizationService localizationService) {
    this.applicationCommonProperties = applicationCommonProperties;
    this.notificationService = notificationService;
    this.metricsService = metricsService;
    this.localizationService = localizationService;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {

    LOG.info("Started the task...");

    Optional<Language> languageOptional = localizationService.findSupportedLangByCode(DEFAULT_CODE);
    if (!languageOptional.isPresent()) {
      LOG.error("Not found language {}", DEFAULT_CODE);
      return RepeatStatus.FINISHED;
    }

    String adminEmails = applicationCommonProperties.getMailAdminEmails();
    if (adminEmails == null || adminEmails.isEmpty()) {
      LOG.error("Parameter {} of admin emails not set in config file (must be comma separated)",
          "user.prop.admin_emails");
      return RepeatStatus.FINISHED;
    }

    String metrics = metricsService.invoke().entrySet().stream().sorted(Map.Entry.comparingByKey())
        .map((e) -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("<br/>"));

    try {
      notificationService
          .preProcessQueue(NotificationEvent.Code.ADMIN_SYSTEM_METRICS, languageOptional.get(),
              new HashMap<String, String>() {
                private static final long serialVersionUID = 714767951595242878L;

                {
                  put(MergeTag.EMAIL.getValue(), adminEmails);
                  put(MergeTag.ADMIN_SYSTEM_METRICS.getValue(), metrics);
                }
              }, new MessageTemplateLocal("System metric", "${systemMetrics}"));
    } catch (Exception ex) {
      LOG.error("Error get and put to send metrics", ex);
    }
    LOG.info("Finished the task");
    return RepeatStatus.FINISHED;
  }
}
