package online.gettrained.backend.domain.activities;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import online.gettrained.backend.domain.AuditableBaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 * Trainee profile in fitness activity.
 */
@Entity
@Table(name = "ACT_FITNESS_TRAINEE_PROFILES",
    indexes = {@Index(
        name = "I_ACT_FITNESS_TRAINEE_PROFILES_CONNECTION_ID_TRAINEE_ID_MEASURE",
        columnList = "REF_CONNECTION_ID, TRAINEE_ID, DATE_MEASURE",
        unique = true)})
public class FitnessTraineeProfile extends AuditableBaseEntity {

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_CONNECTION_ID", nullable = false)
  private TrainerConnections connection;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "TRAINEE_ID", nullable = false)
  private User trainee;

  @JsonIgnore
  @Temporal(TIMESTAMP)
  @Column(name = "DATE_MEASURE", nullable = false)
  private Date dateMeasure;

  @JsonInclude(NON_NULL)
  @Column(name = "NECK", nullable = false)
  private Double neck;

  @JsonInclude(NON_NULL)
  @Column(name = "CHEST", nullable = false)
  private Double chest;

  @JsonInclude(NON_NULL)
  @Column(name = "WAIST", nullable = false)
  private Double waist;

  @JsonInclude(NON_NULL)
  @Column(name = "HIPS", nullable = false)
  private Double hips;

  @JsonInclude(NON_NULL)
  @Column(name = "WRIST", nullable = false)
  private Double wrist;

  @JsonInclude(NON_NULL)
  @Column(name = "FOREARM", nullable = false)
  private Double forearm;

  @JsonInclude(NON_NULL)
  @Column(name = "BICEPS", nullable = false)
  private Double biceps;

  @JsonInclude(NON_NULL)
  @Column(name = "INNER_THIGH", nullable = false)
  private Double innerThigh;

  @JsonInclude(NON_NULL)
  @Column(name = "CALF", nullable = false)
  private Double calf;

  @Transient
  @JsonInclude(NON_NULL)
  private Long traineeProfileId;

  @Transient
  @JsonInclude(NON_NULL)
  private Long connectionId;

  @Transient
  @JsonInclude(NON_NULL)
  private Long traineeUserId;

  @Transient
  @JsonInclude(NON_NULL)
  private String measure;

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

  public Date getDateMeasure() {
    return dateMeasure;
  }

  public void setDateMeasure(Date dateMeasure) {
    this.dateMeasure = dateMeasure;
  }

  public Double getNeck() {
    return neck;
  }

  public void setNeck(Double neck) {
    this.neck = neck;
  }

  public Double getChest() {
    return chest;
  }

  public void setChest(Double chest) {
    this.chest = chest;
  }

  public Double getWaist() {
    return waist;
  }

  public void setWaist(Double waist) {
    this.waist = waist;
  }

  public Double getHips() {
    return hips;
  }

  public void setHips(Double hips) {
    this.hips = hips;
  }

  public Double getWrist() {
    return wrist;
  }

  public void setWrist(Double wrist) {
    this.wrist = wrist;
  }

  public Double getForearm() {
    return forearm;
  }

  public void setForearm(Double forearm) {
    this.forearm = forearm;
  }

  public Double getBiceps() {
    return biceps;
  }

  public void setBiceps(Double biceps) {
    this.biceps = biceps;
  }

  public Double getInnerThigh() {
    return innerThigh;
  }

  public void setInnerThigh(Double innerThigh) {
    this.innerThigh = innerThigh;
  }

  public Double getCalf() {
    return calf;
  }

  public void setCalf(Double calf) {
    this.calf = calf;
  }

  public Long getTraineeProfileId() {
    return traineeProfileId;
  }

  public void setTraineeProfileId(Long traineeProfileId) {
    this.traineeProfileId = traineeProfileId;
  }

  public Long getConnectionId() {
    return connectionId;
  }

  public void setConnectionId(Long connectionId) {
    this.connectionId = connectionId;
  }

  public Long getTraineeUserId() {
    return traineeUserId;
  }

  public void setTraineeUserId(Long traineeUserId) {
    this.traineeUserId = traineeUserId;
  }

  public String getMeasure() {
    return measure;
  }

  public void setMeasure(String measure) {
    this.measure = measure;
  }

  @Override
  public String toString() {
    return "TraineeProfile{" +
        "neck=" + neck +
        ", chest=" + chest +
        ", waist=" + waist +
        ", hips=" + hips +
        ", wrist=" + wrist +
        ", forearm=" + forearm +
        ", biceps=" + biceps +
        ", innerThigh=" + innerThigh +
        ", calf=" + calf +
        ", traineeProfileId=" + traineeProfileId +
        ", connectionId=" + connectionId +
        ", traineeUserId=" + traineeUserId +
        ", measure='" + measure + '\'' +
        "} " + super.toString();
  }
}
