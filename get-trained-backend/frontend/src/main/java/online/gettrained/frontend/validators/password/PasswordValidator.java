package online.gettrained.frontend.validators.password;

import online.gettrained.backend.exceptions.ErrorCode;

/**
 * Password validator
 */
public interface PasswordValidator {
    /**
     * Validate password strength
     *
     * @param password
     * @return ErrorCode
     */
    ErrorCode validate(String password);

    /**
     * Validate the rawPassword match the encodePassword
     *
     * @param rawPassword
     * @param encodePassword
     * @return
     */
    ErrorCode validate(String rawPassword, String encodePassword);

    /**
     * Validate the strength of the newPassword, the newPassword match the confirmPassword and if the
     * oldRawPassword is not empty and the oldEncodePassword is not empty
     * validate the oldRawPassword match the oldEncodePassword
     *
     * @param oldRawPassword
     * @param oldEncodePassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    ErrorCode validate(String oldRawPassword, String oldEncodePassword, String newPassword, String confirmPassword);
}
