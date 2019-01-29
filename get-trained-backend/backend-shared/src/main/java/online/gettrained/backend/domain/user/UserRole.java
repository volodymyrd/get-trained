package online.gettrained.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import online.gettrained.backend.domain.BaseEntity;
import online.gettrained.backend.domain.localization.Language;

import javax.persistence.*;
import java.util.*;

/**
 * User roles, a certain role can contain another roles and called a group
 */
@Entity
@Table(name = "USR_ROLES")
public class UserRole extends BaseEntity {

  private static final long serialVersionUID = 1L;

  public enum Scope {
    ALL,
    SYSTEM,
    COMPANY
  }

  public UserRole() {
  }

  public UserRole(String name) {
    this.name = name;
  }

  @Column(name = "NAME", length = 100, nullable = false, unique = true)
  private String name;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEFAULT_LANG_CODE", nullable = false)
  private Language defaultLang;

  @JsonIgnore
  @Column(name = "DEFAULT_DESCRIPTION", nullable = false, length = 300)
  private String defaultDescription;

  @JsonIgnore
  @ElementCollection
  @CollectionTable(name = "USR_ROLE_LOCALS", joinColumns = {
      @JoinColumn(name = "REF_ROLE_ID", referencedColumnName = "ID")})
  @MapKeyJoinColumn(name = "LANG_CODE")
  private Map<Language, UserRoleLocal> mapLocals = new HashMap<>();

  @JsonIgnore
  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  private Set<User> users;

  @Column(name = "SCOPE", length = 10, nullable = false)
  @Enumerated(EnumType.STRING)
  private Scope scope;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "USR_ROLES_ACTIONS",
      joinColumns = @JoinColumn(name = "REF_ROLE_ID"),
      inverseJoinColumns = @JoinColumn(name = "ACTION_ID"))
  private Set<UserAction> actions = new HashSet<>();

  @Transient
  private Long roleId;

  @Transient
  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Language getDefaultLang() {
    return defaultLang;
  }

  public void setDefaultLang(Language defaultLang) {
    this.defaultLang = defaultLang;
  }

  public String getDefaultDescription() {
    return defaultDescription;
  }

  public void setDefaultDescription(String defaultDescription) {
    this.defaultDescription = defaultDescription;
  }

  public Map<Language, UserRoleLocal> getMapLocals() {
    return mapLocals;
  }

  public void setMapLocals(
      Map<Language, UserRoleLocal> mapLocals) {
    this.mapLocals = mapLocals;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }

  public Set<UserAction> getActions() {
    return actions;
  }

  public void setActions(Set<UserAction> actions) {
    this.actions = actions;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
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
    if (!(o instanceof UserRole)) {
      return false;
    }
    UserRole userRole = (UserRole) o;
    return Objects.equals(name, userRole.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "UserRole{" +
        "name='" + name + '\'' +
        "} " + super.toString();
  }
}
