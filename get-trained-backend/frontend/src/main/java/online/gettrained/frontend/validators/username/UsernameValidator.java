package online.gettrained.frontend.validators.username;

import online.gettrained.backend.exceptions.ErrorCode;

/**
 * Validate username
 */
public interface UsernameValidator {
    /**
     * Validate username
     *
     * @param username
     * @return ErrorCode
     */
    ErrorCode validate(String username);
}
