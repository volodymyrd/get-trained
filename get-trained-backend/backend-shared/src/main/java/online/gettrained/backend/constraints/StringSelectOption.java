package online.gettrained.backend.constraints;

/**
 * Selection option {@link SelectOption} for {@link String}.
 */
public final class StringSelectOption extends SelectOption<String> {

  private static final long serialVersionUID = -3025991537467789641L;

  public StringSelectOption() {
  }

  public StringSelectOption(Sign sign, Option option, String low) {
    super(sign, option, low);
  }

  public StringSelectOption(Sign sign, Option option, String low, String high) {
    super(sign, option, low, high);
  }
}
