package online.gettrained.backend.constraints;

import online.gettrained.backend.domain.notif.NotificationEvent.Code;
import online.gettrained.backend.domain.notif.NotificationEvent.Scope;
import online.gettrained.backend.domain.notif.NotificationEventGroup;
import java.util.Set;
import org.springframework.data.domain.Pageable;

/**
 * Notification event constraint.
 */
public final class NotificationEventConstraint extends AbstractConstraint {

  public static NotificationEventConstraint.Builder getBuilder(
      Scope scope,
      String langCode,
      Pageable pageable) {
    return new Builder(scope, langCode, pageable);
  }

  public final static class Builder {

    private final Scope scope;
    private final String langCode;
    private final Pageable pageable;
    private Set<Code> codes;
    private Set<NotificationEventGroup.Code> groupCodes;

    private Builder(Scope scope, String langCode, Pageable pageable) {
      this.scope = scope;
      this.langCode = langCode;
      this.pageable = pageable;
    }

    public NotificationEventConstraint build() {
      return new NotificationEventConstraint(this);
    }

    public Builder setCodes(Set<Code> codes) {
      this.codes = codes;
      return this;
    }

    public Builder setGroupCodes(Set<NotificationEventGroup.Code> groupCodes) {
      this.groupCodes = groupCodes;
      return this;
    }
  }

  private NotificationEventConstraint(Builder builder) {
    this.scope = builder.scope;
    this.langCode = builder.langCode;
    this.pageable = builder.pageable;
    this.codes = builder.codes;
    this.groupCodes = builder.groupCodes;
  }

  private final Scope scope;
  private final String langCode;
  private final Pageable pageable;
  private final Set<Code> codes;
  private final Set<NotificationEventGroup.Code> groupCodes;

  public boolean isEmpty() {
    return false;
  }

  public Set<Code> getCodes() {
    return getSet(codes);
  }

  public Set<NotificationEventGroup.Code> getGroupCodes() {
    return groupCodes;
  }

  public Scope getScope() {
    return scope;
  }

  public String getLangCode() {
    return langCode;
  }

  public Pageable getPageable() {
    return pageable;
  }

  @Override
  public String toString() {
    return "NotificationEventConstraint{"
        + "scope=" + scope
        + ", langCode='" + langCode + '\''
        + ", pageable=" + pageable
        + ", codes=" + codes
        + ", groupCodes=" + groupCodes
        + '}';
  }
}

