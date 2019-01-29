package online.gettrained.backend.domain.localization;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CityLocal implements Serializable {

  private static final long serialVersionUID = 5701654824487037287L;
  /**
   * City name
   */
  @Column(name = "NAME", length = 100, nullable = false)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "CityLocal{" +
        "name='" + name + '\'' +
        '}';
  }
}
