package online.gettrained.backend.dto.user;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static online.gettrained.backend.utils.DateUtils.DEFAULT_TIME_ZONE;
import static online.gettrained.backend.utils.DateUtils.LONGER_DATE_FORMAT;
import static online.gettrained.backend.utils.DateUtils.SHORT_DATE_FORMAT;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import online.gettrained.backend.domain.user.User.EStatus;

/**
 * User dto.
 */
public class UserDto implements Serializable {

  private static final long serialVersionUID = -443037859383827569L;

  private final Long id;
  private final String email;
  private final String userName;
  private final String firstName;
  private final String lastName;
  private final String fullName;
  @JsonFormat(shape = STRING, pattern = SHORT_DATE_FORMAT, timezone = DEFAULT_TIME_ZONE)
  private final Date expired;
  @JsonFormat(shape = STRING, pattern = LONGER_DATE_FORMAT, timezone = DEFAULT_TIME_ZONE)
  private final Date lastVisit;
  private final boolean locked;
  private final EStatus status;
  @JsonFormat(shape = STRING, pattern = LONGER_DATE_FORMAT, timezone = DEFAULT_TIME_ZONE)
  private final Date dateCreate;
  @JsonFormat(shape = STRING, pattern = LONGER_DATE_FORMAT, timezone = DEFAULT_TIME_ZONE)
  private final Date dateUpdate;

  public UserDto(
      Long id,
      String email,
      String userName,
      Date expired,
      Date lastVisit,
      boolean locked,
      EStatus status,
      Date dateCreate,
      Date dateUpdate,
      String fullName,
      String firstName,
      String lastName) {
    this.id = id;
    this.email = email;
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.fullName = fullName;
    this.expired = expired;
    this.lastVisit = lastVisit;
    this.locked = locked;
    this.status = status;
    this.dateCreate = dateCreate;
    this.dateUpdate = dateUpdate;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getUserName() {
    return userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    return fullName;
  }

  public Date getExpired() {
    return expired;
  }

  public Date getLastVisit() {
    return lastVisit;
  }

  public boolean isLocked() {
    return locked;
  }

  public EStatus getStatus() {
    return status;
  }

  public Date getDateCreate() {
    return dateCreate;
  }

  public Date getDateUpdate() {
    return dateUpdate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserDto)) {
      return false;
    }
    UserDto userDto = (UserDto) o;
    return locked == userDto.locked &&
        Objects.equals(id, userDto.id) &&
        Objects.equals(email, userDto.email) &&
        Objects.equals(fullName, userDto.fullName) &&
        Objects.equals(expired, userDto.expired) &&
        Objects.equals(lastVisit, userDto.lastVisit) &&
        status == userDto.status &&
        Objects.equals(dateCreate, userDto.dateCreate) &&
        Objects.equals(dateUpdate, userDto.dateUpdate);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(id, email, fullName, expired, lastVisit, locked, status, dateCreate, dateUpdate);
  }

  @Override
  public String toString() {
    return "UserDto{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", fullName='" + fullName + '\'' +
        ", expired=" + expired +
        ", lastVisit=" + lastVisit +
        ", locked=" + locked +
        ", status=" + status +
        ", dateCreate=" + dateCreate +
        ", dateUpdate=" + dateUpdate +
        '}';
  }
}
