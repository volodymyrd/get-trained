package online.gettrained.backend.services.notif;

import online.gettrained.backend.constraints.NotificationEventConstraint;
import online.gettrained.backend.domain.notif.ENotificationStatus;
import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.domain.notif.NotificationEventGroup;
import online.gettrained.backend.domain.notif.NotificationQueue;
import online.gettrained.backend.domain.notif.templates.MessageTemplate;
import online.gettrained.backend.domain.notif.templates.MessageTemplateLocal;
import online.gettrained.backend.domain.notif.templates.MessageViewTemplate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.dto.Page;

/**
 *
 */
public interface NotificationService {

  void preProcessQueue(
      NotificationEvent.Code event,
      Language language,
      Map<String, String> data,
      MessageTemplateLocal defaultMessageTemplateLocal);

  List<NotificationQueue> findAllQueuesByStatusInAndExpireDateGreaterThan(
      List<ENotificationStatus> statuses,
      Date date);

  NotificationQueue saveQueue(NotificationQueue queue);

  void processQueue(NotificationQueue queue);

  Long countQueuesByEventCodeAndChannelCodeAndStatusInAndExpireDateGreaterThanAndAddressTo(
      NotificationEvent.Code event,
      NotificationChannel.Code channel,
      List<ENotificationStatus> statuses,
      Date date,
      String addressTo);

  List<NotificationEventGroup> findAllEventGroups(NotificationEvent.Scope scope, Language language);

  Page<NotificationEvent> findAllEventsByConstraint(NotificationEventConstraint constraint);

  Optional<NotificationEvent> findEventByCode(NotificationEvent.Code eventCode);

  Optional<NotificationChannel> findChannelByCode(NotificationChannel.Code channelCode);

  Optional<MessageTemplate> findTemplateByEventAndChannelIfSetIfSetAndLang(
      NotificationEvent.Code eventCode,
      NotificationChannel.Code channelCode,
      String lang);

  List<NotificationChannel> findAllChannelsOrderByCode();

  Optional<MessageTemplate> findTemplateByEventAndChannelIfSetWithEventAndChannelAndLocals(
      NotificationEvent.Code eventCode,
      NotificationChannel.Code channelCode);

  MessageViewTemplate saveViewTemplate(MessageViewTemplate viewTemplate);

  MessageTemplate saveTemplate(MessageTemplate template);

  int deleteTemplateByEventAndChannelIfSetAndLangIfSet(
      NotificationEvent.Code event,
      NotificationChannel.Code channel,
      String langCode);

  int deleteAllWithStatusErrorAndErrorAttemptsGreaterThan(int errorAttempts);
}
