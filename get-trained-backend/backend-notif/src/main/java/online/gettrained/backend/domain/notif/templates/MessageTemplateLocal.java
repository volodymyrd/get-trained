package online.gettrained.backend.domain.notif.templates;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Localization for the body of the template message.
 */
@Embeddable
public class MessageTemplateLocal {

  public MessageTemplateLocal() {
  }

  public MessageTemplateLocal(String title, String body) {
    this.title = title;
    this.body = body;
  }

  @Column(name = "TITLE", columnDefinition = "TEXT")
  private String title;

  @Column(name = "BODY", nullable = false, columnDefinition = "TEXT")
  private String body;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MessageTemplateLocal)) {
      return false;
    }
    MessageTemplateLocal that = (MessageTemplateLocal) o;
    return Objects.equals(title, that.title) &&
        Objects.equals(body, that.body);
  }

  @Override
  public int hashCode() {

    return Objects.hash(title, body);
  }

  @Override
  public String toString() {
    return "MessageTemplateLocal{" +
        "title='" + title + '\'' +
        ", body='" + body + '\'' +
        '}';
  }
}
