package online.gettrained.backend.repositories.notif;

import online.gettrained.backend.domain.notif.NotificationEventGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface NotificationEventGroupRepository extends
    JpaRepository<NotificationEventGroup, NotificationEventGroup.Code> {

}
