package online.gettrained.backend.exceptions;

import java.io.Serializable;

/**
 * Error info dto
 */
public class ErrorInfoDto implements Serializable {

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

  public String toJSON() {
    StringBuilder builder = new StringBuilder("{");
    builder.append("\"code\":\"");
    builder.append(code.name());
    builder.append("\",\"message\":\"");
    builder.append(message);
    builder.append("\"}");
    return builder.toString();
  }
}
