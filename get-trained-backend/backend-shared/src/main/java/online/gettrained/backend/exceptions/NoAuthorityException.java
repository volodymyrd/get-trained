package online.gettrained.backend.exceptions;

/**
 * No authority exception.
 */
public class NoAuthorityException extends Exception {

  private static final long serialVersionUID = 1775789027950978903L;

  public NoAuthorityException() {
  }

  public NoAuthorityException(String message) {
    super(message);
  }
}
