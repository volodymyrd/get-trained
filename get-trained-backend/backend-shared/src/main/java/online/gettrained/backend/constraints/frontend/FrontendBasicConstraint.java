package online.gettrained.backend.constraints.frontend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.Set;
import online.gettrained.backend.constraints.DateSelectOption;
import online.gettrained.backend.constraints.StringSelectOption;
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
  private Set<StringSelectOption> soLangCodes;
  @JsonDeserialize(using = DateSelectOptionJsonDeserializer.class)
  private Set<DateSelectOption> soDateCreated;

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

  public Set<StringSelectOption> getSoLangCodes() {
    return soLangCodes;
  }

  public void setSoLangCodes(
      Set<StringSelectOption> soLangCodes) {
    this.soLangCodes = soLangCodes;
  }

  public Set<DateSelectOption> getSoDateCreated() {
    return soDateCreated;
  }

  public void setSoDateCreated(
      Set<DateSelectOption> soDateCreated) {
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
        ", soDateCreated=" + soDateCreated +
        ", timezoneOffset=" + timezoneOffset +
        ", withoutCount=" + withoutCount +
        ", withoutStat=" + withoutStat +
        '}';
  }
}
