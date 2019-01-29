package online.gettrained.backend.repositories.notif;

import online.gettrained.backend.domain.notif.NotificationChannel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 */
public interface NotificationChannelRepository extends
    JpaRepository<NotificationChannel, NotificationChannel.Code> {

  @Query("SELECT c FROM NotificationChannel c ORDER BY c.channel")
  List<NotificationChannel> findAllOrderByCode();
}
