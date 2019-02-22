package online.gettrained.backend.dto;

import java.io.Serializable;
import online.gettrained.backend.messages.MessageCode;
import online.gettrained.backend.messages.TextCode;

/**
 * Text info dto.
 */
public class TextInfoDto implements Serializable {

  private static final long serialVersionUID = 6675667389765791924L;

  public enum Type {
    /**
     * Success.
     */
    S,
    /**
     * Info.
     */
    I,
    /**
     * Warning.
     */
    W,
    /**
     * Error.
     */
    E,
  }

  private final Type type;
  private final MessageCode code;
  private final String message;

  public TextInfoDto(TextCode code, String message) {
    this(Type.S, code, message);
  }

  public TextInfoDto(Type type, MessageCode code, String message) {
    this.type = type;
    this.code = code;
    this.message = message;
  }

  public Type getType() {
    return type;
  }

  public MessageCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
