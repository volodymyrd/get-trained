package online.gettrained.backend.domain.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import online.gettrained.backend.domain.BaseEntity;
import online.gettrained.backend.domain.localization.City;
import online.gettrained.backend.domain.localization.Country;
import online.gettrained.backend.domain.localization.Language;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User profile
 */
@Entity
@Table(name = "USR_PROFILES",
    indexes = {
        @Index(name = "I_USR_PROFILES_FIRST_NAME", columnList = "FIRST_NAME"),
        @Index(name = "I_USR_PROFILES_LAST_NAME", columnList = "LAST_NAME"),
        @Index(name = "I_USR_PROFILES_FULL_NAME", columnList = "FULL_NAME")
    })
public class Profile extends BaseEntity {

  private static final long serialVersionUID = -4793894790901691011L;

  public enum Gender {
    MALE, FEMALE, OTHER, UNSPECIFIED
  }

  public Profile() {
  }

  public Profile(String firstName) {
    this.firstName = firstName.trim();
    this.fullName = buildFullName();
  }

  public Profile(String firstName, String lastName) {
    this.firstName = firstName.trim();
    this.lastName = lastName.trim();
    this.fullName = buildFullName();
  }

  @Column(name = "FIRST_NAME", nullable = false)
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "FULL_NAME", nullable = false, length = 400)
  private String fullName;

  @Column(name = "GENDER", length = 20)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @JsonIgnore
  @Column(name = "BIRTHDAY")
  @Temporal(TemporalType.DATE)
  private Date birthday;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "COUNTRY_CODE")
  private Country country;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CITY_ID")
  private City city;

  @Column(name = "ABOUT_ME", length = 400)
  private String aboutMe;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "LANG_CODE")
  private Language language;

  @JsonIgnore
  @Column(name = "BLOB_DATA_ID")
  private Long avatarId;

  @JsonIgnore
  @OneToMany(mappedBy = "pk.profile", fetch = FetchType.LAZY)
  private Set<ProfileEmail> setEmails = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "pk.profile", fetch = FetchType.LAZY)
  private Set<ProfilePhone> setPhones = new HashSet<>();

  @Transient
  private Long userId;

  @Transient
  private String lang;

  @Transient
  private String avatarUrl;

  @Transient
  private String countryCode;

  @Transient
  @JsonInclude(Include.NON_NULL)
  private Long cityId;

  @Transient
  private String birthdayStr;

  @Transient
  private String registrationDate;

  @Transient
  private Map<String, String> genders;

  @Transient
  private Map<String, Country> countries;

  @Transient
  private Map<String, String> langs;

  @Transient
  private Map<Long, String> cities;

  @Transient
  private Set<String> emails = new HashSet<>();

  @Transient
  private Set<String> phones = new HashSet<>();

  @Transient
  private Set<String> roles = new HashSet<>();

  public String buildFullName() {
    String name = "";
    if (this.firstName != null && !this.firstName.isEmpty()) {
      name = this.firstName;
    }
    if (this.lastName != null && !this.lastName.isEmpty()) {
      if (!name.isEmpty()) {
        name = name.trim() + " " + this.lastName;
      } else {
        name = this.lastName;
      }
    }

    return name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public Long getAvatarId() {
    return avatarId;
  }

  public void setAvatarId(Long avatarId) {
    this.avatarId = avatarId;
  }

  public Set<ProfileEmail> getSetEmails() {
    return setEmails;
  }

  public void setSetEmails(Set<ProfileEmail> setEmails) {
    this.setEmails = setEmails;
  }

  public Set<ProfilePhone> getSetPhones() {
    return setPhones;
  }

  public void setSetPhones(Set<ProfilePhone> setPhones) {
    this.setPhones = setPhones;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public Long getCityId() {
    return cityId;
  }

  public void setCityId(Long cityId) {
    this.cityId = cityId;
  }

  public String getBirthdayStr() {
    return birthdayStr;
  }

  public void setBirthdayStr(String birthdayStr) {
    this.birthdayStr = birthdayStr;
  }

  public Map<String, String> getGenders() {
    return genders;
  }

  public void setGenders(Map<String, String> genders) {
    this.genders = genders;
  }

  public Map<String, Country> getCountries() {
    return countries;
  }

  public void setCountries(
      Map<String, Country> countries) {
    this.countries = countries;
  }

  public Map<String, String> getLangs() {
    return langs;
  }

  public void setLangs(Map<String, String> langs) {
    this.langs = langs;
  }

  public Map<Long, String> getCities() {
    return cities;
  }

  public void setCities(Map<Long, String> cities) {
    this.cities = cities;
  }

  public Set<String> getEmails() {
    return emails;
  }

  public void setEmails(Set<String> emails) {
    this.emails = emails;
  }

  public Set<String> getPhones() {
    return phones;
  }

  public void setPhones(Set<String> phones) {
    this.phones = phones;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "Profile{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", gender=" + gender +
        ", emails=" + emails +
        ", phones=" + phones +
        ", lang='" + lang + '\'' +
        ", countryCode='" + countryCode + '\'' +
        ", birthdayStr='" + birthdayStr + '\'' +
        "} " + super.toString();
  }
}
