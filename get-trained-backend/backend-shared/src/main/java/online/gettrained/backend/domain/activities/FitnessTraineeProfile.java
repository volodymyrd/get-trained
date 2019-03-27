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

  @Column(name = "WEIGHT", nullable = false)
  private double weight;

  @Column(name = "NECK", nullable = false)
  private double neck;

  @Column(name = "CHEST", nullable = false)
  private double chest;

  @Column(name = "WAIST", nullable = false)
  private double waist;

  @Column(name = "HIPS", nullable = false)
  private double hips;

  @Column(name = "WRIST", nullable = false)
  private double wrist;

  @Column(name = "FOREARM", nullable = false)
  private double forearm;

  @Column(name = "BICEPS", nullable = false)
  private double biceps;

  @Column(name = "INNER_THIGH", nullable = false)
  private double innerThigh;

  @Column(name = "CALF", nullable = false)
  private double calf;

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

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public double getNeck() {
    return neck;
  }

  public void setNeck(double neck) {
    this.neck = neck;
  }

  public double getChest() {
    return chest;
  }

  public void setChest(double chest) {
    this.chest = chest;
  }

  public double getWaist() {
    return waist;
  }

  public void setWaist(double waist) {
    this.waist = waist;
  }

  public double getHips() {
    return hips;
  }

  public void setHips(double hips) {
    this.hips = hips;
  }

  public double getWrist() {
    return wrist;
  }

  public void setWrist(double wrist) {
    this.wrist = wrist;
  }

  public double getForearm() {
    return forearm;
  }

  public void setForearm(double forearm) {
    this.forearm = forearm;
  }

  public double getBiceps() {
    return biceps;
  }

  public void setBiceps(double biceps) {
    this.biceps = biceps;
  }

  public double getInnerThigh() {
    return innerThigh;
  }

  public void setInnerThigh(double innerThigh) {
    this.innerThigh = innerThigh;
  }

  public double getCalf() {
    return calf;
  }

  public void setCalf(double calf) {
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
