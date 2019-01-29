package online.gettrained.backend.services.contacts;

import online.gettrained.backend.domain.contacts.ContactEmail;
import online.gettrained.backend.domain.contacts.ContactPhone;

/**
 *
 */
public interface ContactsService {

  ContactEmail getEmailAndAddIfNotExist(String email);

  ContactPhone getPhoneAndAddIfNotExist(String phone);
}
