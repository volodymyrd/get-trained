package online.gettrained.frontend.web.dto;

/**
 * A dto contains a flag information {@code true} or {@code false}.
 */
public class FlagResponse {

  private final boolean flag;

  public FlagResponse(boolean flag) {
    this.flag = flag;
  }

  public boolean isFlag() {
    return flag;
  }
}
