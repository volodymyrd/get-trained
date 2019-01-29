package online.gettrained.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import online.gettrained.backend.domain.BaseEntity;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.localization.Language;
import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * User
 */
@Entity
@Table(name = "USR_USERS")
public class User extends BaseEntity {

  public enum EStatus {
    NEW, REGISTERED, DELETED
  }

  private static final long serialVersionUID = 1L;


  public User() {
  }

  public User(String email) {
    this(email, email, "", null);
  }

  public User(String email, String password, String firstName) {
    this(email, email, password, new Profile(firstName));
  }

  public User(String userName, String email, String password, Profile profile) {
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.profile = profile;
  }

  @Audited(withModifiedFlag = true)
  @Column(name = "USER_NAME", nullable = false, unique = true, length = 255)
  private String userName;

  @Audited(withModifiedFlag = true)
  @Email
  @Column(name = "EMAIL", nullable = false, unique = true, length = 255)
  private String email;

  @JsonIgnore
  @Audited(withModifiedFlag = true)
  @Column(name = "PASSWORD", nullable = false, length = 255)
  private String password;

  @JsonIgnore
  @Audited(withModifiedFlag = true)
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd.MM.yyyy")
  @Column(name = "EXPIRED", nullable = false)
  private Date expired = new GregorianCalendar(9999, 11, 31).getTime();

  @JsonIgnore
  @Audited(withModifiedFlag = true)
  @Column(name = "LOCKED")
  private Boolean locked = false;

  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
  @Column(name = "LAST_VISIT")
  private Date lastVisit;

  @JsonIgnore
  @Column(name = "AGREED_WITH_TAC", nullable = false)
  private Boolean agreedWithTac = true;

  @JsonIgnore
  @Audited(withModifiedFlag = true)
  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", nullable = false)
  private EStatus status = EStatus.NEW;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "USR_USERS_ROLES",
      joinColumns = @JoinColumn(name = "REF_USER_ID"),
      inverseJoinColumns = @JoinColumn(name = "REF_ROLE_ID"))
  private Set<UserRole> roles = new HashSet<>();

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REF_PROFILE_ID", nullable = false)
  private Profile profile;

  @OneToOne
  @JoinColumn(name = "LANG_CODE")
  private Language loginLang;

  @JsonIgnore
  @Column(name = "IS_LDAP")
  private Boolean ldap;

  @JsonIgnore
  @Column(name = "LOGIN_IP", length = 50)
  private String loginIp;

  @JsonIgnore
  @Column(name = "BAD_LOGIN_ATTEMPTS")
  private Integer badLoginAttempts;

  @Transient
  private String newPassword;

  @Transient
  private String confirmPassword;

  @Transient
  private Set<String> userRoles = new HashSet<>();

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

  public Date getExpired() {
    return expired;
  }

  public void setExpired(Date expired) {
    this.expired = expired;
  }

  public Boolean getLocked() {
    return locked;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public Date getLastVisit() {
    return lastVisit;
  }

  public void setLastVisit(Date lastVisit) {
    this.lastVisit = lastVisit;
  }

  public Boolean getAgreedWithTac() {
    return agreedWithTac;
  }

  public void setAgreedWithTac(Boolean agreedWithTac) {
    this.agreedWithTac = agreedWithTac;
  }

  public EStatus getStatus() {
    return status;
  }

  public void setStatus(EStatus status) {
    this.status = status;
  }

  public Set<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }

  public Boolean getLdap() {
    return ldap;
  }

  public void setLdap(Boolean ldap) {
    this.ldap = ldap;
  }

  public String getLoginIp() {
    return loginIp;
  }

  public void setLoginIp(String loginIp) {
    this.loginIp = loginIp;
  }

  public Integer getBadLoginAttempts() {
    return badLoginAttempts;
  }

  public void setBadLoginAttempts(Integer badLoginAttempts) {
    this.badLoginAttempts = badLoginAttempts;
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

  public Language getLoginLang() {
    return loginLang;
  }

  public void setLoginLang(Language loginLang) {
    this.loginLang = loginLang;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public Set<String> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Set<String> userRoles) {
    this.userRoles = userRoles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(userName, user.userName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }

  @Override
  public String toString() {
    return "User{" +
        "userName='" + userName + '\'' +
        "} " + super.toString();
  }
}
