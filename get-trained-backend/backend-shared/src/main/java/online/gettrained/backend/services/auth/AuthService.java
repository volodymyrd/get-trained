package online.gettrained.backend.services.auth;

import java.util.List;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.domain.user.UserAction.Id;

/**
 * Auth service
 */
public interface AuthService {

  User getCurrentUser();

  User getCurrentUserOrException();

  void setCurrentUser(User user);

  boolean isAllowedForUser(Long userId, Id actionId);

  boolean isAllowedForUser(Long userId, List<Id> actionIds);
}
