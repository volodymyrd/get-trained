package online.gettrained.frontend.web.activities;

import online.gettrained.backend.domain.activities.TrainerConnectionSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Calendar and schedules.
 */
@RestController
@RequestMapping("/calendar")
public class CalendarRestController {

  private static final Logger LOG = LoggerFactory.getLogger(CalendarRestController.class);

  @PostMapping("/test")
  public ResponseEntity<?> test(@RequestBody TrainerConnectionSchedule schedule ) {

    LOG.info("Calling method 'getAllActivities' of CalendarRestController {}", schedule);

    return ResponseEntity.ok("test");
  }
}
