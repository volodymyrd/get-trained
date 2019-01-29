package online.gettrained.backend.domain.localization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

/**
 * Cities, towns, villages
 */
@Entity
@Table(name = "LOCAL_CITIES")
public class City implements Serializable {

  private static final long serialVersionUID = -6471750727904643831L;

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "COUNTRY_CODE", nullable = false)
  private Country country;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MAIN_CITY_ID")
  private City mainCity;

  @JsonIgnore
  @ElementCollection
  @CollectionTable(name = "LOCAL_CITY_LOCALS", joinColumns = {
      @JoinColumn(name = "CITY_ID", referencedColumnName = "ID")})
  @MapKeyJoinColumn(name = "LANG_CODE")
  private Map<Language, CityLocal> mapLocals = new HashMap<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public City getMainCity() {
    return mainCity;
  }

  public void setMainCity(City mainCity) {
    this.mainCity = mainCity;
  }

  public Map<Language, CityLocal> getMapLocals() {
    return mapLocals;
  }

  public void setMapLocals(
      Map<Language, CityLocal> mapLocals) {
    this.mapLocals = mapLocals;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof City)) {
      return false;
    }
    City city = (City) o;
    return Objects.equals(id, city.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "City{" +
        "country=" + country +
        ", mapLocals=" + mapLocals +
        "} " + super.toString();
  }
}
