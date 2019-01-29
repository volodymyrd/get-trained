package online.gettrained.frontend.web.dto;

import online.gettrained.backend.messages.TextCode;

import java.io.Serializable;

/**
 * Text info dto
 */
public class TextInfoDto implements Serializable {
    private static final long serialVersionUID = 6675667389765791924L;

    private final TextCode code;
    private final String message;

    public TextInfoDto(TextCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public TextCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
