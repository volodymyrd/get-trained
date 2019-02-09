package online.gettrained.backend.domain.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import online.gettrained.backend.domain.user.UserRole;

/**
 * Main menu.
 */
@Entity
@Table(name = "DB_MENU")
public class Menu implements Serializable {

  private static final long serialVersionUID = -1633408260670982165L;

  @Id
  @Column(name = "MENU_ID", length = 50)
  private String menuId;

  @Column(name = "POSITION", nullable = false)
  private int position;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "PARENT_ID")
  private Menu parent;

  @JsonIgnore
  @Column(name = "LOCAL_KEY", nullable = false, length = 200)
  private String localKey;

  @JsonIgnore
  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("position")
  private Set<Menu> childrenSet = new HashSet<>();

  @Column(name = "ICON", length = 50)
  private String icon;

  @Column(name = "COLOR", length = 12)
  private String color;

  @Column(name = "LINK", length = 250)
  private String link;

  @Column(name = "FUN_CALL", length = 250)
  private String funCall;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "DB_MENU_ROLES",
      joinColumns = @JoinColumn(name = "REF_MENU_ID"),
      inverseJoinColumns = @JoinColumn(name = "REF_ROLE_ID"))
  private Set<UserRole> roles;

  @Transient
  private String title;

  @Transient
  private Set<Menu> items = new LinkedHashSet<>();

  public String getMenuId() {
    return menuId;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public Menu getParent() {
    return parent;
  }

  public void setParent(Menu parent) {
    this.parent = parent;
  }

  public String getLocalKey() {
    return localKey;
  }

  public void setLocalKey(String localKey) {
    this.localKey = localKey;
  }

  public Set<Menu> getChildrenSet() {
    return childrenSet;
  }

  public void setChildrenSet(Set<Menu> childrenSet) {
    this.childrenSet = childrenSet;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getFunCall() {
    return funCall;
  }

  public void setFunCall(String funCall) {
    this.funCall = funCall;
  }

  public Set<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<Menu> getItems() {
    return items;
  }

  public void setItems(Set<Menu> items) {
    this.items = items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Menu)) {
      return false;
    }
    Menu menu = (Menu) o;
    return Objects.equals(menuId, menu.menuId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(menuId);
  }
}
