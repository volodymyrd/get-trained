package online.gettrained.backend.constraints;

/**
 * Selection option {@link SelectOption} for {@link Long}.
 */
public final class LongSelectOption extends SelectOption<Long> {

  private static final long serialVersionUID = -3025991537467789641L;

  public LongSelectOption() {
  }

  public LongSelectOption(Sign sign, Option option, long low) {
    super(sign, option, low);
  }

  public LongSelectOption(Sign sign, Option option, long low, long high) {
    super(sign, option, low, high);
  }
}
