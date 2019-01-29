package online.gettrained.backend.constraints.frontend.localization;

import java.util.Set;
import online.gettrained.backend.constraints.SelectOption;
import online.gettrained.backend.constraints.frontend.FrontendBasicConstraint;
import online.gettrained.backend.domain.localization.ELocalModule;

/**
 * Frontend localization constraint.
 */
public class FrontendLocalConstraint extends FrontendBasicConstraint {

  private static final long serialVersionUID = 2107237141459940675L;

  private ELocalModule module;

  private Set<SelectOption<ELocalModule>> soModules;

  public ELocalModule getModule() {
    return module;
  }

  public void setModule(ELocalModule module) {
    this.module = module;
  }

  public Set<SelectOption<ELocalModule>> getSoModules() {
    return soModules;
  }

  public void setSoModules(
      Set<SelectOption<ELocalModule>> soModules) {
    this.soModules = soModules;
  }

  @Override
  public String toString() {
    return "FrontendLocalConstraint{" +
        "module=" + module +
        ", soModules=" + soModules +
        "} " + super.toString();
  }
}
