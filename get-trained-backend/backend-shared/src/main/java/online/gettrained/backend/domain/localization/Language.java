package online.gettrained.backend.domain.localization;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * List of languages
 */
@Entity
@Table(name = "LOCAL_LANGUAGES")
public class Language implements Serializable {

  public static final String DEFAULT_CODE = "EN";

  private static final long serialVersionUID = 3528869551271345916L;

  public Language() {
  }

  public Language(String code) {
    this.code = code;
  }

  /**
   * Language code
   */
  @Id
  @Column(name = "CODE", length = 3, nullable = false)
  private String code;

  @JsonIgnore
  @ElementCollection
  @CollectionTable(name = "LOCAL_LANGUAGE_LOCALS", joinColumns = {
      @JoinColumn(name = "LANG_CODE_LOCAL", referencedColumnName = "CODE")})
  @MapKeyJoinColumn(name = "LANG_CODE")
  private Map<Language, CountryLocal> mapLocals = new HashMap<>();

  /**
   * Is language default?
   */
  @JsonIgnore
  @Column(name = "IS_DEFAULT")
  private Boolean isdefault;

  /**
   * Is language supported?
   */
  @JsonIgnore
  @Column(name = "IS_SUPPORTED")
  private Boolean issupported;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Map<Language, CountryLocal> getMapLocals() {
    return mapLocals;
  }

  public void setMapLocals(Map<Language, CountryLocal> mapLocals) {
    this.mapLocals = mapLocals;
  }

  public Boolean getIsdefault() {
    return isdefault;
  }

  public void setIsdefault(Boolean isdefault) {
    this.isdefault = isdefault;
  }

  public Boolean getIssupported() {
    return issupported;
  }

  public void setIssupported(Boolean issupported) {
    this.issupported = issupported;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Language)) {
      return false;
    }
    Language language = (Language) o;
    return Objects.equals(code, language.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }

  @Override
  public String toString() {
    return "Language{" +
        "code='" + code + '\'' +
        '}';
  }
}
