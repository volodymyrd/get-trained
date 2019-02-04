package online.gettrained.backend.exceptions;

import java.io.Serializable;

/**
 * Error info dto.
 */
public final class ErrorInfoDto implements Serializable {

  private static final long serialVersionUID = 6675667389765791924L;

  private final ErrorCode code;
  private final String message;

  public ErrorInfoDto(ErrorCode code, String message) {
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
