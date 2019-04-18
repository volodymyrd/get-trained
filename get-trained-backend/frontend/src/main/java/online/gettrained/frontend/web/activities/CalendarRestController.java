package online.gettrained.frontend.web.activities;

import static online.gettrained.backend.exceptions.ErrorCode.SOMETHING_WENT_WRONG;

import online.gettrained.backend.domain.activities.TrainerConnectionSchedule;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.services.activities.CalendarService;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.frontend.web.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Calendar and schedules.
 */
@RestController
@RequestMapping("/calendar")
public class CalendarRestController {

  private static final Logger LOG = LoggerFactory.getLogger(CalendarRestController.class);

  private final AuthService authService;
  private final LocalizationService localizationService;
  private final CalendarService calendarService;

  public CalendarRestController(
      AuthService authService,
      LocalizationService localizationService,
      CalendarService calendarService) {
    this.authService = authService;
    this.localizationService = localizationService;
    this.calendarService = calendarService;
  }

  @PostMapping("/schedule/get")
  public ResponseEntity<?> getSchedule(@RequestParam("connectionId") long connectionId) {
    LOG.info("Calling method 'getSchedule' of CalendarRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'getSchedule' of CalendarRestController with connectionId:{}",
          connectionId);
    }

    User user = authService.getCurrentUserOrException();

    try {
      return ResponseEntity.ok(calendarService.getSchedule(user, connectionId));
    } catch (Exception e) {
      LOG.error("Error getting a schedule", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    }
  }

  @PostMapping("/schedule/save")
  public ResponseEntity<?> saveSchedule(@RequestBody TrainerConnectionSchedule schedule) {
    LOG.info("Calling method 'saveSchedule' of CalendarRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'saveSchedule' of CalendarRestController with schedule:{}",
          schedule);
    }

    User user = authService.getCurrentUserOrException();

    try {
      return ResponseEntity.ok(calendarService.saveSchedule(user, schedule));
    } catch (Exception e) {
      LOG.error("Error saving a schedule", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    }
  }

  @PostMapping("/get")
  public ResponseEntity<?> getCalendar() {
    LOG.info("Calling method 'getCalendar' of CalendarRestController");

    User user = authService.getCurrentUserOrException();

    try {
      return ResponseEntity.ok(calendarService.getMergedSchedule(user));
    } catch (Exception e) {
      LOG.error("Error getting a calendar", e);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              SOMETHING_WENT_WRONG.toString(),
              Utils.getLanguage(user),
              "Something went wrong!")));
    }
  }
}
