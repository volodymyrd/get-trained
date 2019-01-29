package online.gettrained.backend.repositories.notif;

import online.gettrained.backend.domain.notif.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface NotificationEventRepository extends
    JpaRepository<NotificationEvent, NotificationEvent.Code> {

}
