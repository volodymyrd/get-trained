package online.gettrained.backend.domain.localization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import online.gettrained.backend.domain.AuditableBaseEntity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Localization
 */
@Entity
@Table(name = "LOCAL_LOCALIZATION",
    indexes = {
        @Index(name = "I_LANG_MODULE", columnList = "LANG, MODULE"),
        @Index(name = "I_KEY_LANG", columnList = "LOCAL_KEY, LANG", unique = true)
    })
public class Localization extends AuditableBaseEntity {

  private static final long serialVersionUID = -3597377899201450001L;

  public Localization() {
  }

  public Localization(Language lang, String localKey, ELocalModule module, String text) {
    this.lang = lang;
    this.localKey = localKey;
    this.module = module;
    this.text = text;
  }

  @JsonIgnore
  @JoinColumn(name = "LANG", nullable = false)
  @ManyToOne
  private Language lang;

  @Column(name = "LOCAL_KEY", length = 255, nullable = false)
  private String localKey;

  @Column(name = "MODULE", length = 255, nullable = false)
  @Enumerated(EnumType.STRING)
  private ELocalModule module;

  @Column(name = "LOCAL_TEXT", columnDefinition = "TEXT")
  private String text;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private Long localId;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private String langCode;

  public Language getLang() {
    return lang;
  }

  public void setLang(Language lang) {
    this.lang = lang;
  }

  public String getLocalKey() {
    return localKey;
  }

  public void setLocalKey(String localKey) {
    this.localKey = localKey;
  }

  public ELocalModule getModule() {
    return module;
  }

  public void setModule(ELocalModule module) {
    this.module = module;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getLocalId() {
    return localId;
  }

  public void setLocalId(Long localId) {
    this.localId = localId;
  }

  public String getLangCode() {
    return langCode;
  }

  public void setLangCode(String langCode) {
    this.langCode = langCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Localization that = (Localization) o;
    return Objects.equals(lang, that.lang) &&
        Objects.equals(localKey, that.localKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lang, localKey);
  }

  @Override
  public String toString() {
    return "Localization{" +
        "localKey='" + localKey + '\'' +
        ", module=" + module +
        ", text='" + text + '\'' +
        ", localId=" + localId +
        ", langCode='" + langCode + '\'' +
        "}";
  }
}
