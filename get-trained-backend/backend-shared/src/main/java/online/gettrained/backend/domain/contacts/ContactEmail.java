package online.gettrained.backend.domain.contacts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Objects;

/**
 * Contact emails
 */
@Entity
@Table(name = "CONTACT_EMAILS")
public class ContactEmail implements Serializable {

  private static final long serialVersionUID = 7004839188615409065L;

  public ContactEmail() {
  }

  public ContactEmail(String email) {
    this.email = email;
  }

  @Id
  @Email
  @Column(name = "EMAIL", nullable = false, length = 255)
  private String email;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContactEmail)) {
      return false;
    }
    ContactEmail that = (ContactEmail) o;
    return Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);
  }
}
