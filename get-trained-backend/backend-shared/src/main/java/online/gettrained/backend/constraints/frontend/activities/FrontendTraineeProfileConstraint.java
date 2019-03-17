package online.gettrained.backend.constraints.frontend.activities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Set;
import online.gettrained.backend.constraints.DateSelectOption;
import online.gettrained.backend.constraints.frontend.FrontendBasicConstraint;
import online.gettrained.backend.domain.activities.TraineeProfile;
import online.gettrained.backend.json_serializers.DateSelectOptionJsonDeserializer;

/**
 * Constraints for {@link TraineeProfile}.
 */
public class FrontendTraineeProfileConstraint extends FrontendBasicConstraint {

  private long connectionId;
  private long traineeUserId;
  @JsonDeserialize(using = DateSelectOptionJsonDeserializer.class)
  private Set<DateSelectOption> soDateMeasures;

  public long getConnectionId() {
    return connectionId;
  }

  public void setConnectionId(long connectionId) {
    this.connectionId = connectionId;
  }

  public long getTraineeUserId() {
    return traineeUserId;
  }

  public void setTraineeUserId(long traineeUserId) {
    this.traineeUserId = traineeUserId;
  }

  public Set<DateSelectOption> getSoDateMeasures() {
    return soDateMeasures;
  }

  public void setSoDateMeasures(
      Set<DateSelectOption> soDateMeasures) {
    this.soDateMeasures = soDateMeasures;
  }

  @Override
  public String toString() {
    return "FrontendTraineeProfileConstraint{" +
        "connectionId=" + connectionId +
        ", traineeUserId=" + traineeUserId +
        ", soDateMeasures=" + soDateMeasures +
        "} " + super.toString();
  }
}
