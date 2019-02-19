package online.gettrained.backend.domain.activities;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static javax.persistence.EnumType.STRING;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import online.gettrained.backend.domain.BaseEntity;

/**
 * List of sport activities.
 */
@Entity
@Table(name = "ACT_ACTIVITIES",
    indexes = {@Index(name = "I_ACT_ACTIVITIES_STATUS", columnList = "STATUS")})
public class Activity extends BaseEntity {

  public enum Status {
    ACTIVE, DISABLE
  }

  @JsonIgnore
  @Enumerated(STRING)
  @Column(name = "STATUS", nullable = false)
  private Status status;

  @JsonIgnore
  @Column(name = "DEFAULT_NAME", nullable = false)
  private String defaultName;

  @JsonIgnore
  @Column(name = "LOCAL_KEY_NAME", nullable = false)
  private String localKeyName;

  @Column(name = "ICON")
  private String icon;

  @Transient
  @JsonInclude(NON_NULL)
  private Long activityId;

  @Transient
  @JsonInclude(NON_NULL)
  private String name;

  @Transient
  @JsonInclude(NON_NULL)
  private Integer numberOfTrainers;

  public String getDefaultName() {
    return defaultName;
  }

  public void setDefaultName(String defaultName) {
    this.defaultName = defaultName;
  }

  public String getLocalKeyName() {
    return localKeyName;
  }

  public void setLocalKeyName(String localKeyName) {
    this.localKeyName = localKeyName;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getNumberOfTrainers() {
    return numberOfTrainers;
  }

  public void setNumberOfTrainers(Integer numberOfTrainers) {
    this.numberOfTrainers = numberOfTrainers;
  }
}
