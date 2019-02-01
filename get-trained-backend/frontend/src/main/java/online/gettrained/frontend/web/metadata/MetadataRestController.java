package online.gettrained.frontend.web.metadata;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.localization.Localization;
import online.gettrained.backend.domain.localization.Settings;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.props.ApplicationCommonProperties;
import online.gettrained.backend.services.blob.BlobDataService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.utils.SecurityUtils;
import online.gettrained.frontend.web.Utils;
import online.gettrained.frontend.web.dto.UserTheme;
import online.gettrained.frontend.web.metadata.dto.MetadataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/metadata")
public class MetadataRestController {

  private static final Logger LOG = LoggerFactory.getLogger(MetadataRestController.class);

  private ApplicationCommonProperties applicationCommonProperties;
  private final LocalizationService localizationService;
  private final BlobDataService blobDataService;

  public MetadataRestController(
      ApplicationCommonProperties applicationCommonProperties,
      LocalizationService localizationService,
      BlobDataService blobDataService) {
    this.applicationCommonProperties = applicationCommonProperties;
    this.localizationService = localizationService;
    this.blobDataService = blobDataService;
  }

  @PostMapping("/lang")
  public ResponseEntity<?> getLang(HttpServletRequest request) {

    LOG.info("Calling method 'getLang' of MetadataRestController");

    Language language = Utils.getLanguage(null, request, localizationService);

    if (language != null && language.getCode() != null && !language.getCode().isEmpty()) {
      return ResponseEntity.ok("{\"lang\":\"" + language.getCode() + "\"}");
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/get")
  public ResponseEntity<MetadataDto> getLocalization(
      HttpServletRequest request,
      @RequestParam(name = "lang", required = false) String lang,
      @RequestParam(name = "module") String module) {

    LOG.info("Calling method 'getLocalization' of MetadataRestController");

    User user = SecurityUtils.getCurrentUser();

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'getLocalization' of MetadataRestController "
              + "by user:{}, with lang:{} and module:{}",
          user != null ? user.getId() : "anonymous", lang, module);
    }

    Language language = Utils.getLanguage(lang, request, localizationService);
    if (user != null) {
      if (user.getProfile() != null && user.getProfile().getAvatarId() != null) {
        user.getProfile().setAvatarUrl(blobDataService.getFileUrl(user.getProfile().getAvatarId()));
      }
    }

    return ResponseEntity.ok(
        new MetadataDto(language.getCode(),
            ELocalModule.valueOf(module),
            localizationService.findAllSupportedLang(),
            localizationService.findLocalByLangAndModule(language, ELocalModule.valueOf(module))
                .stream()
                .collect(Collectors.toMap(Localization::getLocalKey, Localization::getText)),
            localizationService.findAllSettingsByType(Settings.Type.FRONTEND)
                .stream()
                .collect(
                    Collectors.toMap(e -> e.getPk().getSettingKey(), Settings::getSettingValue))
        ));
  }

  @RequestMapping("languages")
  public ResponseEntity<List<Language>> getLanguages() {
    return ResponseEntity.ok(localizationService.findAllLang());
  }

  @RequestMapping("activeTheme")
  public ResponseEntity<UserTheme> getActiveTheme() {
    return ResponseEntity
        .ok(new UserTheme(applicationCommonProperties.getUserPropertyActiveTheme()));
  }
}
