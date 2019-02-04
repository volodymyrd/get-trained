package online.gettrained.frontend.web.dto;

import online.gettrained.backend.messages.TextCode;

import java.io.Serializable;

/**
 * Text info dto.
 */
public final class TextInfoDto implements Serializable {

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
    W
  }

  private final Type type;
  private final TextCode code;
  private final String message;

  public TextInfoDto(TextCode code, String message) {
    this.type = Type.S;
    this.code = code;
    this.message = message;
  }

  public TextInfoDto(Type type, TextCode code, String message) {
    this.type = type;
    this.code = code;
    this.message = message;
  }

  public Type getType() {
    return type;
  }

  public TextCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
