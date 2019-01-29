package online.gettrained.backend.constraints;

import java.util.Date;

/**
 * Selection option {@link SelectOption} for {@link Date}.
 */
public final class DateSelectOption extends SelectOption<Date> {

  private static final long serialVersionUID = -8244772364140259058L;

  public DateSelectOption() {
  }

  public DateSelectOption(Sign sign, Option option, Date low) {
    super(sign, option, low);
  }

  public DateSelectOption(Sign sign, Option option, Date low, Date high) {
    super(sign, option, low, high);
  }
}
