package online.gettrained.backend.exceptions;

/**
 * General application exception with {@link ErrorCode}.
 */
public class ApplicationException extends Exception {

  private static final long serialVersionUID = -1815148301990454933L;

  private final ErrorInfoDto errorInfo;

  public ApplicationException(ErrorInfoDto errorInfo) {
    super(errorInfo.getMessage());
    this.errorInfo = errorInfo;
  }

  public ErrorInfoDto getErrorInfo() {
    return errorInfo;
  }
}
