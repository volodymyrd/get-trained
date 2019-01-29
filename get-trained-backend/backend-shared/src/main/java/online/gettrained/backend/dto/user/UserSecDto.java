package online.gettrained.backend.dto.user;

import java.io.Serializable;

/**
 * User security (email, username etc) data dto
 */
public class UserSecDto implements Serializable {

  private static final long serialVersionUID = 1012132429220019763L;

  private String userName;
  private String email;
  private String password;
  private String newPassword;
  private String confirmPassword;

  public UserSecDto() {
  }

  public UserSecDto(String userName, String email) {
    this.userName = userName;
    this.email = email;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public String toString() {
    return "UserSecDto{" +
        "userName='" + userName + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
