package online.gettrained.frontend.services;

import java.util.Optional;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.services.cache.CacheService;
import online.gettrained.backend.services.user.UserService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class HelperServiceImpl implements HelperService {

  private final UserService userService;
  private final CacheService cacheService;

  public HelperServiceImpl(UserService userService, CacheService cacheService) {
    this.userService = userService;
    this.cacheService = cacheService;
  }

  @Override
  public void userCleanUp(String userName) {
    if (userName == null || userName.isEmpty()) {
      return;
    }

    Optional<User> userOptional = userService.findOneByEmail(userName);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      //TODO add eviction for all user's dashboards.
      cacheService.evictDashboard(user, null);
    }
  }
}
