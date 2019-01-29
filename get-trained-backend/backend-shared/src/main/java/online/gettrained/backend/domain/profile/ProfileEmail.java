package online.gettrained.backend.domain.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import online.gettrained.backend.domain.contacts.ContactEmail;

/**
 * Profile emails
 */
@Entity
@Table(name = "USR_PROFILE_EMAILS")
public class ProfileEmail implements Serializable {

  private static final long serialVersionUID = 8685583968844835595L;

  @Embeddable
  public static class Pk implements Serializable {

    private static final long serialVersionUID = 684319764989284774L;

    public Pk() {
    }

    public Pk(Profile profile, ContactEmail email) {
      this.profile = profile;
      this.email = email;
    }

    @ManyToOne
    @JoinColumn(name = "REF_PROFILE_ID", nullable = false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "EMAIL", nullable = false)
    private ContactEmail email;

    public Profile getProfile() {
      return profile;
    }

    public void setProfile(Profile profile) {
      this.profile = profile;
    }

    public ContactEmail getEmail() {
      return email;
    }

    public void setEmail(ContactEmail email) {
      this.email = email;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Pk)) {
        return false;
      }
      Pk pk = (Pk) o;
      return Objects.equals(profile, pk.profile) &&
          Objects.equals(email, pk.email);
    }

    @Override
    public int hashCode() {
      return Objects.hash(profile, email);
    }
  }

  public ProfileEmail() {
  }

  public ProfileEmail(Pk pk) {
    this.pk = pk;
  }

  @JsonIgnore
  @EmbeddedId
  private Pk pk;

  @Column(name = "DESCRIPTION", length = 100)
  private String description;

  public Pk getPk() {
    return pk;
  }

  public void setPk(Pk pk) {
    this.pk = pk;
  }

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
    if (!(o instanceof ProfileEmail)) {
      return false;
    }
    ProfileEmail that = (ProfileEmail) o;
    return Objects.equals(pk, that.pk);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pk);
  }
}
