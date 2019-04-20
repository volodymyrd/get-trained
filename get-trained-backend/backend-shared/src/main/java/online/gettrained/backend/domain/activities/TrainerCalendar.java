package online.gettrained.backend.domain.activities;

import java.util.List;

/**
 * Dto for trainer calendar.
 */
public final class TrainerCalendar {

  private final List<TraineeTimeSlot> monday;
  private final List<TraineeTimeSlot> tuesday;
  private final List<TraineeTimeSlot> wednesday;
  private final List<TraineeTimeSlot> thursday;
  private final List<TraineeTimeSlot> friday;
  private final List<TraineeTimeSlot> saturday;
  private final List<TraineeTimeSlot> sunday;

  public TrainerCalendar(
      List<TraineeTimeSlot> monday,
      List<TraineeTimeSlot> tuesday,
      List<TraineeTimeSlot> wednesday,
      List<TraineeTimeSlot> thursday,
      List<TraineeTimeSlot> friday,
      List<TraineeTimeSlot> saturday,
      List<TraineeTimeSlot> sunday) {
    this.monday = monday;
    this.tuesday = tuesday;
    this.wednesday = wednesday;
    this.thursday = thursday;
    this.friday = friday;
    this.saturday = saturday;
    this.sunday = sunday;
  }

  public List<TraineeTimeSlot> getMonday() {
    return monday;
  }

  public List<TraineeTimeSlot> getTuesday() {
    return tuesday;
  }

  public List<TraineeTimeSlot> getWednesday() {
    return wednesday;
  }

  public List<TraineeTimeSlot> getThursday() {
    return thursday;
  }

  public List<TraineeTimeSlot> getFriday() {
    return friday;
  }

  public List<TraineeTimeSlot> getSaturday() {
    return saturday;
  }

  public List<TraineeTimeSlot> getSunday() {
    return sunday;
  }

  @Override
  public String toString() {
    return "TrainerCalendar{" +
        "monday=" + monday +
        ", tuesday=" + tuesday +
        ", wednesday=" + wednesday +
        ", thursday=" + thursday +
        ", friday=" + friday +
        ", saturday=" + saturday +
        ", sunday=" + sunday +
        '}';
  }
}
