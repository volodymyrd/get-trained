package online.gettrained.backend.services.notif;

import static online.gettrained.backend.services.notif.handlers.NotificationHandler.MESSAGE;
import static online.gettrained.backend.services.notif.handlers.NotificationHandler.SUBJECT;
import static online.gettrained.backend.services.notif.handlers.NotificationHandler.TO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import online.gettrained.backend.constraints.NotificationEventConstraint;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.notif.ENotificationStatus;
import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.domain.notif.NotificationEvent.Scope;
import online.gettrained.backend.domain.notif.NotificationEventGroup;
import online.gettrained.backend.domain.notif.NotificationEventGroup.Code;
import online.gettrained.backend.domain.notif.NotificationQueue;
import online.gettrained.backend.domain.notif.templates.MergeTag;
import online.gettrained.backend.domain.notif.templates.MessageTemplate;
import online.gettrained.backend.domain.notif.templates.MessageTemplateLocal;
import online.gettrained.backend.domain.notif.templates.MessageViewTemplate;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.props.ApplicationCommonProperties;
import online.gettrained.backend.repositories.notif.NotificationChannelRepository;
import online.gettrained.backend.repositories.notif.NotificationEventDAO;
import online.gettrained.backend.repositories.notif.NotificationEventGroupRepository;
import online.gettrained.backend.repositories.notif.NotificationEventRepository;
import online.gettrained.backend.repositories.notif.NotificationQueueRepository;
import online.gettrained.backend.repositories.notif.template.MessageTemplateDAO;
import online.gettrained.backend.repositories.notif.template.MessageTemplateRepository;
import online.gettrained.backend.repositories.notif.template.MessageViewTemplateDAO;
import online.gettrained.backend.repositories.notif.template.MessageViewTemplateRepository;
import online.gettrained.backend.services.blob.BlobDataService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.services.notif.handlers.NotificationHandler;
import online.gettrained.backend.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {

  private static final Logger LOG = LoggerFactory.getLogger(NotificationServiceImpl.class);

  private final ApplicationCommonProperties applicationCommonProperties;
  private final LocalizationService localizationService;
  private final BlobDataService blobDataService;
  private final NotificationChannelRepository channelRepository;
  private final NotificationEventGroupRepository eventGroupRepository;
  private final NotificationEventRepository eventRepository;
  private final NotificationEventDAO notificationEventDAO;
  private final NotificationQueueRepository queueRepository;
  private final MessageTemplateRepository messageTemplateRepository;
  private final MessageViewTemplateRepository messageViewTemplateRepository;
  private final MessageViewTemplateDAO messageViewTemplateDAO;
  private final MessageTemplateDAO messageTemplateDAO;
  private final TemplateProcessor processor;

  public NotificationServiceImpl(
      ApplicationCommonProperties applicationCommonProperties,
      LocalizationService localizationService,
      BlobDataService blobDataService,
      NotificationChannelRepository channelRepository,
      NotificationEventGroupRepository eventGroupRepository,
      NotificationEventRepository eventRepository,
      NotificationEventDAO notificationEventDAO,
      NotificationQueueRepository queueRepository,
      MessageTemplateRepository messageTemplateRepository,
      MessageViewTemplateRepository messageViewTemplateRepository,
      MessageViewTemplateDAO messageViewTemplateDAO,
      MessageTemplateDAO messageTemplateDAO,
      TemplateProcessor processor) {
    this.applicationCommonProperties = applicationCommonProperties;
    this.localizationService = localizationService;
    this.blobDataService = blobDataService;
    this.channelRepository = channelRepository;
    this.eventGroupRepository = eventGroupRepository;
    this.eventRepository = eventRepository;
    this.notificationEventDAO = notificationEventDAO;
    this.queueRepository = queueRepository;
    this.messageTemplateRepository = messageTemplateRepository;
    this.messageViewTemplateRepository = messageViewTemplateRepository;
    this.messageViewTemplateDAO = messageViewTemplateDAO;
    this.messageTemplateDAO = messageTemplateDAO;
    this.processor = processor;
  }

  @Override
  @Transactional
  public void preProcessQueue(
      NotificationEvent.Code codeEvent,
      Language language,
      Map<String, String> data,
      MessageTemplateLocal defaultMessageTemplateLocal) {
    if (data == null) {
      LOG.error("Parameter 'data' must not be null");
      return;
    }
    if (language == null) {
      LOG.warn("Parameter 'language' must not be null, using a default language.");
      Optional<Language> languageOptional = localizationService.findDefaultLang();
      if (!languageOptional.isPresent()) {
        LOG.warn("Not found a default language!");
        return;
      }
      language = languageOptional.get();
    }
    LOG.debug("Run preProcessQueue for the event:{} language:{} with data:{}",
        codeEvent, language, data);
    Optional<NotificationEvent> eventOptional = eventRepository.findById(codeEvent);
    if (!eventOptional.isPresent()) {
      LOG.error("Not found an event with code:{}", codeEvent);
      return;
    }
    NotificationEvent event = eventOptional.get();

    List<MessageTemplate> templates = messageTemplateRepository.findAllByEvent_Event(codeEvent);

    if (templates == null || templates.isEmpty()) {
      LOG.error("Not found any templates for event with code:{}", codeEvent);
      return;
    }
    for (MessageTemplate template : templates) {
      NotificationChannel channel = template.getChannel();
      if (event.getSingleton() != null && event.getSingleton()) {
        long count = queueRepository
            .countByEventCodeAndChannelCodeAndStatusInAndExpireDateGreaterThanAndAddressTo(
                codeEvent,
                channel.getChannel(),
                CommonUtils.immutableListOf(ENotificationStatus.NEW,
                    ENotificationStatus.PROCESSING,
                    ENotificationStatus.ERROR),
                new Date(), getAddressTo(channel.getChannel(), data)
            );
        if (count > 0) {
          LOG.info("The event:{} for the channel:{} is still active in the queue",
              codeEvent, channel.getChannel());
          continue;
        }
      }

      MessageTemplateLocal local;
      Optional<MessageTemplate> localOptional =
          messageTemplateDAO.findOneTemplateByEventAndChannelIfSetAndLang(
              codeEvent,
              template.getChannel().getChannel(),
              language.getCode());
      if (!localOptional.isPresent()) {
        LOG.error(
            "Not found template for event:{} and channel:{} and companyId:{} and language:{}",
            codeEvent, channel.getChannel(), null, language.getCode());
        local = defaultMessageTemplateLocal;
      } else {
        local = localOptional.get().getLocal();
      }

      NotificationQueue queue = new NotificationQueue();
      queue.setChannel(channel);
      queue.setEvent(event);
      queue.setLanguage(language);
      queue.setStatus(ENotificationStatus.NEW);
      if (local.getTitle() != null && !local.getTitle().isEmpty()) {
        queue.setTitle(processor.process(local.getTitle(), data));
      }
      if (local.getBody() != null && !local.getBody().isEmpty()) {
        queue.setMessage(processor.process(local.getBody(), data));
      }
      Date expiredDate = null;
      if (event.getDaysNumberBeforeExpired() <= 0) {
        try {
          expiredDate = new SimpleDateFormat("dd.MM.yyyy").parse("31.12.9999");
        } catch (ParseException e) {
          //ignore it
        }
      } else {
        expiredDate = CommonUtils.convertLocalDateTimeToDate(
            LocalDateTime.now().plusDays(event.getDaysNumberBeforeExpired()));
      }
      queue.setAddressTo(getAddressTo(channel.getChannel(), data));
      queue.setExpireDate(expiredDate);
      queueRepository.save(queue);
      LOG.info("The event:{} for the channel:{} successfully registered in the queue",
          codeEvent, channel.getChannel());
    }
  }

  @Override
  public List<NotificationQueue> findAllQueuesByStatusInAndExpireDateGreaterThan(
      List<ENotificationStatus> statuses, Date date) {
    return queueRepository.findAllByStatusInAndExpireDateGreaterThan(statuses, date);
  }

  @Override
  @Transactional
  public NotificationQueue saveQueue(NotificationQueue queue) {
    return queueRepository.save(queue);
  }

  @Autowired
  @Qualifier("mailNotificationHandler")
  private NotificationHandler mailHandler;

  @Override
  @Transactional
  public void processQueue(NotificationQueue queue) {
    Map<String, Object> parameters = new HashMap<>();

    NotificationChannel.Code channel = queue.getChannel().getChannel();
    Optional<MessageViewTemplate> viewTemplateOptional =
        messageViewTemplateDAO.findByCompanyIdAndChannelAndLangCode(
            channel,
            queue.getLanguage().getCode());
    String message;
    if (viewTemplateOptional.isPresent()) {
      String logoUrl = blobDataService.getFileUrl(-1);
      message = processor.process(
          viewTemplateOptional.get().getViewTemplate(), new HashMap<String, String>() {
            private static final long serialVersionUID = 7937499290494633865L;

            {
              put("companyLogo", applicationCommonProperties.getUserPropertyHost() + logoUrl);
              put("title", queue.getTitle());
              put("body", queue.getMessage());
              if (viewTemplateOptional.get().getFooter() != null
                  && !viewTemplateOptional.get().getFooter().isEmpty()) {
                put("footer", viewTemplateOptional.get().getFooter());
              }
            }
          }
      );
    } else {
      message = queue.getMessage();
      LOG.warn("Not found a view template for the channel:{}", channel);
    }

    try {
      switch (channel) {
        case SMS:
          break;
        case EMAIL:
          parameters.clear();
          parameters.put(SUBJECT, queue.getTitle());
          parameters.put(MESSAGE, message);
          parameters.put(TO, queue.getAddressTo().split(","));
          mailHandler.send(parameters);
          break;
        case PUSH:
          break;
        case SYSTEM:
          break;
      }
      queue.setStatus(ENotificationStatus.FINISHED);
    } catch (Exception e) {
      LOG.error("Error sending a email", e);
      queue.setErrorMessage(
          e.getMessage().length() < 400 ? e.getMessage() : e.getMessage().substring(0, 400));
      queue.setStatus(ENotificationStatus.ERROR);
      if (queue.getErrorAttempts() == null) {
        queue.setErrorAttempts(1);
      } else {
        queue.setErrorAttempts(queue.getErrorAttempts() + 1);
      }
    }
    queueRepository.save(queue);
  }

  @Override
  public Long countQueuesByEventCodeAndChannelCodeAndStatusInAndExpireDateGreaterThanAndAddressTo(
      NotificationEvent.Code event,
      NotificationChannel.Code channel,
      List<ENotificationStatus> statuses,
      Date date,
      String addressTo) {
    return queueRepository
        .countByEventCodeAndChannelCodeAndStatusInAndExpireDateGreaterThanAndAddressTo(
            event,
            channel,
            statuses,
            date,
            addressTo);
  }

  private String getAddressTo(NotificationChannel.Code channel, Map<String, String> data) {
    switch (channel) {
      case SMS:
        return "";
      case EMAIL:
        return data.get(MergeTag.EMAIL.getValue());
      case PUSH:
        return "";
      case SYSTEM:
        return "";
      default:
        throw new UnsupportedOperationException();
    }
  }

  @Override
  @Cacheable(cacheNames = "notificationEventGroups", key = "{#scope,#language.code}")
  public List<NotificationEventGroup> findAllEventGroups(
      NotificationEvent.Scope scope, Language language) {
    return eventGroupRepository.findAll().stream()
        .filter(
            g -> (scope == Scope.SYSTEM ? g.getCode() == Code.SYSTEM : g.getCode() != Code.SYSTEM))
        .peek(g -> {
          g.setName(localizationService.getLocalTextByKeyAndLangOrUseDefault(
              g.getLocalKeyName(),
              language,
              g.getDefaultName()));
          g.setDescription(localizationService.getLocalTextByKeyAndLangOrUseDefault(
              g.getLocalKeyDescription(),
              language,
              g.getDefaultDescription()));
        })
        .sorted(Comparator.comparing(NotificationEventGroup::getName))
        .collect(Collectors.toList());
  }

  @Override
  public Page<NotificationEvent> findAllEventsByConstraint(NotificationEventConstraint constraint) {
    return notificationEventDAO.findAllByConstraint(constraint);
  }

  @Override
  @Cacheable(cacheNames = "notificationEvent", key = "#eventCode")
  public Optional<NotificationEvent> findEventByCode(NotificationEvent.Code eventCode) {
    return eventRepository.findById(eventCode);
  }

  @Override
  @Cacheable(cacheNames = "notificationChannel", key = "#channelCode")
  public Optional<NotificationChannel> findChannelByCode(NotificationChannel.Code channelCode) {
    return channelRepository.findById(channelCode);
  }

  @Override
  public Optional<MessageTemplate> findTemplateByEventAndChannelIfSetIfSetAndLang(
      NotificationEvent.Code eventCode,
      NotificationChannel.Code channelCode,
      String lang) {
    if (eventCode == null || lang == null || lang.isEmpty()) {
      throw new IllegalArgumentException("Obligatory parameters must be set");
    }
    return messageTemplateDAO.findOneTemplateByEventAndChannelIfSetAndLang(
        eventCode,
        channelCode,
        lang);
  }

  @Override
  @Cacheable(cacheNames = "notificationChannels", key = "#root.methodName")
  public List<NotificationChannel> findAllChannelsOrderByCode() {
    return channelRepository.findAllOrderByCode();
  }

  @Override
  public Optional<MessageTemplate> findTemplateByEventAndChannelIfSetWithEventAndChannelAndLocals(
      NotificationEvent.Code eventCode, NotificationChannel.Code channelCode) {
    return messageTemplateRepository
        .findByEventAndChannelWithEventAndChannelAndLocals(eventCode, channelCode);
  }

  @Override
  @Transactional
  public MessageViewTemplate saveViewTemplate(MessageViewTemplate viewTemplate) {
    return messageViewTemplateRepository.save(viewTemplate);
  }

  @Override
  @Transactional
  public MessageTemplate saveTemplate(MessageTemplate template) {
    return messageTemplateRepository.save(template);
  }

  @Override
  @Transactional
  public int deleteTemplateByEventAndChannelIfSetAndLangIfSet(
      NotificationEvent.Code event,
      NotificationChannel.Code channel,
      String langCode) {

    if (event == null || channel == null) {
      throw new IllegalArgumentException("Obligatory parameters must be set");
    }
    if (langCode == null) {
      int count = messageTemplateDAO.countLocalsByEventAndChannel(event, channel);
      if (count > 0) {
        if (messageTemplateRepository.deleteAllByEvent_EventAndChannel_Channel(event, channel)
            == 0) {
          count = 0;
        }
      }
      return count;
    } else {
      return messageTemplateDAO
          .deleteByEventAndChannelAndLangCode(event, channel, langCode);
    }
  }

  @Override
  @Transactional
  public int deleteAllWithStatusErrorAndErrorAttemptsGreaterThan(int errorAttempts) {
    return queueRepository.deleteAllWithStatusErrorAndErrorAttemptsGreaterThan(errorAttempts);
  }
}
