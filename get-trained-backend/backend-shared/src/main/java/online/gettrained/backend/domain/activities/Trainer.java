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
import online.gettrained.backend.domain.BaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 * Trainers entity.
 */
@Entity
@Table(name = "ACT_TRAINERS",
    indexes = {
        @Index(name = "I_ACT_TRAINERS_REF_USER_ID", columnList = "REF_USER_ID"),
        @Index(name = "I_ACT_TRAINERS_STATUS", columnList = "STATUS"),
        @Index(name = "I_ACT_TRAINERS_VISIBILITY", columnList = "VISIBILITY"),
        @Index(
            name = "I_ACT_TRAINERS_REF_ACTIVITY_ID_REF_USER_ID",
            columnList = "REF_ACTIVITY_ID, REF_USER_ID",
            unique = true)
    })
public class Trainer extends BaseEntity {

  public enum Status {
    NEW, PENDING, VERIFIED
  }

  public enum Visibility {
    PUBLIC, PRIVATE, HIDDEN
  }

  @JsonIgnore
  @Enumerated(STRING)
  @Column(name = "STATUS", nullable = false)
  private Status status;

  @JsonIgnore
  @Enumerated(STRING)
  @Column(name = "VISIBILITY", nullable = false)
  private Visibility visibility;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_USER_ID", nullable = false)
  private User user;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_ACTIVITY_ID", nullable = false)
  private Activity activity;

  @Transient
  @JsonInclude(NON_NULL)
  private Long trainerId;

  @Transient
  @JsonInclude(NON_NULL)
  private Long trainerUserId;

  @Transient
  @JsonInclude(NON_NULL)
  private Long activityId;

  @Transient
  @JsonInclude(NON_NULL)
  private String activityName;

  @Transient
  @JsonInclude(NON_NULL)
  private String fullName;

  @Transient
  @JsonInclude(NON_NULL)
  private String logoUrl;

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
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

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }
}
