package online.gettrained.backend.exceptions;

import online.gettrained.backend.messages.MessageCode;

/**
 * Error codes for localization errors
 */
public enum ErrorCode implements MessageCode {
  NO_ERRORS("no_errors"),

  SOMETHING_WENT_WRONG("be_error.something_went_wrong"),

  NO_AUTHORITY("be_error.no_authority"),

  FILE_SIZE_LIMIT_EXCEEDED("be_error.file_size_limit_exceeded"),

  WRONG_PASSWORD("be_error.wrong_password"),
  TOO_SHORT_PASSWORD("be_error.too_short_password"),
  PASSWORD_DOES_NOT_MATCH_CONFIRM_PASSWORD("be_error.password_does_not_match_confirm_password"),
  INVALID_EMAIL("be_error.invalid_email"),
  EMAIL_ALREADY_EXIST("be_error.email_already_exist"),
  TOO_SHORT_USERNAME("be_error.too_short_username"),
  USERNAME_CONTAINS_ILLEGAL_SYMBOLS("be_error.username_contains_illegal_symbols"),
  USERNAME_ALREADY_EXIST("be_error.username_already_exist"),

  USER_PROFILE_AVATAR_EXCEED_MAX_SIZE("be_error.user_profile_avatar_exceed_max_size"),

  ACTIVITY_NOT_FOUND_TRAINEE_WITH_EMAIL("be_error.activity_not_found_trainee_with_email"),
  ACTIVITY_CANNOT_BE_TRAINER_FOR_YOURSELF("be_error.activity_cannot_be_trainer_for_yourself"),

  TRAINEE_PROFILE_MEASURE_EXISTS("be_error.trainee_profile_measure_exists");


  private final String text;

  /**
   *
   */
  ErrorCode(final String text) {
    this.text = text;
  }

  public boolean isError() {
    return !text.equals(NO_ERRORS.toString());
  }


  @Override
  public String toString() {
    return text;
  }
}
