package online.gettrained.backend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import online.gettrained.backend.domain.localization.Language;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User actions
 */
@Entity
@Table(name = "USR_ACTIONS")
public class UserAction implements Serializable {

  public enum Id {
    BERIZE_ALL,
    LOCALS_RW,
    LOCALS_R,
    USERS_R,
    USERS_RW,
    COMPANY_CREATE,
    COMPANY_RW,
    COMPANY_R,
    HR_RW,
    HR_R,
    HR_R_PERSONAL,
    HR_ROLES_R,
    HR_ROLES_RW,
    CONTENT_RW,
    CONTENT_DELETE_FULL,
    CONTENT_R,
    FILE_RW,
    FILE_R,
    ASM_SYSTEM_RW,
    ASM_SYSTEM_R,
    ASM_CASE_RW,
    ASM_CASE_DELETE_FULL,
    ASM_CASE_R,
    ASM_START,
    ASM_VIEW,
    ASM_CLOSE,
    ASM_REOPEN,
    ASM_DELETE,
    ASM_APPEAL_CLOSE,
    ASM_CLOSED_CHANGE,
    NOTIF_TEMPL_R,
    NOTIF_TEMPL_RW,
    NOTIF_SYS_TEMPL_RW,
    TASK_RW,
    TASK_R,
    CONTEST_RW,
    CONTEST_ASSESS,
    CONTEST_MATERIALS_RW
  }

  private static final long serialVersionUID = -5100912212604230312L;

  @javax.persistence.Id
  @Enumerated(EnumType.STRING)
  @Column(name = "ID", length = 25)
  private Id id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEFAULT_LANG_CODE", nullable = false)
  private Language defaultLang;

  @JsonIgnore
  @Column(name = "DEFAULT_DESCRIPTION", nullable = false, length = 300)
  private String defaultDescription;

  @JsonIgnore
  @ElementCollection
  @CollectionTable(name = "USR_ACTION_LOCALS", joinColumns = {
      @JoinColumn(name = "ACTION_ID", referencedColumnName = "ID")})
  @MapKeyJoinColumn(name = "LANG_CODE")
  private Map<Language, UserActionLocal> mapLocals = new HashMap<>();

  public Id getId() {
    return id;
  }

  public void setId(Id id) {
    this.id = id;
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

  public Map<Language, UserActionLocal> getMapLocals() {
    return mapLocals;
  }

  public void setMapLocals(
      Map<Language, UserActionLocal> mapLocals) {
    this.mapLocals = mapLocals;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserAction)) {
      return false;
    }
    UserAction that = (UserAction) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "UserAction{" +
        "id=" + id +
        '}';
  }
}
