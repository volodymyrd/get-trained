package online.gettrained.backend.domain.user;

import online.gettrained.backend.domain.NonUpdateBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Keep information about all bad logins in the system.
 */
@Entity
@Table(name = "USR_BAD_LOGINS")
public class BadLogin extends NonUpdateBaseEntity {

  private static final long serialVersionUID = -6399278656248414631L;

  @Column(name = "IP", length = 50)
  private String ip;

  @Column(name = "USER_NAME", length = 100)
  private String userName;

  @Column(name = "REASON")
  private String reason;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REF_USER_ID")
  private User user;

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
