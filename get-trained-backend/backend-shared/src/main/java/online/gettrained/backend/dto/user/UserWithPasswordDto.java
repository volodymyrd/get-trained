package online.gettrained.backend.dto.user;

import java.util.Date;
import online.gettrained.backend.domain.user.User.EStatus;

/**
 * User dto with password
 */
public class UserWithPasswordDto extends UserDto {

  private static final long serialVersionUID = -6238116426793534637L;

  private final String password;
  private final String passwordConfirmation;

  public UserWithPasswordDto(Long id, String email, String userName, Date expired,
      Date lastVisit, boolean locked, EStatus status,
      Date dateCreate, Date dateUpdate, String firstName,
      String lastName, String fullName, String password, String passwordConfirmation) {
    super(id, email, userName, expired, lastVisit, locked, status, dateCreate, dateUpdate,
        firstName,
        lastName, fullName);
    this.password = password;
    this.passwordConfirmation = passwordConfirmation;
  }

  public String getPassword() {
    return password;
  }

  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }
}
