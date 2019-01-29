package online.gettrained.backend.constraints;

import java.util.Date;

/**
 * Date constraint
 */
public final class DateConstraint {

  public static DateConstraint empty() {
    return getBuilder().build();
  }

  public static Builder getBuilder() {
    return new Builder();
  }

  public final static class Builder {

    private boolean empty = true;
    private SelectOption<Date> dateCreate;
    private SelectOption<Date> dateUpdate;

    private Builder() {
    }

    public DateConstraint build() {
      return new DateConstraint(this);
    }

    public Builder setCreatedDate(SelectOption<Date> dateCreate) {
      empty = false;
      this.dateCreate = dateCreate;
      return this;
    }

    public Builder setUpdateDate(SelectOption<Date> dateUpdate) {
      empty = false;
      this.dateUpdate = dateUpdate;
      return this;
    }
  }

  private DateConstraint(Builder builder) {
    this.empty = builder.empty;
    this.dateCreate = builder.dateCreate;
    this.dateUpdate = builder.dateUpdate;
  }

  private final boolean empty;
  private final SelectOption<Date> dateCreate;
  private final SelectOption<Date> dateUpdate;

  public boolean isEmpty() {
    return empty;
  }

  public SelectOption<Date> getDateCreate() {
    return dateCreate == null ? null : new SelectOption<>(dateCreate);
  }

  public SelectOption<Date> getDateUpdate() {
    return dateUpdate == null ? null : new SelectOption<>(dateUpdate);
  }

  @Override
  public String toString() {
    return "DateConstraint{" +
        "empty=" + empty +
        ", dateCreate=" + dateCreate +
        ", dateUpdate=" + dateUpdate +
        '}';
  }
}
