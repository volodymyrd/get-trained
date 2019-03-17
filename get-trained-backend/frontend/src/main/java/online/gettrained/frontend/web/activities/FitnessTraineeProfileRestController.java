package online.gettrained.frontend.web.activities;

import static online.gettrained.backend.exceptions.ErrorCode.SOMETHING_WENT_WRONG;
import static online.gettrained.backend.messages.TextCode.ENUM_PROFILE_GENDER_PREFIX;
import static online.gettrained.frontend.web.Utils.enumToMapWithLocalSortedByLocalText;
import static online.gettrained.frontend.web.Utils.getLanguage;

import online.gettrained.backend.domain.activities.FitnessTraineeProfile;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.exceptions.NotFoundException;
import online.gettrained.backend.services.activities.FitnessTraineeProfileService;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.localization.LocalizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for CRUD on {@link FitnessTraineeProfile}.
 */
@RestController
@RequestMapping("/fe/activity/trainee/profile")
public class FitnessTraineeProfileRestController {

  private static final Logger LOG = LoggerFactory
      .getLogger(FitnessTraineeProfileRestController.class);

  private final AuthService authService;
  private final LocalizationService localizationService;
  private final FitnessTraineeProfileService fitnessTraineeProfileService;

  public FitnessTraineeProfileRestController(
      AuthService authService,
      LocalizationService localizationService,
      FitnessTraineeProfileService fitnessTraineeProfileService) {
    this.authService = authService;
    this.localizationService = localizationService;
    this.fitnessTraineeProfileService = fitnessTraineeProfileService;
  }

  @PostMapping("/genders")
  public ResponseEntity<?> getGenders() {
    LOG.debug("Calling method 'getGenders' of FitnessTraineeProfileRestController");

    User user = authService.getCurrentUserOrException();

    return ResponseEntity.ok(enumToMapWithLocalSortedByLocalText(
        Profile.Gender.class,
        localizationService,
        user.getLoginLang(),
        ENUM_PROFILE_GENDER_PREFIX
    ));
  }

  @PostMapping("/get")
  public ResponseEntity<?> getTraineeProfile(@RequestParam("traineeUserId") long traineeUserId) {
    LOG.info("Calling method 'getTraineeProfile' of FitnessTraineeProfileRestController");

    LOG.debug("Calling method 'getTraineeProfile' of FitnessTraineeProfileRestController "
        + "with traineeUserId:{}", traineeUserId);

    User user = authService.getCurrentUserOrException();

    try {
      return ResponseEntity.ok(fitnessTraineeProfileService.getProfile(user, traineeUserId));
    } catch (NotFoundException e) {
      LOG.error("Not found exception: {}", e.getMessage());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(), getLanguage(user), "Something went wrong!")));
    } catch (Exception e) {
      LOG.error("Error getting a trainee profile", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(), getLanguage(user), "Something went wrong!")));
    }
  }

  @PostMapping("/update")
  public ResponseEntity<?> updateTraineeProfile(@RequestBody Profile traineeProfile) {
    LOG.info("Calling method 'updateTraineeProfile' of FitnessTraineeProfileRestController");

    LOG.debug("Calling method 'updateTraineeProfile' of FitnessTraineeProfileRestController "
        + "with traineeProfile:{}", traineeProfile);

    User user = authService.getCurrentUserOrException();

    try {
      return ResponseEntity.ok(fitnessTraineeProfileService.updateProfile(user, traineeProfile));
    } catch (NotFoundException e) {
      LOG.error("Not found exception: {}", e.getMessage());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(), getLanguage(user), "Something went wrong!")));
    } catch (Exception e) {
      LOG.error("Error saving a trainee profile", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(), getLanguage(user), "Something went wrong!")));
    }
  }

  @PostMapping("/fitness/save")
  public ResponseEntity<?> saveFitnessTraineeProfile(
      @RequestBody FitnessTraineeProfile traineeProfile) {
    LOG.info("Calling method 'saveFitnessTraineeProfile' of FitnessTraineeProfileRestController");

    LOG.debug("Calling method 'saveFitnessTraineeProfile' of FitnessTraineeProfileRestController "
        + "with traineeProfile:{}", traineeProfile);

    User user = authService.getCurrentUserOrException();

    try {
      return ResponseEntity.ok(
          fitnessTraineeProfileService.saveTraineeProfile(user, traineeProfile));
    } catch (NotFoundException e) {
      LOG.error("Not found exception: {}", e.getMessage());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(), getLanguage(user), "Something went wrong!")));
    } catch (Exception e) {
      LOG.error("Error saving a trainee fitness profile", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(), getLanguage(user), "Something went wrong!")));
    }
  }
}
