package online.gettrained.backend.domain.activities;

/**
 * Dto {@link TimeSlot} with information about connection and trainee.
 */
public final class TraineeTimeSlot extends TimeSlot {

  private final long connectionId;
  private final long traineeUserId;

  public TraineeTimeSlot(TimeSlot slot, long connectionId, long traineeUserId) {
    super(slot.getStart(), slot.getEnd());
    this.connectionId = connectionId;
    this.traineeUserId = traineeUserId;
  }

  public long getConnectionId() {
    return connectionId;
  }

  public long getTraineeUserId() {
    return traineeUserId;
  }

  @Override
  public String toString() {
    return "TraineeTimeSlot{" +
        "connectionId=" + connectionId +
        ", traineeUserId=" + traineeUserId +
        "} " + super.toString();
  }
}
