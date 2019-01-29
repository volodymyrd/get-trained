package online.gettrained.frontend.validators.email;

import online.gettrained.backend.exceptions.ErrorCode;

/**
 * Validate email
 */
public interface EmailValidator {
    /**
     * Validate email
     *
     * @param email
     * @return ErrorCode
     */
    ErrorCode validate(String email);

    /**
     * Validate email
     *
     * @param email
     * @param existingValidation check that the email already exist in the system
     * @return ErrorCode
     */
    ErrorCode validate(String email, boolean existingValidation);
}
