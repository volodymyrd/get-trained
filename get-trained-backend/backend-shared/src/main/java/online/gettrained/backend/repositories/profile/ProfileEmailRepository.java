package online.gettrained.backend.repositories.profile;

import online.gettrained.backend.domain.profile.ProfileEmail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface ProfileEmailRepository extends JpaRepository<ProfileEmail, ProfileEmail.Pk> {

}
