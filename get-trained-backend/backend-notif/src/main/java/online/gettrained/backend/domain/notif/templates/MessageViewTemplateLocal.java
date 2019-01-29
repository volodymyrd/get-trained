package online.gettrained.backend.domain.notif.templates;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Localization for the template view.
 */
@Embeddable
public class MessageViewTemplateLocal {

  public MessageViewTemplateLocal() {
  }

  public MessageViewTemplateLocal(String footer) {
    this.footer = footer;
  }

  @Column(name = "FOOTER", columnDefinition = "TEXT", nullable = false)
  private String footer;

  public String getFooter() {
    return footer;
  }

  public void setFooter(String footer) {
    this.footer = footer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MessageViewTemplateLocal)) {
      return false;
    }
    MessageViewTemplateLocal that = (MessageViewTemplateLocal) o;
    return Objects.equals(footer, that.footer);
  }

  @Override
  public int hashCode() {

    return Objects.hash(footer);
  }

  @Override
  public String toString() {
    return "MessageViewTemplateLocal{" + "footer='" + footer + '\'' + '}';
  }
}
