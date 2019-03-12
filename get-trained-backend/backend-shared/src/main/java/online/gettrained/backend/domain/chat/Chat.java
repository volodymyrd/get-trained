package online.gettrained.backend.domain.chat;

import static javax.persistence.FetchType.LAZY;
import static online.gettrained.backend.domain.chat.Chat.Type.SYS_PERSONAL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import online.gettrained.backend.domain.BaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 * List of chats.
 */
@Entity
@Table(name = "CH_CHATS")
public class Chat extends BaseEntity {

  public enum Type {
    SYS_PERSONAL
  }

  @JsonIgnore
  @Column(name = "TYPE", nullable = false, length = 30)
  private Type type = SYS_PERSONAL;

  @JsonIgnore
  @Column(name = "NAME", nullable = false, length = 100)
  private String name;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "AUTHOR_ID", nullable = false)
  private User author;

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }
}
