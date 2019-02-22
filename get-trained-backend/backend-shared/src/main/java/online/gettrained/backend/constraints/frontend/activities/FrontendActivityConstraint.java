package online.gettrained.backend.constraints.frontend.activities;

import java.util.Set;
import online.gettrained.backend.constraints.LongSelectOption;
import online.gettrained.backend.constraints.SelectOption;
import online.gettrained.backend.constraints.StringSelectOption;
import online.gettrained.backend.constraints.frontend.FrontendBasicConstraint;
import online.gettrained.backend.domain.activities.Activity;
import online.gettrained.backend.domain.activities.Activity.Status;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.activities.Trainer.Visibility;
import online.gettrained.backend.domain.activities.TrainerConnections;

/**
 * Constraints for activities.
 */
public final class FrontendActivityConstraint extends FrontendBasicConstraint {

  private Set<SelectOption<Activity.Status>> soActivityStatuses;
  private Set<SelectOption<Trainer.Status>> soTrainerStatuses;
  private Set<SelectOption<TrainerConnections.Status>> soConnectionsStatuses;
  private Set<SelectOption<Trainer.Visibility>> soTrainerVisibilities;
  private Set<LongSelectOption> soActivityIds;
  private Set<StringSelectOption> soActivityNames;
  private Set<LongSelectOption> soTrainerIds;
  private Set<LongSelectOption> soUserTrainerIds;
  private Set<LongSelectOption> soUserTraineeIds;

  public Set<SelectOption<Status>> getSoActivityStatuses() {
    return soActivityStatuses;
  }

  public void setSoActivityStatuses(
      Set<SelectOption<Status>> soActivityStatuses) {
    this.soActivityStatuses = soActivityStatuses;
  }

  public Set<SelectOption<Trainer.Status>> getSoTrainerStatuses() {
    return soTrainerStatuses;
  }

  public void setSoTrainerStatuses(
      Set<SelectOption<Trainer.Status>> soTrainerStatuses) {
    this.soTrainerStatuses = soTrainerStatuses;
  }

  public Set<SelectOption<TrainerConnections.Status>> getSoConnectionsStatuses() {
    return soConnectionsStatuses;
  }

  public void setSoConnectionsStatuses(
      Set<SelectOption<TrainerConnections.Status>> soConnectionsStatuses) {
    this.soConnectionsStatuses = soConnectionsStatuses;
  }

  public Set<SelectOption<Visibility>> getSoTrainerVisibilities() {
    return soTrainerVisibilities;
  }

  public void setSoTrainerVisibilities(
      Set<SelectOption<Visibility>> soTrainerVisibilities) {
    this.soTrainerVisibilities = soTrainerVisibilities;
  }

  public Set<LongSelectOption> getSoActivityIds() {
    return soActivityIds;
  }

  public void setSoActivityIds(
      Set<LongSelectOption> soActivityIds) {
    this.soActivityIds = soActivityIds;
  }

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
        "soActivityStatuses=" + soActivityStatuses +
        ", soTrainerStatuses=" + soTrainerStatuses +
        ", soConnectionsStatuses=" + soConnectionsStatuses +
        ", soTrainerVisibilities=" + soTrainerVisibilities +
        ", soActivityIds=" + soActivityIds +
        ", soActivityNames=" + soActivityNames +
        ", soTrainerIds=" + soTrainerIds +
        ", soUserTrainerIds=" + soUserTrainerIds +
        ", soUserTraineeIds=" + soUserTraineeIds +
        "} " + super.toString();
  }
}
