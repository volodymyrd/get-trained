package online.gettrained.backend.domain.activities;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import online.gettrained.backend.domain.AuditableNonDeletableBaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 * Trainer connections with trainees.
 */
@Entity
@Table(name = "ACT_TRAINER_CONNECTIONS",
    indexes = {
        @Index(
            name = "I_ACT_TRAINER_CONNECTIONS_REF_TRAINER_USER_ID",
            columnList = "REF_TRAINER_USER_ID"),
        @Index(
            name = "I_ACT_TRAINER_CONNECTIONS_REF_TRAINEE_USER_ID",
            columnList = "REF_TRAINEE_USER_ID"),
        @Index(
            name = "I_ACT_TRAINER_CONNECTIONS_REF_TRAINER_ID_REF_TRAINEE_USER_ID",
            columnList = "REF_TRAINER_ID, REF_TRAINEE_USER_ID",
            unique = true)
    })
public class TrainerConnections extends AuditableNonDeletableBaseEntity {

  public enum Status {
    PENDING_ON_TRAINEE, CONNECTED
  }

  @Enumerated(STRING)
  @Column(name = "STATUS", nullable = false, length = 30)
  private Status status;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_TRAINER_ID", nullable = false)
  private Trainer trainer;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_TRAINER_USER_ID", nullable = false)
  private User userTrainer;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_TRAINEE_USER_ID", nullable = false)
  private User trainee;

  @Transient
  @JsonInclude(NON_NULL)
  private Long connectionId;

  @Transient
  @JsonInclude(NON_NULL)
  private Long trainerId;

  @Transient
  @JsonInclude(NON_NULL)
  private Long trainerUserId;

  @Transient
  @JsonInclude(NON_NULL)
  private String trainerFullName;

  @Transient
  @JsonInclude(NON_NULL)
  private String trainerLogoUrl;

  @Transient
  @JsonInclude(NON_NULL)
  private Long traineeUserId;

  @Transient
  @JsonInclude(NON_NULL)
  private String traineeFullName;

  @Transient
  @JsonInclude(NON_NULL)
  private String traineeLogoUrl;

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Trainer getTrainer() {
    return trainer;
  }

  public void setTrainer(Trainer trainer) {
    this.trainer = trainer;
  }

  public User getUserTrainer() {
    return userTrainer;
  }

  public void setUserTrainer(User userTrainer) {
    this.userTrainer = userTrainer;
  }

  public User getTrainee() {
    return trainee;
  }

  public void setTrainee(User trainee) {
    this.trainee = trainee;
  }

  public Long getConnectionId() {
    return connectionId;
  }

  public void setConnectionId(Long connectionId) {
    this.connectionId = connectionId;
  }

  public Long getTrainerId() {
    return trainerId;
  }

  public void setTrainerId(Long trainerId) {
    this.trainerId = trainerId;
  }

  public Long getTrainerUserId() {
    return trainerUserId;
  }

  public void setTrainerUserId(Long trainerUserId) {
    this.trainerUserId = trainerUserId;
  }

  public String getTrainerFullName() {
    return trainerFullName;
  }

  public void setTrainerFullName(String trainerFullName) {
    this.trainerFullName = trainerFullName;
  }

  public String getTrainerLogoUrl() {
    return trainerLogoUrl;
  }

  public void setTrainerLogoUrl(String trainerLogoUrl) {
    this.trainerLogoUrl = trainerLogoUrl;
  }

  public Long getTraineeUserId() {
    return traineeUserId;
  }

  public void setTraineeUserId(Long traineeUserId) {
    this.traineeUserId = traineeUserId;
  }

  public String getTraineeFullName() {
    return traineeFullName;
  }

  public void setTraineeFullName(String traineeFullName) {
    this.traineeFullName = traineeFullName;
  }

  public String getTraineeLogoUrl() {
    return traineeLogoUrl;
  }

  public void setTraineeLogoUrl(String traineeLogoUrl) {
    this.traineeLogoUrl = traineeLogoUrl;
  }
}
