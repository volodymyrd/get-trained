package online.gettrained.frontend.web;

/**
 * Created by Volodymyr Dotsenko on 4/3/17.
 */
@Deprecated
public enum HttpStatusCodes {
  AUTH_EMAIL_ALREADY_EXIST(721),
  AUTH_NOT_FINISHED_REGISTRATION(722);

  private final int code;

  private HttpStatusCodes(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
