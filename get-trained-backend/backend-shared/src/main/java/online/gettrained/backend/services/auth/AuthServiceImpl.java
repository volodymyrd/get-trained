package online.gettrained.backend.services.auth;

import java.util.List;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.domain.user.UserAction.Id;
import online.gettrained.backend.repositories.user.ActionDAO;
import online.gettrained.backend.utils.CommonUtils;
import online.gettrained.backend.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link AuthService}
 */
@Service
public class AuthServiceImpl implements AuthService {

  private final ActionDAO actionDAO;

  public AuthServiceImpl(ActionDAO actionDAO) {
    this.actionDAO = actionDAO;
  }

  @Override
  public User getCurrentUser() {
    return SecurityUtils.getCurrentUser();
  }

  @Override
  public User getCurrentUserOrException() {
    return SecurityUtils.getCurrentUserOrException();
  }

  @Override
  public void setCurrentUser(User user) {
    SecurityUtils.setCurrentUser(user);
  }

  @Override
  public boolean isAllowedForUser(Long userId, Id actionId) {
    if (userId == null || actionId == null) {
      throw new IllegalArgumentException("Parameters must set");
    }
    return isAllowedForUser(userId, CommonUtils.immutableListOf(actionId));
  }

  @Override
  public boolean isAllowedForUser(Long userId, List<Id> actionIds) {
    if (userId == null || actionIds == null || actionIds.isEmpty()) {
      throw new IllegalArgumentException("Parameters must set");
    }
    return actionDAO.isAllowedForUser(userId, actionIds);
  }
}
