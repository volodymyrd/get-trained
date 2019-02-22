package online.gettrained.backend.exceptions;

import online.gettrained.backend.dto.TextInfoDto;

/**
 * General application exception with {@link ErrorCode}.
 */
public class ApplicationException extends Exception {

  private static final long serialVersionUID = -1815148301990454933L;

  private final TextInfoDto info;

  public ApplicationException(TextInfoDto info) {
    super(info.getMessage());
    this.info = info;
  }

  public TextInfoDto getInfo() {
    return info;
  }
}
