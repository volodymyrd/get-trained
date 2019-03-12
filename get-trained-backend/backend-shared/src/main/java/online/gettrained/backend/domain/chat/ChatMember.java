package online.gettrained.backend.domain.chat;

import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import online.gettrained.backend.domain.BaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 * Members of a chat.
 */
@Entity
@Table(name = "CH_CHAT_MEMBERS",
    indexes = {@Index(
        name = "I_CH_CHAT_MEMBERS_REF_CHAT_ID_MEMBER_ID",
        columnList = "REF_CHAT_ID, MEMBER_ID",
        unique = true),
        @Index(name = "I_CH_CHAT_MEMBERS_MEMBER_ID", columnList = "MEMBER_ID")})
public class ChatMember extends BaseEntity {

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_CHAT_ID", nullable = false)
  private Chat chat;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "MEMBER_ID", nullable = false)
  private User member;

  public Chat getChat() {
    return chat;
  }

  public void setChat(Chat chat) {
    this.chat = chat;
  }

  public User getMember() {
    return member;
  }

  public void setMember(User member) {
    this.member = member;
  }
}
