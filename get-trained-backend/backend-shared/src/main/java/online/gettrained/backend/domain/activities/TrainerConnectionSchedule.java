package online.gettrained.backend.domain.activities;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import online.gettrained.backend.converters.TimeSlotSetConverter;
import online.gettrained.backend.domain.AuditableBaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 * Schedule (training calendar).
 */
@Entity
@Table(name = "ACT_SCHEDULES",
    indexes = {
        @Index(name = "I_ACT_SCHEDULES_REF_CONNECTION_ID", columnList = "REF_CONNECTION_ID")})
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
  @Convert(converter = TimeSlotSetConverter.class)
  @Column(name = "MONDAY", columnDefinition = "TEXT")
  private Set<TimeSlot> monday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotSetConverter.class)
  @Column(name = "TUESDAY", columnDefinition = "TEXT")
  private Set<TimeSlot> tuesday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotSetConverter.class)
  @Column(name = "WEDNESDAY", columnDefinition = "TEXT")
  private Set<TimeSlot> wednesday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotSetConverter.class)
  @Column(name = "THURSDAY", columnDefinition = "TEXT")
  private Set<TimeSlot> thursday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotSetConverter.class)
  @Column(name = "FRIDAY", columnDefinition = "TEXT")
  private Set<TimeSlot> friday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotSetConverter.class)
  @Column(name = "SATURDAY", columnDefinition = "TEXT")
  private Set<TimeSlot> saturday;

  @JsonInclude(NON_NULL)
  @Convert(converter = TimeSlotSetConverter.class)
  @Column(name = "SUNDAY", columnDefinition = "TEXT")
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
