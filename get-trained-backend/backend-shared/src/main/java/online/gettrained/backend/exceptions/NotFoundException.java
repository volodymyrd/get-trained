package online.gettrained.backend.exceptions;

/**
 * No found exception.
 */
public class NotFoundException extends Exception {

  private static final long serialVersionUID = 1775789027950978903L;

  public NotFoundException() {
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
