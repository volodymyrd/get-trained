package online.gettrained.backend.domain.activities;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import online.gettrained.backend.converters.TimeSlotListConverter;
import online.gettrained.backend.domain.AuditableBaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 * Schedule (training calendar).
 */
@Entity
@Table(name = "ACT_SCHEDULES",
    indexes = {@Index(
        name = "I_ACT_SCHEDULES_REF_CONNECTION_ID",
        columnList = "REF_CONNECTION_ID",
        unique = true)
    }
)
public class TrainerConnectionSchedule extends AuditableBaseEntity {

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_CONNECTION_ID", nullable = false)
  private TrainerConnections connection;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "TRAINEE_ID", nullable = false)
  private User trainee;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotListConverter.class)
  @Column(name = "MONDAY", columnDefinition = "TEXT")
  private List<TimeSlot> monday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotListConverter.class)
  @Column(name = "TUESDAY", columnDefinition = "TEXT")
  private List<TimeSlot> tuesday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotListConverter.class)
  @Column(name = "WEDNESDAY", columnDefinition = "TEXT")
  private List<TimeSlot> wednesday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotListConverter.class)
  @Column(name = "THURSDAY", columnDefinition = "TEXT")
  private List<TimeSlot> thursday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotListConverter.class)
  @Column(name = "FRIDAY", columnDefinition = "TEXT")
  private List<TimeSlot> friday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotListConverter.class)
  @Column(name = "SATURDAY", columnDefinition = "TEXT")
  private List<TimeSlot> saturday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotListConverter.class)
  @Column(name = "SUNDAY", columnDefinition = "TEXT")
  private List<TimeSlot> sunday;

  @Transient
  @JsonInclude(NON_NULL)
  private Long traineeUserId;

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

  public List<TimeSlot> getMonday() {
    return monday;
  }

  public void setMonday(List<TimeSlot> monday) {
    this.monday = monday;
  }

  public List<TimeSlot> getTuesday() {
    return tuesday;
  }

  public void setTuesday(List<TimeSlot> tuesday) {
    this.tuesday = tuesday;
  }

  public List<TimeSlot> getWednesday() {
    return wednesday;
  }

  public void setWednesday(List<TimeSlot> wednesday) {
    this.wednesday = wednesday;
  }

  public List<TimeSlot> getThursday() {
    return thursday;
  }

  public void setThursday(List<TimeSlot> thursday) {
    this.thursday = thursday;
  }

  public List<TimeSlot> getFriday() {
    return friday;
  }

  public void setFriday(List<TimeSlot> friday) {
    this.friday = friday;
  }

  public List<TimeSlot> getSaturday() {
    return saturday;
  }

  public void setSaturday(List<TimeSlot> saturday) {
    this.saturday = saturday;
  }

  public List<TimeSlot> getSunday() {
    return sunday;
  }

  public void setSunday(List<TimeSlot> sunday) {
    this.sunday = sunday;
  }

  public Long getTraineeUserId() {
    return traineeUserId;
  }

  public void setTraineeUserId(Long traineeUserId) {
    this.traineeUserId = traineeUserId;
  }

  @Override
  public String toString() {
    return "TrainerConnectionSchedule{" +
        "traineeUserId=" + traineeUserId +
        "} " + super.toString();
  }
}
