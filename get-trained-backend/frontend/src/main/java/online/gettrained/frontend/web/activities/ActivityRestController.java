package online.gettrained.frontend.web.activities;

import online.gettrained.backend.services.activities.ActivityService;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.localization.LocalizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
