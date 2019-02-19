package online.gettrained.backend.constraints.frontend.activities;

import java.util.Set;
import online.gettrained.backend.constraints.LongSelectOption;
import online.gettrained.backend.constraints.StringSelectOption;
import online.gettrained.backend.constraints.frontend.FrontendBasicConstraint;

/**
 * Constraints for activities.
 */
public final class FrontendActivityConstraint extends FrontendBasicConstraint {

  private Set<StringSelectOption> soActivityNames;
  private Set<LongSelectOption> soTrainerIds;
  private Set<LongSelectOption> soUserTrainerIds;
  private Set<LongSelectOption> soUserTraineeIds;

  public Set<StringSelectOption> getSoActivityNames() {
    return soActivityNames;
  }

  public void setSoActivityNames(
      Set<StringSelectOption> soActivityNames) {
    this.soActivityNames = soActivityNames;
  }

  public Set<LongSelectOption> getSoTrainerIds() {
    return soTrainerIds;
  }

  public void setSoTrainerIds(
      Set<LongSelectOption> soTrainerIds) {
    this.soTrainerIds = soTrainerIds;
  }

  public Set<LongSelectOption> getSoUserTrainerIds() {
    return soUserTrainerIds;
  }

  public void setSoUserTrainerIds(
      Set<LongSelectOption> soUserTrainerIds) {
    this.soUserTrainerIds = soUserTrainerIds;
  }

  public Set<LongSelectOption> getSoUserTraineeIds() {
    return soUserTraineeIds;
  }

  public void setSoUserTraineeIds(
      Set<LongSelectOption> soUserTraineeIds) {
    this.soUserTraineeIds = soUserTraineeIds;
  }

  @Override
  public String toString() {
    return "FrontendActivityConstraint{" +
        "soActivityNames=" + soActivityNames +
        ", soTrainerIds=" + soTrainerIds +
        ", soUserTrainerIds=" + soUserTrainerIds +
        ", soUserTraineeIds=" + soUserTraineeIds +
        "} " + super.toString();
  }
}
