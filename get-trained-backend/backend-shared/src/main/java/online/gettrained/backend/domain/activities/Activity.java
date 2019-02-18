package online.gettrained.backend.domain.activities;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import online.gettrained.backend.domain.BaseEntity;

/**
 * List of sport activities.
 */
@Entity
@Table(name = "ACT_ACTIVITIES")
public class Activity extends BaseEntity {

  @JsonIgnore
  @Column(name = "DEFAULT_NAME", nullable = false)
  private String defaultName;

  @JsonIgnore
  @Column(name = "LOCAL_KEY_NAME", nullable = false)
  private String localKeyName;

  @Column(name = "ICON")
  private String icon;

  @JsonInclude(NON_NULL)
  private String name;
}
