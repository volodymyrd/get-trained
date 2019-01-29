package online.gettrained.backend.repositories.notif;

import online.gettrained.backend.domain.notif.ENotificationStatus;
import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.domain.notif.NotificationQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface NotificationQueueRepository extends JpaRepository<NotificationQueue, Long> {

  List<NotificationQueue> findAllByStatusInAndExpireDateGreaterThan(
      List<ENotificationStatus> statuses, Date date);

  @Query("SELECT q FROM NotificationQueue q INNER JOIN FETCH q.event INNER JOIN FETCH q.channel")
  List<NotificationQueue> findAllWithEventsAndChannels();

  @Query("SELECT COUNT(q) FROM NotificationQueue q "
      + " WHERE q.event.event=:event AND q.channel.channel=:channel "
      + " AND q.status IN :statuses AND q.expireDate > :date AND q.addressTo=:addressTo")
  Long countByEventCodeAndChannelCodeAndStatusInAndExpireDateGreaterThanAndAddressTo(
      @Param("event") NotificationEvent.Code event,
      @Param("channel") NotificationChannel.Code channel,
      @Param("statuses") List<ENotificationStatus> statuses,
      @Param("date") Date date,
      @Param("addressTo") String addressTo);

  @Modifying
  @Query("DELETE FROM NotificationQueue q WHERE q.status='ERROR' AND q.errorAttempts > :errorAttempts")
  int deleteAllWithStatusErrorAndErrorAttemptsGreaterThan(
      @Param("errorAttempts") Integer errorAttempts);
}
