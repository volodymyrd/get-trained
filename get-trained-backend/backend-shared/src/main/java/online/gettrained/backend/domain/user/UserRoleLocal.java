package online.gettrained.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * User role localization
 */
@Embeddable
public class UserRoleLocal implements Serializable {

  private static final long serialVersionUID = -7603901031028918270L;


  public UserRoleLocal() {
  }

  public UserRoleLocal(String description) {
    this.description = description;
  }

  @JsonIgnore
  @Column(name = "DESCRIPTION", nullable = false, length = 300)
  private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserRoleLocal)) {
      return false;
    }
    UserRoleLocal that = (UserRoleLocal) o;
    return Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description);
  }

  @Override
  public String toString() {
    return "UserRoleLocal{" +
        "description='" + description + '\'' +
        '}';
  }
}
