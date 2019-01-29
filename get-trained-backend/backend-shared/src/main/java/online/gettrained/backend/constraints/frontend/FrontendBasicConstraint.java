package online.gettrained.backend.constraints.frontend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import online.gettrained.backend.constraints.SelectOption;
import online.gettrained.backend.json_serializers.DateSelectOptionJsonDeserializer;
import online.gettrained.backend.json_serializers.PageableJsonDeserializer;
import org.springframework.data.domain.Pageable;

/**
 * Basic frontend constraint.
 */
public class FrontendBasicConstraint implements Serializable {

  private static final long serialVersionUID = -5398111912615855821L;

  private String langCode;
  @JsonDeserialize(using = PageableJsonDeserializer.class)
  private Pageable pageable;
  private Set<SelectOption<String>> soLangCodes;
  private Set<SelectOption<Long>> soCompanyIds;
  @JsonDeserialize(using = DateSelectOptionJsonDeserializer.class)
  private Set<SelectOption<Date>> soDateCreated;

  @JsonIgnore
  private Integer timezoneOffset;
  @JsonIgnore
  private Boolean withoutCount;
  @JsonIgnore
  private Boolean withoutStat;

  public String getLangCode() {
    return langCode;
  }

  public void setLangCode(String langCode) {
    this.langCode = langCode;
  }

  public Pageable getPageable() {
    return pageable;
  }

  public void setPageable(Pageable pageable) {
    this.pageable = pageable;
  }

  public Set<SelectOption<String>> getSoLangCodes() {
    return soLangCodes;
  }

  public void setSoLangCodes(
      Set<SelectOption<String>> soLangCodes) {
    this.soLangCodes = soLangCodes;
  }

  public Set<SelectOption<Long>> getSoCompanyIds() {
    return soCompanyIds;
  }

  public void setSoCompanyIds(
      Set<SelectOption<Long>> soCompanyIds) {
    this.soCompanyIds = soCompanyIds;
  }

  public Set<SelectOption<Date>> getSoDateCreated() {
    return soDateCreated;
  }

  public void setSoDateCreated(
      Set<SelectOption<Date>> soDateCreated) {
    this.soDateCreated = soDateCreated;
  }

  public Integer getTimezoneOffset() {
    return timezoneOffset;
  }

  public void setTimezoneOffset(Integer timezoneOffset) {
    this.timezoneOffset = timezoneOffset;
  }

  public Boolean getWithoutCount() {
    return withoutCount;
  }

  public void setWithoutCount(Boolean withoutCount) {
    this.withoutCount = withoutCount;
  }

  public Boolean getWithoutStat() {
    return withoutStat;
  }

  public void setWithoutStat(Boolean withoutStat) {
    this.withoutStat = withoutStat;
  }

  @Override
  public String toString() {
    return "FrontendBasicConstraint{" +
        "langCode='" + langCode + '\'' +
        ", pageable=" + pageable +
        ", soLangCodes=" + soLangCodes +
        ", soCompanyIds=" + soCompanyIds +
        ", soDateCreated=" + soDateCreated +
        ", timezoneOffset=" + timezoneOffset +
        ", withoutCount=" + withoutCount +
        ", withoutStat=" + withoutStat +
        '}';
  }
}
