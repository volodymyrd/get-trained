package online.gettrained.frontend.web.profile;

import static com.google.common.base.Strings.isNullOrEmpty;
import static online.gettrained.backend.exceptions.ErrorCode.SOMETHING_WENT_WRONG;
import static online.gettrained.backend.exceptions.ErrorCode.WRONG_PASSWORD;
import static online.gettrained.backend.messages.TextCode.PROFILE_SUCCESS_AVATAR_DELETE;
import static online.gettrained.backend.messages.TextCode.SETTINGS_SUCCESS_PASSWORD_CHANGED;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import online.gettrained.backend.domain.localization.City;
import online.gettrained.backend.domain.localization.Country;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.BlobDataDto;
import online.gettrained.backend.dto.user.UserSecDto;
import online.gettrained.backend.exceptions.ErrorCode;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.blob.BlobDataService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.services.user.UserService;
import online.gettrained.backend.utils.CommonUtils;
import online.gettrained.backend.utils.DateUtils;
import online.gettrained.frontend.validators.email.EmailValidator;
import online.gettrained.frontend.validators.password.PasswordValidator;
import online.gettrained.frontend.validators.username.UsernameValidator;
import online.gettrained.frontend.web.Utils;
import online.gettrained.backend.dto.TextInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fe/profile/user")
public class UserProfileRestController {

  private static final Logger LOG = LoggerFactory.getLogger(UserProfileRestController.class);

  private static final long AVATAR_MAX_SIZE = 5000000;

  private final AuthService authService;
  private final PasswordEncoder passwordEncoder;
  private final PasswordValidator passwordValidator;
  private final UsernameValidator usernameValidator;
  private final EmailValidator emailValidator;
  private final LocalizationService localizationService;
  private final UserService userService;
  private final BlobDataService blobDataService;

  public UserProfileRestController(
      AuthService authService,
      PasswordEncoder passwordEncoder,
      PasswordValidator passwordValidator,
      UsernameValidator usernameValidator,
      EmailValidator emailValidator,
      LocalizationService localizationService,
      UserService userService,
      BlobDataService blobDataService) {
    this.authService = authService;
    this.passwordEncoder = passwordEncoder;
    this.passwordValidator = passwordValidator;
    this.usernameValidator = usernameValidator;
    this.emailValidator = emailValidator;
    this.localizationService = localizationService;
    this.userService = userService;
    this.blobDataService = blobDataService;
  }

  @PostMapping("/get-sec")
  public ResponseEntity<?> getSec() {
    LOG.info("Calling method 'getSec' of UserProfileRestController");

    User user = authService.getCurrentUserOrException();

    UserSecDto userSecDto = new UserSecDto(user.getUserName(), user.getEmail());

    if (LOG.isDebugEnabled()) {
      LOG.debug("User sec data for user with id: {}, found and return", user.getId());
    }

    return ResponseEntity.ok(userSecDto);
  }

  @PostMapping("/avatar/del")
  public ResponseEntity<?> delAvatar() {

    LOG.info("Calling method 'delAvatar' of UserProfileRestController");

    User user = authService.getCurrentUserOrException();

    Optional<User> userOptional = userService.findByIdWithProfile(user.getId());
    if (!userOptional.isPresent()) {
      LOG.error("User with id:{} not found", user.getEmail());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    user = userOptional.get();

    try {
      userService.removeAvatar(user, user.getProfile());
      if (LOG.isDebugEnabled()) {
        LOG.debug("Avatar for user profile with id: {} deleted successfully", user.getId());
      }
      return ResponseEntity
          .ok(new TextInfoDto(PROFILE_SUCCESS_AVATAR_DELETE,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(
                  PROFILE_SUCCESS_AVATAR_DELETE.toString(),
                  Utils.getLanguage(user),
                  "Avatar deleted successfully."
              )));
    } catch (Exception ex) {
      LOG.error("Error deleting an avatar", ex);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }
  }

  @PostMapping("/avatar/load")
  public ResponseEntity<?> loadAvatar(@RequestParam("file") MultipartFile multipartFile) {

    LOG.info("Calling method 'loadAvatar' of UserProfileRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug(
          "Calling method 'loadAvatar' of UserProfileRestController with multipartFile:{}",
          multipartFile);
    }

    User user = authService.getCurrentUserOrException();

    Optional<User> userOptional = userService.findByIdWithProfileWithLang(user.getId());
    if (!userOptional.isPresent()) {
      LOG.error("User with id:{} not found", user.getEmail());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    user = userOptional.get();

    if (multipartFile.getSize() > AVATAR_MAX_SIZE) {
      LOG.error("Avatar size exceed the max size");
      ErrorCode errorCode = ErrorCode.USER_PROFILE_AVATAR_EXCEED_MAX_SIZE;
      return ResponseEntity.badRequest().body(
          new ErrorInfoDto(errorCode,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(errorCode.toString(),
                  user.getLoginLang(),
                  "Avatar size must be less than {0} Mb",
                  AVATAR_MAX_SIZE / 1000000)));
    }

    try {
      List<BlobDataDto> dtos = blobDataService
          .save(CommonUtils.immutableListOf(multipartFile), null);
      if (dtos.size() == 0) {
        return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
            localizationService
                .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                    Utils.getLanguage(user),
                    "Something went wrong!")));
      }

      try {
        Profile profile = user.getProfile();
        Long oldBlobId = profile.getAvatarId();
        profile.setAvatarId(dtos.get(0).getId());
        userService.saveProfile(user, profile);
        if (LOG.isDebugEnabled()) {
          LOG.debug("User profile with id: {}, saved successfully", user.getId());
        }

        if (oldBlobId != null) {
          blobDataService.deleteBlobDataById(oldBlobId);
        }
      } catch (Exception ex) {
        LOG.error("Error saving a user profile", ex);
        blobDataService.deleteBlobDataById(dtos.get(0).getId());
        return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
            localizationService
                .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                    Utils.getLanguage(user),
                    "Something went wrong!")));
      }

      return ResponseEntity.ok(dtos);
    } catch (Exception e) {
      LOG.error("Failed update avatar", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }
  }

  @PostMapping("/update-sec/password")
  public ResponseEntity<?> updatePassword(@RequestBody UserSecDto userSecDto) {
    LOG.info("Calling method 'updatePassword' of UserProfileRestController");

    User user = authService.getCurrentUserOrException();

    if (isNullOrEmpty(userSecDto.getPassword())
        || isNullOrEmpty(userSecDto.getNewPassword())
        || isNullOrEmpty(userSecDto.getConfirmPassword())) {
      LOG.error("Calling method 'updatePassword' of UserProfileRestController: "
          + "Illegal argument exception: "
          + " parameters 'password', 'newPassword', 'confirmPassword' must be filled");
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    ErrorCode errorCode = passwordValidator.validate(
        userSecDto.getPassword(),
        user.getPassword(),
        userSecDto.getNewPassword(),
        userSecDto.getConfirmPassword());
    if (errorCode.isError()) {
      LOG.error("Password validation error");
      return ResponseEntity.badRequest().body(
          new ErrorInfoDto(errorCode,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(errorCode.toString(),
                  user.getLoginLang(),
                  errorCode == WRONG_PASSWORD ? "Wrong password" : "Password validation error")));
    } else {
      Optional<User> userOptional = userService.findById(user.getId());
      if (!userOptional.isPresent()) {
        LOG.error("The user with id:{} not found ", user.getId());
        return ResponseEntity.badRequest().body(new ErrorInfoDto(
            SOMETHING_WENT_WRONG,
            localizationService.getLocalTextByKeyAndLangOrUseDefault(
                SOMETHING_WENT_WRONG.toString(),
                Utils.getLanguage(user),
                "Something went wrong!")));
      }
      user = userOptional.get();
      user.setPassword(passwordEncoder.encode(userSecDto.getNewPassword()));
    }

    try {
      authService.setCurrentUser(userService.saveUser(user));
      if (LOG.isDebugEnabled()) {
        LOG.debug("The password for user with id: {} updated successfully", user.getId());
      }

      return ResponseEntity
          .ok(new TextInfoDto(SETTINGS_SUCCESS_PASSWORD_CHANGED,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(
                  SETTINGS_SUCCESS_PASSWORD_CHANGED.toString(),
                  Utils.getLanguage(user),
                  "Password changed successfully."
              )));
    } catch (Exception ex) {
      LOG.error("Error updating the password", ex);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }
  }


  @PostMapping("/update-sec/email")
  public ResponseEntity<?> updateEmail(@RequestBody UserSecDto userSecDto) {
    LOG.info("Calling method 'updateEmail' of UserProfileRestController");

    User user = authService.getCurrentUserOrException();

    if (isNullOrEmpty(userSecDto.getEmail()) || isNullOrEmpty(userSecDto.getPassword())) {
      LOG.error("Calling method 'updateEmail' of UserProfileRestController: "
          + "Illegal argument exception: parameters 'email', 'password', must be filled");
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug(
            "Calling method 'updateEmail' of UserProfileRestController with email:{} and ldap:{}",
            userSecDto.getEmail(), user.getLdap());
      }
    }

    if (user.getLdap() == null || !user.getLdap()) {
      ErrorCode errorCode = passwordValidator
          .validate(userSecDto.getPassword(), user.getPassword());
      if (errorCode.isError()) {
        LOG.error("Wrong password");
        return ResponseEntity.badRequest().body(
            new ErrorInfoDto(errorCode,
                localizationService.getLocalTextByKeyAndLangOrUseDefault(errorCode.toString(),
                    user.getLoginLang(),
                    "Wrong password")));
      }
    }

    userSecDto.setEmail(userSecDto.getEmail().toLowerCase());

    ErrorCode errorCode = emailValidator.validate(userSecDto.getEmail(), true);
    if (errorCode.isError()) {
      LOG.error("Email validation error");
      return ResponseEntity.badRequest().body(
          new ErrorInfoDto(errorCode,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(errorCode.toString(),
                  user.getLoginLang(),
                  "Email validation error")));
    } else {
      Optional<User> userOptional = userService.findById(user.getId());
      if (!userOptional.isPresent()) {
        LOG.error("The user with id:{} not found ", user.getId());
        return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
            localizationService
                .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                    Utils.getLanguage(user),
                    "Something went wrong!")));
      }
      user = userOptional.get();
      user.setEmail(userSecDto.getEmail());
    }

    try {
      authService.setCurrentUser(userService.saveUser(user));
      if (LOG.isDebugEnabled()) {
        LOG.debug("The email for user with id: {} updated successfully", user.getId());
      }

      return ResponseEntity.ok().build();
    } catch (Exception ex) {
      LOG.error("Error updating the email", ex);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }
  }

  @PostMapping("/update-sec/username")
  public ResponseEntity<?> updateUsername(@RequestBody UserSecDto userSecDto) {
    LOG.info("Calling method 'updateUsername' of UserProfileRestController");

    User user = authService.getCurrentUserOrException();

    if (isNullOrEmpty(userSecDto.getUserName()) || isNullOrEmpty(userSecDto.getPassword())) {
      LOG.error("Calling method 'updateUsername' of UserProfileRestController: "
          + "Illegal argument exception: parameters 'userName', 'password', must be filled");
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug(
            "Calling method 'updateUsername' of UserProfileRestController with username:{}",
            userSecDto.getUserName());
      }
    }

    ErrorCode errorCode = passwordValidator.validate(userSecDto.getPassword(), user.getPassword());
    if (errorCode.isError()) {
      LOG.error("Wrong password");
      return ResponseEntity.badRequest().body(
          new ErrorInfoDto(errorCode,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(errorCode.toString(),
                  user.getLoginLang(),
                  "Wrong password")));
    }

    userSecDto.setUserName(userSecDto.getUserName().toLowerCase());

    errorCode = usernameValidator.validate(userSecDto.getUserName());
    if (errorCode.isError()) {
      LOG.error("Username validation error");
      return ResponseEntity.badRequest().body(
          new ErrorInfoDto(errorCode,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(errorCode.toString(),
                  user.getLoginLang(),
                  "Username validation error")));
    } else {
      Optional<User> userOptional = userService.findById(user.getId());
      if (!userOptional.isPresent()) {
        LOG.error("The user with id:{} not found ", user.getId());
        return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
            localizationService
                .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                    Utils.getLanguage(user),
                    "Something went wrong!")));
      }
      user = userOptional.get();
      user.setUserName(userSecDto.getUserName());
    }

    try {
      authService.setCurrentUser(userService.saveUser(user));
      if (LOG.isDebugEnabled()) {
        LOG.debug("The username for user with id: {} updated successfully", user.getId());
      }

      return ResponseEntity.ok().build();
    } catch (Exception ex) {
      LOG.error("Error updating the username", ex);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }
  }

  @PostMapping("/getLight")
  public ResponseEntity<?> getLight() {
    LOG.info("Calling method 'getLight' of UserProfileRestController");

    User user = authService.getCurrentUserOrException();

    Optional<User> userOptional = userService
        .findByIdWithFullProfileAndLangAndCountryAndRoles(user.getId());
    if (!userOptional.isPresent()) {
      LOG.error("User with id:{} not fount", user.getId());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    user = userOptional.get();

    Profile profile = user.getProfile();
    profile.setUserId(user.getId());
    if (user.getProfile().getAvatarId() != null) {
      profile.setAvatarUrl(blobDataService.getFileUrl(user.getProfile().getAvatarId()));
    }

    profile.setRoles(
        user.getRoles().stream().map(r -> "S_" + r.getName()).collect(Collectors.toSet()));

    if (LOG.isDebugEnabled()) {
      LOG.debug("Profile for user with id: {}, found and return", user.getId());
    }

    return ResponseEntity.ok(profile);
  }

  @PostMapping("/getCitiesByCountryCode")
  public ResponseEntity<?> getCitiesByCountryCode(@RequestParam("countryCode") String countryCode) {
    LOG.info("Calling method 'getCitiesByCountryCode' of UserProfileRestController");

    LOG.debug("Calling method 'getCitiesByCountryCode' of UserProfileRestController "
        + "with countryCode:{}", countryCode);

    User user = authService.getCurrentUserOrException();

    return ResponseEntity
        .ok(localizationService
            .findAllCitiesByCountryCodeAndLangWithLocals(
                countryCode, user.getLoginLang().getCode()));
  }

  @PostMapping("/get")
  public ResponseEntity<?> get() {
    LOG.info("Calling method 'get' of UserProfileRestController");

    User user = authService.getCurrentUserOrException();
    String langCode = user.getLoginLang().getCode();

    Optional<User> userOptional = userService
        .findByIdWithFullProfileAndLangAndCountryAndCity(user.getId());
    if (!userOptional.isPresent()) {
      LOG.error("User with id:{} not fount", user.getEmail());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    user = userOptional.get();

    Profile profile = user.getProfile();
    profile.setRegistrationDate(DateUtils.getShortDateFormat().format(user.getDateCreate()));
    profile.setLang(profile.getLanguage().getCode());
    profile.setCountryCode(profile.getCountry() != null ? profile.getCountry().getCode() : "");
    profile.setCityId(profile.getCity() != null ? profile.getCity().getId() : null);
    profile.setBirthdayStr(profile.getBirthday() != null ?
        DateUtils.getShortDateFormat().format(profile.getBirthday()) : "");
    profile.setEmails(profile.getSetEmails().stream().map(e -> e.getPk().getEmail().getEmail())
        .collect(Collectors.toSet()));
    profile.setPhones(profile.getSetPhones().stream().map(e -> e.getPk().getPhone().getPhone())
        .collect(Collectors.toSet()));
    profile.setLangs(localizationService.findAllLanguagesWithLocalsByLang(langCode));
    profile.setCountries(localizationService.findAllCountriesWithLocalsByLang(langCode));
    if (profile.getCountryCode() != null && !profile.getCountryCode().isEmpty()) {
      profile.setCities(localizationService.findAllCitiesByCountryCodeAndLangWithLocals(
          profile.getCountryCode(), langCode));
    }
    profile.setAvatarUrl(blobDataService.getFileUrl(user.getProfile().getAvatarId()));

    if (LOG.isDebugEnabled()) {
      LOG.debug("Profile for user with id: {}, found and return", user.getId());
    }

    return ResponseEntity.ok(profile);
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestBody Profile profile) {
    LOG.info("Calling method 'save' of UserProfileRestController");

    User user = authService.getCurrentUserOrException();

    if (isNullOrEmpty(profile.getFirstName()) || isNullOrEmpty(profile.getLang())) {
      LOG.error("Calling method 'save' of UserProfileRestController: Illegal argument exception: " +
          "parameters 'firstName', 'lang', must be filled");
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Calling method 'save' of UserProfileRestController with profile:{}",
            profile);
      }
    }

    Optional<Language> language = localizationService
        .findSupportedLangByCode(profile.getLang().toUpperCase());
    if (!language.isPresent()) {
      LOG.error("Not found language {} or not supported", profile.getLang());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    Optional<User> userOptional = userService.findByIdWithFullProfile(user.getId());
    if (!userOptional.isPresent()) {
      LOG.error("User with id:{} not fount", user.getEmail());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    user = userOptional.get();

    Profile oldProfile = user.getProfile();
    oldProfile.setFirstName(profile.getFirstName());
    oldProfile.setLastName(profile.getLastName());
    oldProfile.setFullName(profile.buildFullName());
    oldProfile.setGender(profile.getGender());
    oldProfile.setAboutMe(profile.getAboutMe());
    if (profile.getBirthdayStr() != null && !profile.getBirthdayStr().isEmpty()) {
      try {
        oldProfile.setBirthday(DateUtils.getShortDateFormat().parse(profile.getBirthdayStr()));
      } catch (ParseException ex) {
        //just log and ignore it
        LOG.warn("Cannot parse a birthday:{}", profile.getBirthdayStr());
      }
    }
    if (profile.getCountryCode() != null && !profile.getCountryCode().isEmpty()) {
      Optional<Country> countryOptional = localizationService
          .findCountryByCode(profile.getCountryCode());
      if (countryOptional.isPresent()) {
        oldProfile.setCountry(countryOptional.get());
      } else {
        LOG.warn("Not found a country with id:{}", profile.getCountryCode());
      }
    } else {
      oldProfile.setCountry(null);
    }
    if (profile.getCityId() != null && oldProfile.getCountry() != null) {
      Optional<City> cityOptional = localizationService
          .findCityByIdAndCountryCode(profile.getCityId(), oldProfile.getCountry().getCode());
      if (cityOptional.isPresent()) {
        oldProfile.setCity(cityOptional.get());
      } else {
        LOG.warn("Not found a city with id:{} and countryCode:{}",
            profile.getCityId(), oldProfile.getCountry().getCode());
      }
    } else {
      oldProfile.setCity(null);
    }
    oldProfile.setLanguage(language.get());
    try {
      userService.saveProfile(user, oldProfile, profile.getEmails(), profile.getPhones());
      if (LOG.isDebugEnabled()) {
        LOG.debug("Profile for user with id: {} saved successfully", user.getId());
      }

      return ResponseEntity.ok().build();
    } catch (Exception ex) {
      LOG.error("Error saving a profile", ex);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }
  }
}
