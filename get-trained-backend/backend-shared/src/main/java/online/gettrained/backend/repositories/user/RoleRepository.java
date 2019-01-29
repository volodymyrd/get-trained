package online.gettrained.backend.repositories.user;

import online.gettrained.backend.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {

  String BERIZE_ALL_ROLE = "BERIZE_ALL";
  String ADMIN_ROLE = "ROLE_ADMIN";
  String ACTUATOR_ROLE = "ACTUATOR";
  String USER_ROLE = "ROLE_USER";
  String COMPANY_ADMIN_ROLE = "ROLE_COMPANY_ADMIN";
  String COMPANY_HR_ROLE = "ROLE_COMPANY_HR";

  Optional<UserRole> findByName(String name);

  List<UserRole> findAllByScopeIn(List<UserRole.Scope> scopes);
}
