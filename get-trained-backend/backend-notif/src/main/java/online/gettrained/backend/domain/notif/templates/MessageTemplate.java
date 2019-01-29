package online.gettrained.backend.domain.notif.templates;

import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.domain.notif.NotificationEvent.Code;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
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
 * The templates of the messages.
 */
@Entity
@Table(
    name = "NOTIF_MESSAGE_TEMPLATES",
    indexes = {@Index(name = "I_NMT_EVENT_CHANNEL", columnList = "EVENT, CHANNEL", unique = true)})
public class MessageTemplate extends AuditableBaseEntity {

  private static final long serialVersionUID = 2587239587783724690L;


  public MessageTemplate() {
  }

  public MessageTemplate(
      Long templateId,
      Code eventCode,
      String eventName,
      NotificationChannel.Code channelCode,
      String description,
      String selectedLang,
      MessageTemplateLocal local) {
    this.templateId = templateId;
    this.eventCode = eventCode;
    this.eventName = eventName;
    this.channelCode = channelCode;
    this.description = description;
    this.selectedLang = selectedLang;
    this.local = local;
  }

  public MessageTemplate(
      NotificationEvent event,
      NotificationChannel channel,
      User author,
      String description,
      Map<Language, MessageTemplateLocal> localMap) {
    this.event = event;
    this.channel = channel;
    this.author = author;
    this.description = description;
    this.localMap = localMap.entrySet().stream()
        .collect(Collectors.toMap(
            Entry::getKey,
            e -> new MessageTemplateLocal(e.getValue().getTitle(), e.getValue().getBody())));
  }

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "EVENT", nullable = false)
  private NotificationEvent event;

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

  @JsonIgnore
  @ElementCollection
  @CollectionTable(
      name = "NOTIF_MESSAGE_TEMPLATE_LOCALS",
      joinColumns = @JoinColumn(name = "REF_TEMPLATE_ID", referencedColumnName = "ID"))
  @MapKeyJoinColumn(name = "LANG_CODE")
  private Map<Language, MessageTemplateLocal> localMap = new HashMap<>();

  @Transient
  @JsonInclude(Include.NON_NULL)
  private Long templateId;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private NotificationEvent.Code eventCode;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private String eventName;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private NotificationChannel.Code channelCode;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private String selectedLang;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private MessageTemplateLocal local;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private Map<String, String> langs;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private Map<NotificationChannel.Code, String> channels;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private Map<String, String> mergedTags;

  public NotificationEvent getEvent() {
    return event;
  }

  public void setEvent(NotificationEvent event) {
    this.event = event;
  }

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

  public Map<Language, MessageTemplateLocal> getLocalMap() {
    return localMap;
  }

  public void setLocalMap(
      Map<Language, MessageTemplateLocal> localMap) {
    this.localMap = localMap;
  }

  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }

  public Code getEventCode() {
    return eventCode;
  }

  public void setEventCode(Code eventCode) {
    this.eventCode = eventCode;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public NotificationChannel.Code getChannelCode() {
    return channelCode;
  }

  public void setChannelCode(NotificationChannel.Code channelCode) {
    this.channelCode = channelCode;
  }

  public String getSelectedLang() {
    return selectedLang;
  }

  public void setSelectedLang(String selectedLang) {
    this.selectedLang = selectedLang;
  }

  public MessageTemplateLocal getLocal() {
    return local;
  }

  public void setLocal(MessageTemplateLocal local) {
    this.local = local;
  }

  public Map<String, String> getLangs() {
    return langs;
  }

  public void setLangs(Map<String, String> langs) {
    this.langs = langs;
  }

  public Map<NotificationChannel.Code, String> getChannels() {
    return channels;
  }

  public void setChannels(
      Map<NotificationChannel.Code, String> channels) {
    this.channels = channels;
  }

  public Map<String, String> getMergedTags() {
    return mergedTags;
  }

  public void setMergedTags(Map<String, String> mergedTags) {
    this.mergedTags = mergedTags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MessageTemplate)) {
      return false;
    }
    MessageTemplate that = (MessageTemplate) o;
    return Objects.equals(event, that.event) &&
        Objects.equals(channel, that.channel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), event, channel);
  }

  @Override
  public String toString() {
    return "MessageTemplate{" +
        "templateId=" + templateId +
        ", eventCode=" + eventCode +
        ", channelCode=" + channelCode +
        ", selectedLang='" + selectedLang + '\'' +
        ", local=" + local +
        "} " + super.toString();
  }
}
