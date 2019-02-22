package online.gettrained.frontend.web.localization;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import online.gettrained.backend.constraints.frontend.localization.FrontendLocalConstraint;
import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.localization.Localization;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.domain.user.UserAction.Id;
import online.gettrained.backend.dto.localization.LocalDto;
import online.gettrained.backend.exceptions.ErrorCode;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.messages.TextCode;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.utils.FileUtils;
import online.gettrained.frontend.web.Utils;
import online.gettrained.backend.dto.TextInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for CRUD operation with localization.
 */
@RestController
@RequestMapping("/fe/localization")
public class LocalizationRestController {

  private static final Logger LOG = LoggerFactory.getLogger(LocalizationRestController.class);

  private final AuthService authService;
  private final LocalizationService localizationService;

  public LocalizationRestController(
      AuthService authService,
      LocalizationService localizationService) {
    this.authService = authService;
    this.localizationService = localizationService;
  }

  @PostMapping("/modules")
  public ResponseEntity<?> getModules() {
    return ResponseEntity.ok(ELocalModule.values());
  }

  @PostMapping("/getAll")
  public ResponseEntity<?> getAll(@RequestBody FrontendLocalConstraint constraint) {

    LOG.info("Calling method 'getAll' of LocalizationRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'getAll' of LocalizationRestController with constraint:{}",
          constraint);
    }

    User user = authService.getCurrentUserOrException();

    if (constraint.getPageable() == null) {
      LOG.error("Parameter 'pageable' must be filled.");
      return ResponseEntity.badRequest().body(new ErrorInfoDto(ErrorCode.SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    List<Id> allowedActions = Arrays.asList(Id.LOCALS_R, Id.LOCALS_RW);
    if (!authService.isAllowedForUser(user.getId(), allowedActions)) {
      LOG.error("User with id:{} doesn't have {} authority(ies)", user.getId(), allowedActions);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ErrorInfoDto(ErrorCode.NO_AUTHORITY,
              localizationService
                  .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.NO_AUTHORITY.toString(),
                      Utils.getLanguage(user),
                      "No authority!")));
    }

    return ResponseEntity.ok(localizationService.findAllLocalizationsByConstraint(constraint));
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestBody Localization localization) {

    LOG.info("Calling method 'save' of LocalizationRestController");

    User user = authService.getCurrentUserOrException();

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'save' of LocalizationRestController with localization:{}",
          localization);
    }

    if (localization.getLangCode() == null || localization.getLangCode().isEmpty()
        || localization.getLocalKey() == null || localization.getLocalKey().isEmpty()
        || localization.getText() == null || localization.getText().isEmpty()
        || localization.getModule() == null) {
      LOG.error("Obligatory parameters must be set");
      return ResponseEntity.badRequest().body(new ErrorInfoDto(ErrorCode.SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }

    if (!authService.isAllowedForUser(user.getId(), Id.LOCALS_RW)) {
      LOG.error("User with id:{} doesn't have {} authority", user.getId(), Id.LOCALS_RW);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ErrorInfoDto(ErrorCode.NO_AUTHORITY,
              localizationService
                  .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.NO_AUTHORITY.toString(),
                      Utils.getLanguage(user),
                      "No authority!")));
    }

    try {
      localization = save(user, localization);
      LOG.error("The localization for key:{}, lang:{}, module:{} saved successfully",
          localization.getLocalKey(), localization.getLang().getCode(), localization.getModule());
      return ResponseEntity
          .ok(new TextInfoDto(TextCode.LOCAL_LOCALIZATION_SAVED_SUCCESSFULLY,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(
                  TextCode.LOCAL_LOCALIZATION_SAVED_SUCCESSFULLY.toString(),
                  Utils.getLanguage(user),
                  "The localization saved successfully."
              )));
    } catch (NotFoundLanguage ex) {
      LOG.error("Not found language with code: {}", localization.getLangCode());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(ErrorCode.SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    } catch (Exception ex) {
      LOG.error("Error {} saving the localization for key:{}, lang:{}, module:{}", ex.getMessage(),
          localization.getLocalKey(), localization.getLangCode(), localization.getModule());
      return ResponseEntity.badRequest().body(new ErrorInfoDto(ErrorCode.SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }
  }

  private class NotFoundLanguage extends Exception {

    private static final long serialVersionUID = 1582215932605724367L;
  }

  private Localization save(User user, Localization localization) throws NotFoundLanguage {
    Optional<Language> languageOptional = localizationService
        .findLangByCode(localization.getLangCode());
    if (!languageOptional.isPresent()) {
      throw new NotFoundLanguage();
    }

    Optional<Localization> localizationOptional = localizationService.findLocalByKeyAndLang(
        localization.getLocalKey(), localization.getLangCode());

    if (localizationOptional.isPresent()) {
      localizationOptional.get().setModule(localization.getModule());
      localizationOptional.get().setText(localization.getText());
      localization = localizationOptional.get();
    } else {
      localization.setLang(languageOptional.get());
    }
    localization.setUserLastChanged(user);
    return localizationService.save(localization);
  }

  @PostMapping("/delete")
  public ResponseEntity<?> delete(@RequestParam("localIds") Set<Long> localIds) {

    LOG.info("Calling method 'delete' of LocalizationRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'delete' of LocalizationRestController with localIds:{}", localIds);
    }

    User user = authService.getCurrentUserOrException();

    if (!authService.isAllowedForUser(user.getId(), Id.LOCALS_RW)) {
      LOG.error("User with id:{} doesn't have {} authority", user.getId(), Id.LOCALS_RW);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ErrorInfoDto(ErrorCode.NO_AUTHORITY,
              localizationService
                  .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.NO_AUTHORITY.toString(),
                      Utils.getLanguage(user),
                      "No authority!")));
    }

    try {
      localizationService.delete(localIds);
      LOG.error("The localization for localIds:{} deleted successfully", localIds);
      return ResponseEntity
          .ok(new TextInfoDto(TextCode.LOCAL_LOCALIZATION_DELETED_SUCCESSFULLY,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(
                  TextCode.LOCAL_LOCALIZATION_DELETED_SUCCESSFULLY.toString(),
                  Utils.getLanguage(user),
                  "The localizations deleted successfully."
              )));
    } catch (Exception ex) {
      LOG.error("Error {} deleting the localization for localIds:{}", ex.getMessage(), localIds);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(ErrorCode.SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }
  }

  @GetMapping("/export")
  public ResponseEntity<?> export(
      @RequestParam(value = "delimiter", required = false) String delimiter) {

    LOG.info("Calling method 'export' of LocalizationRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'export' of LocalizationRestController with delimiter:{}",
          delimiter);
    }

    User user = authService.getCurrentUserOrException();

    if (!authService.isAllowedForUser(user.getId(), Id.LOCALS_RW)) {
      LOG.error("User with id:{} doesn't have {} authority(ies)", user.getId(), Id.LOCALS_RW);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ErrorInfoDto(ErrorCode.NO_AUTHORITY,
              localizationService
                  .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.NO_AUTHORITY.toString(),
                      Utils.getLanguage(user),
                      "No authority!")));
    }

    if (delimiter == null || delimiter.isEmpty()) {
      delimiter = "\t";
    }

    HttpHeaders headers = new HttpHeaders();
    headers.set(CONTENT_DISPOSITION, "attachment;filename=berize_local.csv");
    headers.set(CONTENT_TYPE, "application/csv");

    return ResponseEntity
        .ok()
        .headers(headers)
        .body(localizationService.export().toCSV(delimiter));
  }

  @PostMapping("/import")
  public ResponseEntity<?> importCsv(
      @RequestParam(value = "file") MultipartFile multipartFile,
      @RequestParam(value = "delimiter", required = false) String delimiter) {

    LOG.info("Calling method 'importCsv' of LocalizationRestController");

    if (LOG.isDebugEnabled()) {
      LOG.debug("Calling method 'importCsv' of LocalizationRestController with delimiter:{}",
          delimiter);
    }

    if (delimiter == null || delimiter.isEmpty()) {
      delimiter = "\t";
    }

    User user = authService.getCurrentUserOrException();

    if (!authService.isAllowedForUser(user.getId(), Id.LOCALS_RW)) {
      LOG.error("User with id:{} doesn't have {} authority(ies)", user.getId(), Id.LOCALS_RW);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ErrorInfoDto(ErrorCode.NO_AUTHORITY,
              localizationService
                  .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.NO_AUTHORITY.toString(),
                      Utils.getLanguage(user),
                      "No authority!")));
    }

    List<String> data;
    try {
      data = FileUtils.bytesToStringList(multipartFile.getBytes());
      for (LocalDto l : LocalDto.fromCSV(data, delimiter)) {
        for (Map.Entry<String, String> e : l.getLocalMap().entrySet()) {
          Localization localization = new Localization();
          localization.setModule(l.getModule());
          localization.setLocalKey(l.getLocalKey());
          localization.setLangCode(e.getKey());
          localization.setText(e.getValue());
          try {
            save(user, localization);
          } catch (NotFoundLanguage ex) {
            LOG.error("Not found language with code: {}", localization.getLangCode());
          } catch (Exception ex) {
            LOG.error("Error {} saving the localization for key:{}, lang:{}, module:{}",
                ex.getMessage(),
                localization.getLocalKey(), localization.getLangCode(), localization.getModule());
          }
        }
      }
      LOG.info("File with locals uploaded successfully");
      return ResponseEntity
          .ok(new TextInfoDto(TextCode.LOCAL_LOCALIZATION_SAVED_SUCCESSFULLY,
              localizationService.getLocalTextByKeyAndLangOrUseDefault(
                  TextCode.LOCAL_LOCALIZATION_SAVED_SUCCESSFULLY.toString(),
                  Utils.getLanguage(user),
                  "The localization saved successfully."
              )));
    } catch (IOException e) {
      LOG.error("Error reading parameters from the csv file");
      return ResponseEntity.badRequest().body(new ErrorInfoDto(ErrorCode.SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(ErrorCode.SOMETHING_WENT_WRONG.toString(),
                  Utils.getLanguage(user),
                  "Something went wrong!")));
    }
  }
}
