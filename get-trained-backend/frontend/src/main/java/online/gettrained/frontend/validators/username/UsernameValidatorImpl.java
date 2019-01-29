package online.gettrained.frontend.validators.username;

import online.gettrained.backend.domain.localization.Settings;
import online.gettrained.backend.exceptions.ErrorCode;
import online.gettrained.backend.repositories.user.UserRepository;
import online.gettrained.backend.services.localization.LocalizationService;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class UsernameValidatorImpl implements UsernameValidator {

  private final LocalizationService localizationService;
  private final UserRepository userRepository;

  public UsernameValidatorImpl(
    LocalizationService localizationService,
    UserRepository userRepository) {
    this.localizationService = localizationService;
    this.userRepository = userRepository;
  }

  @Override
  public ErrorCode validate(String username) {
    if (username == null || username.isEmpty()) {
      return ErrorCode.TOO_SHORT_USERNAME;
    }

    Optional<Settings> minLengthSetting =
      localizationService.findSettingByKey(Settings.Key.MIN_USERNAME_LENGTH.toString());
    if (minLengthSetting.isPresent()) {
      try {
        int minLength = Integer.valueOf(minLengthSetting.get().getSettingValue());

        if (username.length() < minLength) {
          return ErrorCode.TOO_SHORT_USERNAME;
        }

      } catch (NumberFormatException ex) {
        //ignore it
      }
    }

    if (username.contains("@")) {
      return ErrorCode.USERNAME_CONTAINS_ILLEGAL_SYMBOLS;
    }

    if (userRepository.countByUserName(username) > 0) {
      return ErrorCode.USERNAME_ALREADY_EXIST;
    }

    return ErrorCode.NO_ERRORS;
  }
}
