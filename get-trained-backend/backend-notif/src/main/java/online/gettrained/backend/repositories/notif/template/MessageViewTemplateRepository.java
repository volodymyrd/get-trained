package online.gettrained.backend.repositories.notif.template;

import online.gettrained.backend.domain.notif.templates.MessageViewTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link MessageViewTemplate}.
 */
public interface MessageViewTemplateRepository extends JpaRepository<MessageViewTemplate, Long> {

}
