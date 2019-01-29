package online.gettrained.backend.repositories.notif.template;

import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.domain.notif.NotificationEvent.Code;
import online.gettrained.backend.domain.notif.templates.MessageTemplate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for {@link MessageTemplate}.
 */
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {

  List<MessageTemplate> findAllByEvent_Event(Code codeEvent);

  @Query("SELECT t FROM MessageTemplate t JOIN FETCH t.event JOIN FETCH t.channel "
      + " JOIN FETCH t.userLastChanged JOIN FETCH t.localMap "
      + " WHERE t.id=:templateId")
  Optional<MessageTemplate> findTemplateByIdWithEventAndChannelAndUseLastChangedAndLocals(
      @Param("templateId") Long templateId);

  @Query("SELECT t FROM MessageTemplate t JOIN FETCH t.event JOIN FETCH t.channel "
      + " LEFT JOIN FETCH t.localMap "
      + "WHERE t.event.event=:eventCode AND t.channel.channel=:channelCode")
  Optional<MessageTemplate> findByEventAndChannelWithEventAndChannelAndLocals(
      @Param("eventCode") NotificationEvent.Code eventCode,
      @Param("channelCode") NotificationChannel.Code channelCode);

  List<MessageTemplate> findAllByEvent_EventAndChannel_Channel(
      NotificationEvent.Code event,
      NotificationChannel.Code channel);

  long countByEvent_EventAndChannel_Channel(
      NotificationEvent.Code event,
      NotificationChannel.Code channel);


  int deleteAllByEvent_EventAndChannel_Channel(
      NotificationEvent.Code event,
      NotificationChannel.Code channel);
}
