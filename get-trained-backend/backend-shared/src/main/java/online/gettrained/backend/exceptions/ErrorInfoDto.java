package online.gettrained.backend.exceptions;

import online.gettrained.backend.dto.TextInfoDto;

/**
 * Error info dto.
 */
public final class ErrorInfoDto extends TextInfoDto {

  private static final long serialVersionUID = 6675667389765791924L;

  private final ErrorCode code;
  private final String message;

  public ErrorInfoDto(ErrorCode code, String message) {
    super(Type.E, code, message);
    this.code = code;
    this.message = message;
  }

  public ErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  String toJSON() {
    return "{" + "\"code\":\""
        + code.name()
        + "\",\"message\":\""
        + message
        + "\"}";
  }
}
