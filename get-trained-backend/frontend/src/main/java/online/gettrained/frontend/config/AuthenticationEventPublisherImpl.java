package online.gettrained.frontend.config;

import online.gettrained.backend.props.ApplicationCommonProperties;
import online.gettrained.backend.domain.user.BadLogin;
import online.gettrained.backend.domain.user.CurrentUser;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.repositories.user.BadLoginRepository;
import online.gettrained.backend.repositories.user.UserRepository;
import online.gettrained.frontend.services.HelperService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Component
public class AuthenticationEventPublisherImpl implements AuthenticationEventPublisher {

  private static final Logger LOG = LoggerFactory.getLogger(AuthenticationEventPublisherImpl.class);

  private final ApplicationCommonProperties applicationCommonProperties;
  private final UserRepository userRepository;
  private final BadLoginRepository badLoginRepository;
  private final HelperService helperService;

  public AuthenticationEventPublisherImpl(
      ApplicationCommonProperties applicationCommonProperties,
      UserRepository userRepository,
      BadLoginRepository badLoginRepository, HelperService helperService) {
    this.applicationCommonProperties = applicationCommonProperties;
    this.userRepository = userRepository;
    this.badLoginRepository = badLoginRepository;
    this.helperService = helperService;
  }

  @Override
  public void publishAuthenticationSuccess(Authentication authentication) {
  }

  @Override
  @Transactional
  public void publishAuthenticationFailure(
      AuthenticationException exception, Authentication authentication) {
    final int maxLengthForReason = 255;

    String userName;
    if (authentication.getPrincipal() instanceof String) {
      userName = (String) authentication.getPrincipal();
    } else if (authentication.getPrincipal() instanceof CurrentUser) {
      userName = ((CurrentUser) authentication.getPrincipal()).getUsername();
      helperService.userCleanUp(userName);
    } else {
      LOG.error("Unknown object of principal:{}", authentication.getPrincipal().getClass());
      return;
    }

    LOG.info("Registered bad login for user:{}", userName);

    Optional<User> userOptional;
    if (userName != null && !userName.isEmpty()) {
      if (userName.contains("@")) {
        userOptional = userRepository.findOneByEmail(userName.toLowerCase());
      } else {
        userOptional = userRepository.findOneByUserName(userName.toLowerCase());
      }
    } else {
      userOptional = Optional.empty();
    }

    BadLogin badLogin = new BadLogin();
    badLogin.setUserName(userName);
    badLogin.setUser(userOptional.orElse(null));
    if (authentication.getDetails() instanceof WebAuthenticationDetails) {
      badLogin.setIp(((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress());
    }
    if (exception.getMessage() != null && !exception.getMessage().isEmpty()) {
      if (exception.getMessage().length() < maxLengthForReason) {
        badLogin.setReason(exception.getMessage());
      } else {
        badLogin.setReason(exception.getMessage().substring(maxLengthForReason));
      }
    } else {
      badLogin.setReason("Unknown reason");
    }

    if (exception instanceof BadCredentialsException) {
      if (userOptional.isPresent()) {
        User user = userOptional.get();
        if (user.getBadLoginAttempts() != null
            && user.getBadLoginAttempts() >= applicationCommonProperties
            .getMaxBadLoginAttempts()) {
          userRepository.lockUser(user.getId());
          LOG.info("User {} was locked, because exceeded max bad login attempts {}",
              userName, applicationCommonProperties.getMaxBadLoginAttempts());
          //TODO send email about locking.
        } else if (user.getBadLoginAttempts() == null) {
          userRepository.setToOneBadLoginAttempts(user.getId());
        } else {
          userRepository.incrementBadLoginAttempts(user.getId());
        }
      }
    }

    badLoginRepository.save(badLogin);
  }
}
