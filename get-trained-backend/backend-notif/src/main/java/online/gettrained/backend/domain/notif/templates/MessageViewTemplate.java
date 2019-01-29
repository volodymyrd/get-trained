package online.gettrained.backend.domain.notif.templates;

import online.gettrained.backend.domain.notif.NotificationChannel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import online.gettrained.backend.domain.AuditableBaseEntity;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.user.User;

/**
 * The templates of the message views.
 */
@Entity
@Table(
    name = "NOTIF_MESSAGE_VIEW_TEMPLATES",
    indexes = {@Index(name = "I_NMVT_COMPANY_CHANNEL", columnList = "CHANNEL", unique = true)
    }
)
public class MessageViewTemplate extends AuditableBaseEntity {

  private static final long serialVersionUID = -7982459033941482075L;

  public MessageViewTemplate() {
  }

  public MessageViewTemplate(
      NotificationChannel channel,
      User author,
      String description,
      String viewTemplate,
      Map<Language, MessageViewTemplateLocal> localMap) {
    this.channel = channel;
    this.author = author;
    this.description = description;
    this.viewTemplate = viewTemplate;
    this.localMap = localMap.entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            e -> new MessageViewTemplateLocal(e.getValue().getFooter())));
  }

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "CHANNEL", nullable = false)
  private NotificationChannel channel;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUTHOR_ID", nullable = false)
  private User author;

  @Column(name = "DESCRIPTION", length = 300)
  private String description;

  @Column(name = "VIEW_TEMPLATE", nullable = false, columnDefinition = "TEXT")
  private String viewTemplate;

  @JsonIgnore
  @ElementCollection
  @CollectionTable(
      name = "NOTIF_MESSAGE_VIEW_TEMPLATE_LOCALS",
      joinColumns = @JoinColumn(name = "REF_TEMPLATE_ID", referencedColumnName = "ID"))
  @MapKeyJoinColumn(name = "LANG_CODE")
  private Map<Language, MessageViewTemplateLocal> localMap = new HashMap<>();

  @Transient
  @JsonInclude(Include.NON_NULL)
  private String footer;

  public NotificationChannel getChannel() {
    return channel;
  }

  public void setChannel(NotificationChannel channel) {
    this.channel = channel;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getViewTemplate() {
    return viewTemplate;
  }

  public void setViewTemplate(String viewTemplate) {
    this.viewTemplate = viewTemplate;
  }

  public Map<Language, MessageViewTemplateLocal> getLocalMap() {
    return localMap;
  }

  public void setLocalMap(
      Map<Language, MessageViewTemplateLocal> localMap) {
    this.localMap = localMap;
  }

  public String getFooter() {
    return footer;
  }

  public void setFooter(String footer) {
    this.footer = footer;
  }
}
