package online.gettrained.backend.domain.activities;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import online.gettrained.backend.converters.TimeSlotSetConverter;
import online.gettrained.backend.domain.user.User;

/**
 * Schedule (training calendar).
 */
public class TrainerConnectionSchedule {

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_CONNECTION_ID", nullable = false)
  private TrainerConnections connection;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "TRAINEE_ID", nullable = false)
  private User trainee;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotSetConverter.class)
  @Column(name = "MONDAY", columnDefinition = "TEXT")
  private Set<TimeSlot> monday;
  private Set<TimeSlot> tuesday;
  private Set<TimeSlot> wednesday;
  private Set<TimeSlot> thursday;
  private Set<TimeSlot> friday;
  private Set<TimeSlot> saturday;
  private Set<TimeSlot> sunday;

  public TrainerConnections getConnection() {
    return connection;
  }

  public void setConnection(TrainerConnections connection) {
    this.connection = connection;
  }

  public User getTrainee() {
    return trainee;
  }

  public void setTrainee(User trainee) {
    this.trainee = trainee;
  }

  public Set<TimeSlot> getMonday() {
    return monday;
  }

  public void setMonday(Set<TimeSlot> monday) {
    this.monday = monday;
  }

  public Set<TimeSlot> getTuesday() {
    return tuesday;
  }

  public void setTuesday(Set<TimeSlot> tuesday) {
    this.tuesday = tuesday;
  }

  public Set<TimeSlot> getWednesday() {
    return wednesday;
  }

  public void setWednesday(Set<TimeSlot> wednesday) {
    this.wednesday = wednesday;
  }

  public Set<TimeSlot> getThursday() {
    return thursday;
  }

  public void setThursday(Set<TimeSlot> thursday) {
    this.thursday = thursday;
  }

  public Set<TimeSlot> getFriday() {
    return friday;
  }

  public void setFriday(Set<TimeSlot> friday) {
    this.friday = friday;
  }

  public Set<TimeSlot> getSaturday() {
    return saturday;
  }

  public void setSaturday(Set<TimeSlot> saturday) {
    this.saturday = saturday;
  }

  public Set<TimeSlot> getSunday() {
    return sunday;
  }

  public void setSunday(Set<TimeSlot> sunday) {
    this.sunday = sunday;
  }
}
