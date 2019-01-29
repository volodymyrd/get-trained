package online.gettrained.backend.domain.notif;

import online.gettrained.backend.domain.notif.templates.MessageTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import online.gettrained.backend.domain.localization.Language;

/**
 * The notification events.
 */
@Entity
@Table(name = "NOTIF_EVENTS",
    indexes = {@Index(name = "I_NOTIF_EVENTS_GROUPS", columnList = "REF_GROUP_CODE")})
public class NotificationEvent implements Serializable {

  private static final long serialVersionUID = -4364099223092482908L;

  public enum Scope {
    SYSTEM,
  }

  public enum Code {
    /**
     * {@link NotificationEventGroup.Code.SYSTEM}
     */
    CONFIRM_REGISTRATION,
    SUCCESS_REGISTRATION,
    SUCCESS_LOGIN,
    RESTORE_PASSWORD,
    ADMIN_SYSTEM_METRICS,
  }

  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "EVENT", nullable = false, length = 50)
  private Code event;

  @Enumerated(EnumType.STRING)
  @Column(name = "SCOPE", nullable = false, length = 10)
  private Scope scope = Scope.SYSTEM;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REF_GROUP_CODE", nullable = false)
  private NotificationEventGroup group;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEFAULT_LANG_CODE", nullable = false)
  private Language defaultLang;

  @JsonIgnore
  @Column(name = "DEFAULT_DESCRIPTION", nullable = false, length = 300)
  private String defaultDescription;

  @JsonIgnore
  @ElementCollection
  @CollectionTable(name = "NOTIF_EVENT_LOCALS", joinColumns = {
      @JoinColumn(name = "EVENT", referencedColumnName = "EVENT")})
  @MapKeyJoinColumn(name = "LANG_CODE")
  private Map<Language, NotificationEventLocal> mapLocals = new HashMap<>();

  @Column(name = "DAYS_NUMBER", nullable = false)
  private int daysNumberBeforeExpired = 0;

  @Column(name = "SINGLETON")
  private Boolean singleton;

  @JsonIgnore
  @OneToMany(mappedBy = "event")
  private Set<MessageTemplate> templateSet = new HashSet<>();

  @Transient
  @JsonInclude(Include.NON_NULL)
  private String description;

  public Code getEvent() {
    return event;
  }

  public void setEvent(Code event) {
    this.event = event;
  }

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }

  public NotificationEventGroup getGroup() {
    return group;
  }

  public void setGroup(NotificationEventGroup group) {
    this.group = group;
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

  public Map<Language, NotificationEventLocal> getMapLocals() {
    return mapLocals;
  }

  public void setMapLocals(Map<Language, NotificationEventLocal> mapLocals) {
    this.mapLocals = mapLocals;
  }

  public int getDaysNumberBeforeExpired() {
    return daysNumberBeforeExpired;
  }

  public void setDaysNumberBeforeExpired(int daysNumberBeforeExpired) {
    this.daysNumberBeforeExpired = daysNumberBeforeExpired;
  }

  public Boolean getSingleton() {
    return singleton;
  }

  public void setSingleton(Boolean singleton) {
    this.singleton = singleton;
  }

  public Set<MessageTemplate> getTemplateSet() {
    return templateSet;
  }

  public void setTemplateSet(
      Set<MessageTemplate> templateSet) {
    this.templateSet = templateSet;
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
    if (!(o instanceof NotificationEvent)) {
      return false;
    }
    NotificationEvent that = (NotificationEvent) o;
    return event == that.event;
  }

  @Override
  public int hashCode() {
    return Objects.hash(event);
  }

  @Override
  public String toString() {
    return "NotificationEvent{" +
        "code=" + event +
        '}';
  }
}
