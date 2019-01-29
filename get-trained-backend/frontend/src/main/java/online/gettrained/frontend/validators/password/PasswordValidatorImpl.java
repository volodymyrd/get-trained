package online.gettrained.frontend.validators.password;

import online.gettrained.backend.domain.localization.Settings;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.exceptions.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Password validator
 */
@Component
public class PasswordValidatorImpl implements PasswordValidator {
    private final LocalizationService localizationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordValidatorImpl(LocalizationService localizationService, PasswordEncoder passwordEncoder) {
        this.localizationService = localizationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ErrorCode validate(String password) {
        if (password == null || password.isEmpty())
            return ErrorCode.TOO_SHORT_PASSWORD;

        Optional<Settings> minLengthSetting =
                localizationService.findSettingByKey(Settings.Key.MIN_PASSWORD_LENGTH.toString());
        if (minLengthSetting.isPresent()) {
            try {
                int minLength = Integer.valueOf(minLengthSetting.get().getSettingValue());

                if (password.length() < minLength)
                    return ErrorCode.TOO_SHORT_PASSWORD;

            } catch (NumberFormatException ex) {
                //ignore it
            }
        }
        return ErrorCode.NO_ERRORS;
    }

    @Override
    public ErrorCode validate(String rawPassword, String encodePassword) {
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            return ErrorCode.WRONG_PASSWORD;
        }
        return ErrorCode.NO_ERRORS;
    }

    @Override
    public ErrorCode validate(String oldRawPassword, String oldEncodePassword, String newPassword,
                              String confirmPassword) {

        ErrorCode errorCode = validate(newPassword);
        if (errorCode.isError()) {
            return errorCode;
        }

        if (oldRawPassword != null && !oldRawPassword.isEmpty()
                && oldEncodePassword != null && !oldEncodePassword.isEmpty()) {
            errorCode = validate(oldRawPassword, oldEncodePassword);
            if (errorCode.isError()) {
                return errorCode;
            }
        }

        if (!newPassword.equals(confirmPassword)) {
            return ErrorCode.PASSWORD_DOES_NOT_MATCH_CONFIRM_PASSWORD;
        }

        return ErrorCode.NO_ERRORS;
    }
}
