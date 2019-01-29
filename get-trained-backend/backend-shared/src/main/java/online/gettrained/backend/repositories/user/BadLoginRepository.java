package online.gettrained.backend.repositories.user;

import online.gettrained.backend.domain.user.BadLogin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link BadLogin}.
 */
public interface BadLoginRepository extends JpaRepository<BadLogin, Long> {

}
