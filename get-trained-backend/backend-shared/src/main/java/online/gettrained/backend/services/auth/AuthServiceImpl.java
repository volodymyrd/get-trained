package online.gettrained.backend.services.auth;

import java.util.List;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.domain.user.UserAction.Id;
import online.gettrained.backend.props.ApplicationCommonProperties;
import online.gettrained.backend.repositories.user.ActionDAO;
import online.gettrained.backend.repositories.user.UserRepository;
import online.gettrained.backend.utils.CommonUtils;
import online.gettrained.backend.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link AuthService}
 */
@Service
public class AuthServiceImpl implements AuthService {

  private final ApplicationCommonProperties applicationCommonProperties;
  private final ActionDAO actionDAO;
  private final UserRepository userRepository;

  public AuthServiceImpl(
      ApplicationCommonProperties applicationCommonProperties,
      ActionDAO actionDAO,
      UserRepository userRepository) {
    this.applicationCommonProperties = applicationCommonProperties;
    this.actionDAO = actionDAO;
    this.userRepository = userRepository;
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

  @Override
  public boolean isAllowedForUser(Long companyId, Long userId, Id actionId) {
    if (companyId == null || userId == null || actionId == null) {
      throw new IllegalArgumentException("Parameters must set");
    }
    return isAllowedForUser(companyId, userId, CommonUtils.immutableListOf(actionId));
  }

  @Override
  public boolean isAllowedForUser(Long companyId, Long userId, List<Id> actionIds) {
    if (companyId == null || userId == null || actionIds == null || actionIds.isEmpty()) {
      throw new IllegalArgumentException("Parameters must set");
    }
    return actionDAO.isAllowedForUser(companyId, userId, actionIds);
  }

  @Override
  public boolean isAllowedForMember(Long memberId, Id actionId) {
    if (memberId == null || actionId == null) {
      throw new IllegalArgumentException("Parameters must set");
    }
    return isAllowedForMember(memberId, CommonUtils.immutableListOf(actionId));
  }

  @Override
  public boolean isAllowedForMember(Long memberId, List<Id> actionIds) {
    return actionDAO.isAllowedForMember(memberId, actionIds);
  }
}
