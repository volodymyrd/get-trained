package online.gettrained.backend.domain.chat;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import online.gettrained.backend.domain.AuditableNonDeletableBaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 * List of chat messages.
 */
@Entity
@Table(name = "CH_CHAT_MESSAGES")
public class ChatMessage extends AuditableNonDeletableBaseEntity {

  public static class ChatMessageUser {
    private String _id;
    private String name;
    private String avatar;

    public ChatMessageUser() {
    }

    public ChatMessageUser(String _id, String name, String avatar) {
      this._id = _id;
      this.name = name;
      this.avatar = avatar;
    }

    public String get_id() {
      return _id;
    }

    public String getName() {
      return name;
    }

    public String getAvatar() {
      return avatar;
    }

    @Override
    public String toString() {
      return "ChatMessageUser{" +
          "_id='" + _id + '\'' +
          ", name='" + name + '\'' +
          ", avatar='" + avatar + '\'' +
          '}';
    }
  }

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_CHAT_MEMBER_ID", nullable = false)
  private ChatMember member;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_CHAT_ID", nullable = false)
  private Chat chat;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "AUTHOR_ID", nullable = false)
  private User author;

  @Column(name = "TEXT_MESSAGE", nullable = false, columnDefinition = "TEXT")
  private String text;

  @Transient
  @JsonInclude(NON_NULL)
  private Long chatId;

  @Transient
  @JsonInclude(NON_NULL)
  private String _id;

  @Transient
  @JsonInclude(NON_NULL)
  private String createdAt;

  @Transient
  @JsonInclude(NON_NULL)
  private ChatMessageUser user;

  public ChatMember getMember() {
    return member;
  }

  public void setMember(ChatMember member) {
    this.member = member;
  }

  public Chat getChat() {
    return chat;
  }

  public void setChat(Chat chat) {
    this.chat = chat;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getChatId() {
    return chatId;
  }

  public void setChatId(Long chatId) {
    this.chatId = chatId;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public ChatMessageUser getUser() {
    return user;
  }

  public void setUser(ChatMessageUser user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "ChatMessage{" +
        "text='" + text + '\'' +
        ", chatId=" + chatId +
        ", _id='" + _id + '\'' +
        ", createdAt='" + createdAt + '\'' +
        ", user=" + user +
        "} " + super.toString();
  }
}
