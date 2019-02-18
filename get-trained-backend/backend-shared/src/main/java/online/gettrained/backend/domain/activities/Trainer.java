package online.gettrained.backend.domain.activities;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import online.gettrained.backend.domain.BaseEntity;
import online.gettrained.backend.domain.user.User;

/**
 * Trainers entity.
 */
@Entity
@Table(name = "ACT_TRAINERS",
    indexes = {
        @Index(name = "I_ACT_TRAINERS_REF_USER_ID", columnList = "REF_USER_ID"),
        @Index(
            name = "I_ACT_TRAINERS_REF_ACTIVITY_ID_REF_USER_ID",
            columnList = "REF_ACTIVITY_ID, REF_USER_ID",
            unique = true)
    })
public class Trainer extends BaseEntity {

  public enum Status {
    NEW, VERIFIED
  }

  @Enumerated(STRING)
  @Column(name = "STATUS", nullable = false)
  private Status status;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_USER_ID", nullable = false)
  private User user;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_ACTIVITY_ID", nullable = false)
  private Activity activity;
}
