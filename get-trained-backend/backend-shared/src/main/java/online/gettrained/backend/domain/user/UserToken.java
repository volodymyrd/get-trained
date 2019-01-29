package online.gettrained.backend.domain.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * User tokens
 */
@Entity
@Table(name = "USR_USER_TOKENS",
    indexes = {@Index(name = "I_TOKEN", columnList = "TOKEN", unique = true)})
public class UserToken implements Serializable {

  private static final long serialVersionUID = -5274059470255460293L;

  public enum Type {
    CONFIRM_REGISTRATION_TOKEN
  }

  @Embeddable
  public static class Pk implements Serializable {

    private static final long serialVersionUID = -2776387713423747152L;

    public Pk() {
    }

    public Pk(User user, Type type) {
      this.user = user;
      this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REF_USER_ID", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "TOKEN_TYPE", nullable = false, length = 50)
    private Type type;

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public Type getType() {
      return type;
    }

    public void setType(Type type) {
      this.type = type;
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
      return Objects.equals(user, pk.user) &&
          type == pk.type;
    }

    @Override
    public int hashCode() {
      return Objects.hash(user, type);
    }
  }

  public UserToken() {
  }

  public UserToken(User user, Type type, Date expireDate, String token) {
    this.pk = new UserToken.Pk(user, type);
    this.expireDate = expireDate;
    this.token = token;
  }

  @EmbeddedId
  private Pk pk;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "EXPIRE_DATE", nullable = false)
  private Date expireDate;

  @Column(name = "TOKEN", nullable = false, length = 100)
  private String token;

  public Pk getPk() {
    return pk;
  }

  public void setPk(Pk pk) {
    this.pk = pk;
  }

  public Date getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserToken)) {
      return false;
    }
    UserToken userToken = (UserToken) o;
    return Objects.equals(pk, userToken.pk);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pk);
  }
}
