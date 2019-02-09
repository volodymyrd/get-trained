package online.gettrained.frontend.web.dashboard;

import java.util.Optional;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.exceptions.ErrorCode;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.dashboard.DashboardService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.frontend.web.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for dashboard.
 */
@RestController
@RequestMapping("/fe/dashboard")
public class DashboardRestController {

  private static final Logger LOG = LoggerFactory.getLogger(DashboardRestController.class);

  private final AuthService authService;
  private final LocalizationService localizationService;
  private final DashboardService dashboardService;

  public DashboardRestController(
      AuthService authService,
      LocalizationService localizationService,
      DashboardService dashboardService) {
    this.authService = authService;
    this.localizationService = localizationService;
    this.dashboardService = dashboardService;
  }

  @PostMapping(value = "/menu")
  public ResponseEntity<?> menu(@RequestParam(name = "lang") String lang) {

    LOG.info("Calling method 'menu' of DashboardRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'menu' of DashboardRestController with lang:{}", lang);
    }

    User user = authService.getCurrentUserOrException();

    Optional<Language> languageOptional = localizationService
        .findSupportedLangByCode(lang.toUpperCase());
    if (!languageOptional.isPresent()) {
      LOG.error("Not found language {} or not supported", lang.toUpperCase());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(ErrorCode.SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    return ResponseEntity.ok(dashboardService.findFullMainMenuWithChildrenAndWithLocale(
        user, languageOptional.get()));
  }
}
