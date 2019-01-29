package online.gettrained.frontend.validators.email;

import online.gettrained.backend.utils.ValidatorUtils;
import online.gettrained.backend.exceptions.ErrorCode;
import online.gettrained.backend.repositories.user.UserRepository;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class EmailValidatorImpl implements EmailValidator {


  private final UserRepository userRepository;

  public EmailValidatorImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public ErrorCode validate(String email) {
    return validate(email, false);
  }

  @Override
  public ErrorCode validate(String email, boolean existingValidation) {
    if (email == null || email.isEmpty()) {
      return ErrorCode.INVALID_EMAIL;
    }

    if (!ValidatorUtils.isEmail(email)) {
      return ErrorCode.INVALID_EMAIL;
    }

    if (existingValidation && userRepository.countByEmail(email) > 0) {
      return ErrorCode.EMAIL_ALREADY_EXIST;
    }

    return ErrorCode.NO_ERRORS;
  }
}
