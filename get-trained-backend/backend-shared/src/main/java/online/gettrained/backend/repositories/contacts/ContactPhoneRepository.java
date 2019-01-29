package online.gettrained.backend.repositories.contacts;

import online.gettrained.backend.domain.contacts.ContactPhone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface ContactPhoneRepository extends JpaRepository<ContactPhone, String> {

}
