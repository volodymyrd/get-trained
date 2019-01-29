package online.gettrained.backend.services.contacts;

import online.gettrained.backend.domain.contacts.ContactEmail;
import online.gettrained.backend.domain.contacts.ContactPhone;
import online.gettrained.backend.repositories.contacts.ContactEmailRepository;
import online.gettrained.backend.repositories.contacts.ContactPhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class ContactsServiceImpl implements ContactsService {

  private final ContactEmailRepository contactEmailRepository;
  private final ContactPhoneRepository contactPhoneRepository;

  public ContactsServiceImpl(
      ContactEmailRepository contactEmailRepository,
      ContactPhoneRepository contactPhoneRepository) {
    this.contactEmailRepository = contactEmailRepository;
    this.contactPhoneRepository = contactPhoneRepository;
  }

  @Transactional
  @Override
  public ContactEmail getEmailAndAddIfNotExist(String email) {
    return contactEmailRepository.findById(email)
        .orElseGet(() -> contactEmailRepository.save(new ContactEmail(email)));
  }

  @Transactional
  @Override
  public ContactPhone getPhoneAndAddIfNotExist(String phone) {
    return contactPhoneRepository.findById(phone)
        .orElseGet(() -> contactPhoneRepository.save(new ContactPhone(phone)));
  }
}
