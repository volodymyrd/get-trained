package online.gettrained.frontend.web.activities;

import static online.gettrained.backend.exceptions.ErrorCode.SOMETHING_WENT_WRONG;
import static online.gettrained.backend.messages.TextCode.ACTIVITY_YOU_BECAME_A_TRAINER;

import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.exceptions.ApplicationException;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.exceptions.NotFoundException;
import online.gettrained.backend.services.activities.ActivityService;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.frontend.web.Utils;
import online.gettrained.frontend.web.dto.TextInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main controller for activity functionality.
 */
@RestController
@RequestMapping("/fe/activity")
public class ActivityRestController {

  private static final Logger LOG = LoggerFactory.getLogger(ActivityRestController.class);

  private final AuthService authService;
  private final LocalizationService localizationService;
  private final ActivityService activityService;

  public ActivityRestController(
      AuthService authService,
      LocalizationService localizationService,
      ActivityService activityService) {
    this.authService = authService;
    this.localizationService = localizationService;
    this.activityService = activityService;
  }

  @PostMapping("/getAll")
  public ResponseEntity<?> getAllActivities(@RequestBody FrontendActivityConstraint constraint) {

    LOG.info("Calling method 'getAllActivities' of ActivityRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'getAllActivities' of ActivityRestController with constraint:{}",
          constraint);
    }

    User user = authService.getCurrentUserOrException();

    try {
      return ResponseEntity.ok(activityService.findAllActivities(user, constraint));
    } catch (Exception e) {
      LOG.error("Error getting all activities", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    }
  }

  @PostMapping("/trainer/getAll")
  public ResponseEntity<?> getAllTrainers(@RequestBody FrontendActivityConstraint constraint) {

    LOG.info("Calling method 'getAllTrainers' of ActivityRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'getAllTrainers' of ActivityRestController with constraint:{}",
          constraint);
    }

    User user = authService.getCurrentUserOrException();

    try {
      return ResponseEntity.ok(activityService.findAllTrainers(user, constraint));
    } catch (Exception e) {
      LOG.error("Error getting all trainers", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    }
  }

  @PostMapping("/fitness/trainer/add")
  public ResponseEntity<?> addFitnessTrainer() {
    LOG.info("Calling method 'addFitnessTrainer' of ActivityRestController");

    User user = authService.getCurrentUserOrException();

    try {
      activityService.addFitnessTrainer(user);
      return ResponseEntity
          .ok(new TextInfoDto(ACTIVITY_YOU_BECAME_A_TRAINER,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(
                  ACTIVITY_YOU_BECAME_A_TRAINER.toString(),
                  Utils.getLanguage(user),
                  "You are a trainer now."
              )));
    } catch (NotFoundException e) {
      LOG.error("Not found exception: {}", e.getMessage());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    } catch (ApplicationException e) {
      LOG.error("Error adding a new trainer: {}", e.getMessage());
      return ResponseEntity.badRequest().body(e.getErrorInfo());
    } catch (Exception e) {
      LOG.error("Error adding a new trainer", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    }
  }

  @PostMapping("/fitness/request/trainee")
  public ResponseEntity<?> requestFitnessTrainee(@RequestParam("email") String email) {
    LOG.info("Calling method 'requestFitnessTrainee' of ActivityRestController");
    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'requestFitnessTrainee' of ActivityRestController with email:{}",
          email);
    }

    User user = authService.getCurrentUserOrException();

    try {
      activityService.addFitnessTrainer(user);
      return ResponseEntity
          .ok(new TextInfoDto(ACTIVITY_YOU_BECAME_A_TRAINER,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(
                  ACTIVITY_YOU_BECAME_A_TRAINER.toString(),
                  Utils.getLanguage(user),
                  "You are a trainer now."
              )));
    } catch (NotFoundException e) {
      LOG.error("Not found exception: {}", e.getMessage());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    } catch (ApplicationException e) {
      LOG.error("Error adding a new trainer: {}", e.getMessage());
      return ResponseEntity.badRequest().body(e.getErrorInfo());
    } catch (Exception e) {
      LOG.error("Error adding a new trainer", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    }
  }

  @PostMapping("/trainer/add")
  public ResponseEntity<?> addTrainer(@RequestParam("activityId") Long activityId) {

    LOG.info("Calling method 'addTrainer' of ActivityRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'addTrainer' of ActivityRestController with activityId:{}",
          activityId);
    }

    User user = authService.getCurrentUserOrException();

    try {
      activityService.addTrainer(user, activityId);
      return ResponseEntity
          .ok(new TextInfoDto(ACTIVITY_YOU_BECAME_A_TRAINER,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(
                  ACTIVITY_YOU_BECAME_A_TRAINER.toString(),
                  Utils.getLanguage(user),
                  "You are a trainer now."
              )));
    } catch (NotFoundException e) {
      LOG.error("Not found exception: {}", e.getMessage());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    } catch (ApplicationException e) {
      LOG.error("Error adding a new trainer: {}", e.getMessage());
      return ResponseEntity.badRequest().body(e.getErrorInfo());
    } catch (Exception e) {
      LOG.error("Error adding a new trainer", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    }
  }
}
