package online.gettrained.backend.repositories.profile;

import online.gettrained.backend.domain.profile.ProfilePhone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface ProfilePhoneRepository extends JpaRepository<ProfilePhone, ProfilePhone.Pk> {

}
