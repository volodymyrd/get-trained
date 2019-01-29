package online.gettrained.backend.repositories.contacts;

import online.gettrained.backend.domain.contacts.ContactEmail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface ContactEmailRepository extends JpaRepository<ContactEmail, String> {

}
