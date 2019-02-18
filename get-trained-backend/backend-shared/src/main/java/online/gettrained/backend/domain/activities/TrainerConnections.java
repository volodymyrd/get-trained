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
 * Trainer connections with trainees.
 */
@Entity
@Table(name = "ACT_TRAINER_CONNECTIONS",
    indexes = {
        @Index(name = "I_ACT_TRAINER_CONNECTIONS_REF_USER_ID", columnList = "REF_USER_ID"),
        @Index(
            name = "I_ACT_TRAINER_CONNECTIONS_REF_TRAINER_ID_REF_USER_ID",
            columnList = "REF_TRAINER_ID, REF_USER_ID",
            unique = true)
    })
public class TrainerConnections extends BaseEntity {

  public enum Status {
    PENDING, CONNECTED
  }

  @Enumerated(STRING)
  @Column(name = "STATUS", nullable = false)
  private Status status;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_TRAINER_ID", nullable = false)
  private Trainer trainer;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "REF_USER_ID", nullable = false)
  private User trainee;
}
