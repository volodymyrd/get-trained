package online.gettrained.backend.domain.contacts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Contact phones.
 */
@Entity
@Table(name = "CONTACT_PHONES")
public class ContactPhone implements Serializable {

  private static final long serialVersionUID = 7004839188615409065L;

  public ContactPhone() {
  }

  public ContactPhone(String phone) {
    this.phone = phone;
  }

  @Id
  @Column(name = "PHONE", nullable = false, length = 50)
  private String phone;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContactPhone)) {
      return false;
    }
    ContactPhone that = (ContactPhone) o;
    return Objects.equals(phone, that.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(phone);
  }
}
