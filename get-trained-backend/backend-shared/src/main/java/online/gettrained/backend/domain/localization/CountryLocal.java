package online.gettrained.backend.domain.localization;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CountryLocal implements Serializable {

  private static final long serialVersionUID = -4702148770111766627L;

  /**
   * Country name
   */
  @Column(name = "NAME", length = 200, nullable = false)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
