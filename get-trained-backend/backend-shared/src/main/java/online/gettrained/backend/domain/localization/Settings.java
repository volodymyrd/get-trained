package online.gettrained.backend.domain.localization;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Global settings
 */
@Entity
@Table(name = "LOCAL_SETTINGS")
public class Settings implements Serializable {

  private static final long serialVersionUID = 6996414431779867102L;

  public enum Type {
    BACKEND, FRONTEND
  }

  public enum Key {
    MIN_PASSWORD_LENGTH("f_min_password_length"),
    MIN_USERNAME_LENGTH("f_min_username_length");

    private final String text;

    /**
     * @param text
     */
    private Key(final String text) {
      this.text = text;
    }

    @Override
    public String toString() {
      return text;
    }
  }

  @Embeddable
  public static class Pk implements Serializable {

    private static final long serialVersionUID = 2631249123109571459L;

    public Pk() {
    }

    public Pk(Type type, String settingKey) {
      this.type = type;
      this.settingKey = settingKey;
    }

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "SETTING_TYPE", length = 10, nullable = false)
    private Type type;

    @Column(name = "SETTING_KEY", length = 100, nullable = false, unique = true)
    private String settingKey;

    public Type getType() {
      return type;
    }

    public void setType(Type type) {
      this.type = type;
    }

    public String getSettingKey() {
      return settingKey;
    }

    public void setSettingKey(String settingKey) {
      this.settingKey = settingKey;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Pk)) {
        return false;
      }
      Pk pk = (Pk) o;
      return type == pk.type &&
          Objects.equals(settingKey, pk.settingKey);
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, settingKey);
    }
  }

  public Settings() {
  }

  public Settings(Pk pk, String settingValue) {
    this.pk = pk;
    this.settingValue = settingValue;
  }

  @EmbeddedId
  private Pk pk;

  @JsonIgnore
  @Column(name = "DATE_CREATE", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateCreate = new Date();

  @JsonIgnore
  @Column(name = "DATE_UPDATE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateUpdate;

  @Column(name = "SETTING_VALUE", length = 400, nullable = false)
  private String settingValue;

  public Pk getPk() {
    return pk;
  }

  public void setPk(Pk pk) {
    this.pk = pk;
  }

  public Date getDateCreate() {
    return dateCreate;
  }

  public void setDateCreate(Date dateCreate) {
    this.dateCreate = dateCreate;
  }

  public Date getDateUpdate() {
    return dateUpdate;
  }

  public void setDateUpdate(Date dateUpdate) {
    this.dateUpdate = dateUpdate;
  }

  public String getSettingValue() {
    return settingValue;
  }

  public void setSettingValue(String settingValue) {
    this.settingValue = settingValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Settings)) {
      return false;
    }
    Settings settings = (Settings) o;
    return Objects.equals(pk, settings.pk);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pk);
  }
}
