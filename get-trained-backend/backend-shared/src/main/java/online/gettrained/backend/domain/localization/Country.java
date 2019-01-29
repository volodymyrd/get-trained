package online.gettrained.backend.domain.localization;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * List of countries
 */
@Entity
@Table(name = "LOCAL_COUNTRIES")
public class Country implements Serializable {

  private static final long serialVersionUID = 2683965811864565400L;

  public Country() {
  }

  public Country(String code, String phoneCode, String name) {
    this.code = code;
    this.phoneCode = phoneCode;
    this.name = name;
  }

  /**
   * Code country
   */
  @Id
  @Column(name = "CODE", length = 3, nullable = false)
  private String code;

  /**
   * Phone code
   */
  @Column(name = "PHONE_CODE", length = 7, nullable = false)
  private String phoneCode;

  @JsonIgnore
  @ElementCollection
  @CollectionTable(name = "LOCAL_COUNTRY_LOCALS", joinColumns = {
      @JoinColumn(name = "COUNTRY_CODE", referencedColumnName = "CODE")})
  @MapKeyJoinColumn(name = "LANG_CODE")
  private Map<Language, CountryLocal> mapLocals = new HashMap<>();

  @Transient
  private String name;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getPhoneCode() {
    return phoneCode;
  }

  public void setPhoneCode(String phoneCode) {
    this.phoneCode = phoneCode;
  }

  public Map<Language, CountryLocal> getMapLocals() {
    return mapLocals;
  }

  public void setMapLocals(Map<Language, CountryLocal> mapLocals) {
    this.mapLocals = mapLocals;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Country country = (Country) o;
    return Objects.equals(code, country.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }

  @Override
  public String toString() {
    return "Country{" +
        "code='" + code + '\'' +
        '}';
  }
}
