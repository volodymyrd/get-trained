package online.gettrained.frontend.web.dto;

import java.io.Serializable;

/**
 * The user theme
 */
public class UserTheme implements Serializable {

  private static final long serialVersionUID = 3126762478292320448L;

  private String name;

  public UserTheme() {
  }

  public UserTheme(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
