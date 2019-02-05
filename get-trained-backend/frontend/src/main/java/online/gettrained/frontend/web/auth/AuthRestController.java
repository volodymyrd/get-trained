package online.gettrained.frontend.web.auth;

import static online.gettrained.backend.domain.user.User.EStatus.NEW;
import static online.gettrained.backend.exceptions.ErrorCode.EMAIL_ALREADY_EXIST;
import static online.gettrained.backend.exceptions.ErrorCode.SOMETHING_WENT_WRONG;
import static online.gettrained.backend.messages.TextCode.AUTH_INFO_NOT_FINISHED_SIGN_UP;
import static online.gettrained.backend.messages.TextCode.AUTH_INFO_PASSWORD_RESET_PROCESS;
import static online.gettrained.backend.messages.TextCode.AUTH_SUCCESS_PASSWORD_RESET;
import static online.gettrained.backend.messages.TextCode.AUTH_SUCCESS_SIGNED_IN;
import static online.gettrained.backend.messages.TextCode.AUTH_SUCCESS_SIGNED_OUT;
import static online.gettrained.backend.messages.TextCode.AUTH_SUCCESS_SIGNED_UP;
import static online.gettrained.frontend.web.Utils.getLanguage;
import static online.gettrained.frontend.web.dto.TextInfoDto.Type.I;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.notif.ENotificationStatus;
import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.domain.notif.templates.MergeTag;
import online.gettrained.backend.domain.notif.templates.MessageTemplateLocal;
import online.gettrained.backend.domain.user.CurrentUser;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.domain.user.UserToken;
import online.gettrained.backend.dto.ResponseValueJson;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.services.notif.NotificationService;
import online.gettrained.backend.services.user.UserService;
import online.gettrained.backend.utils.CommonUtils;
import online.gettrained.backend.utils.SecurityUtils;
import online.gettrained.frontend.services.HelperService;
import online.gettrained.frontend.web.Utils;
import online.gettrained.frontend.web.auth.dto.UserRegistrationDto;
import online.gettrained.frontend.web.dto.TextInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fe/auth")
public class AuthRestController {

  private static final Logger LOG = LoggerFactory.getLogger(AuthRestController.class);

  private static final int NUMBER_DAYS_CONFIRM_REGISTRATION_TOKEN_IS_EXPIRED = 2;
  private static final String CONFIRM_REGISTRATION_URL = "fe/auth/regconfirmation?token=";

  private final AbstractRememberMeServices rememberMeServices;
  private final UserService userService;
  private final LocalizationService localizationService;
  private final NotificationService notificationService;
  private final HelperService helperService;
  private final AuthService authService;

  public AuthRestController(
      AbstractRememberMeServices rememberMeServices,
      UserService userService,
      LocalizationService localizationService,
      NotificationService notificationService,
      HelperService helperService,
      AuthService authService) {
    this.rememberMeServices = rememberMeServices;
    this.userService = userService;
    this.localizationService = localizationService;
    this.notificationService = notificationService;
    this.helperService = helperService;
    this.authService = authService;
  }

  @PostMapping("/checkemailexist")
  public ResponseEntity<ResponseValueJson<Boolean>> checkEmailExist(String email) {
    return ResponseEntity.ok(new ResponseValueJson<>(userService.countByEmail(email) > 0));
  }

  @PostMapping("/passwordrestore")
  public ResponseEntity<?> passwordRestore(
      @RequestParam("email") String email,
      @RequestParam("lang") String lang,
      HttpServletRequest request) {

    LOG.info("Requested the password restore for email:{} for lang:{}", email, lang);

    Language language = getLanguage(lang, request, localizationService);

    long number = notificationService
        .countQueuesByEventCodeAndChannelCodeAndStatusInAndExpireDateGreaterThanAndAddressTo(
            NotificationEvent.Code.RESTORE_PASSWORD,
            NotificationChannel.Code.EMAIL,
            CommonUtils.immutableListOf(
                ENotificationStatus.NEW,
                ENotificationStatus.PROCESSING,
                ENotificationStatus.ERROR),
            new Date(), email
        );
    if (number > 0) {
      LOG.info("The request is already processing");
      return ResponseEntity.ok(new TextInfoDto(
          I,
          AUTH_INFO_PASSWORD_RESET_PROCESS,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              AUTH_INFO_PASSWORD_RESET_PROCESS.toString(),
              language,
              "Success reset password."
          )));
    }

    Optional<User> userOptional = userService.findOneByEmailWithProfile(email);
    if (!userOptional.isPresent()) {
      LOG.error("User with email {} not found", email);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  language,
                  "Something went wrong!")));
    }

    try {
      User user = userOptional.get();
      String newPassword = SecurityUtils.genPassword(7);
      user.setNewPassword(newPassword);
      userService.saveUser(user);
      LOG.info("The password was successfully changed for user with id:{}", user.getId());
      notificationService.preProcessQueue(NotificationEvent.Code.RESTORE_PASSWORD, language,
          new HashMap<String, String>() {
            private static final long serialVersionUID = 714767951595242878L;

            {
              put(MergeTag.EMAIL.getValue(), user.getEmail());
              put(MergeTag.FIRST_NAME.getValue(), user.getProfile().getFirstName());
              put(MergeTag.AUTO_GENERATED_PASSWORD.getValue(), newPassword);
              put(MergeTag.HOME_URL.getValue(),
                  Utils.getSchemeAndHostAndPort(request));
            }
          }, new MessageTemplateLocal(
              "Restore password",
              "Please fill out a message template for the 'RESTORE_PASSWORD' event"));

      return ResponseEntity.ok(new TextInfoDto(
          AUTH_SUCCESS_PASSWORD_RESET,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              AUTH_SUCCESS_PASSWORD_RESET.toString(),
              language,
              "Success reset password."
          )));
    } catch (Exception ex) {
      LOG.error("Error restoring the password ", ex);
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  language,
                  "Something went wrong!")));
    }
  }

  @GetMapping("/regconfirmation")
  public ResponseEntity<?> confirmRegistration(
      @RequestParam("token") String token,
      HttpServletRequest request) {

    if (token == null || token.isEmpty()) {
      return ResponseEntity.badRequest().body("Error");
    }

    Optional<User> userOptional = userService.findUserByUserToken(token, new Date());
    if (!userOptional.isPresent()) {
      return ResponseEntity.badRequest().body("Error");
    }

    userOptional = userService.findByIdWithProfileWithLang(userOptional.get().getId());
    User user = userOptional.orElseThrow(IllegalStateException::new);

    if (user.getStatus() == User.EStatus.REGISTERED) {
      return ResponseEntity.ok("Success");
    }
    user.setStatus(User.EStatus.REGISTERED);
    try {
      userService.saveUserAndDeleteToken(user, token);
      LOG.info("User with email:{} confirmed the registration successfully", user.getEmail());
      notificationService.preProcessQueue(NotificationEvent.Code.SUCCESS_REGISTRATION,
          user.getProfile().getLanguage(),
          new HashMap<String, String>() {
            private static final long serialVersionUID = 6397612460475863838L;

            {
              put(MergeTag.EMAIL.getValue(), user.getEmail());
              put(MergeTag.FIRST_NAME.getValue(), user.getProfile().getFirstName());
              put(MergeTag.HOME_URL.getValue(),
                  Utils.getSchemeAndHostAndPort(request));
            }
          }, new MessageTemplateLocal(
              "Success registration",
              "Please fill out a message template for the 'SUCCESS_REGISTRATION' event"));
    } catch (Exception ex) {
      LOG.error("Error the registration confirming", ex);
      return ResponseEntity.badRequest().body("Error");
    }
    return ResponseEntity.ok("Thank you for registration!");
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(
      @RequestBody UserRegistrationDto dto,
      HttpServletRequest request) {

    Language language = getLanguage(dto.getLang(), request, localizationService);

    if (userService.countByEmail(dto.getEmail()) > 0) {
      LOG.error("User with email {} already exists", dto.getEmail());
      return ResponseEntity.badRequest().body(
          new ErrorInfoDto(
              EMAIL_ALREADY_EXIST,
              localizationService
                  .getLocalTextByKeyAndLangOrUseDefault(EMAIL_ALREADY_EXIST.toString(),
                      language,
                      "User with such email already exists!")));
    }

    User user = userService.createDefaultUser(dto.toUser(), dto.toProfile(language));

    if (user == null) {
      LOG.error("User creating a new user");
      return ResponseEntity.badRequest().body(new ErrorInfoDto(
          SOMETHING_WENT_WRONG,
          localizationService
              .getLocalTextByKeyAndLangOrUseDefault(SOMETHING_WENT_WRONG.toString(),
                  language,
                  "Something went wrong!")));
    }

    sendNotificationToConfirmRegistration(user, language, request);

    return ResponseEntity.ok(new TextInfoDto(
        AUTH_SUCCESS_SIGNED_UP,
        localizationService.getLocalTextByKeyAndLangOrUseDefault(
            AUTH_SUCCESS_SIGNED_UP.toString(),
            language,
            "Success registration."
        )));
  }

  private void sendNotificationToConfirmRegistration(User user, Language language,
      HttpServletRequest request) {
    String token = userService.generateUserToken(user, UserToken.Type.CONFIRM_REGISTRATION_TOKEN,
        CommonUtils.convertLocalDateTimeToDate(
            LocalDateTime.now().plusDays(NUMBER_DAYS_CONFIRM_REGISTRATION_TOKEN_IS_EXPIRED)));
    if (token != null) {
      notificationService.preProcessQueue(NotificationEvent.Code.CONFIRM_REGISTRATION, language,
          new HashMap<String, String>() {
            private static final long serialVersionUID = 1663802005797511824L;

            {
              put(MergeTag.EMAIL.getValue(), user.getEmail());
              put(MergeTag.FIRST_NAME.getValue(), user.getProfile().getFirstName());
              put(MergeTag.URL.getValue(),
                  Utils.getSchemeAndHostAndPort(request) + CONFIRM_REGISTRATION_URL + token);
              put(MergeTag.HOME_URL.getValue(),
                  Utils.getSchemeAndHostAndPort(request));
            }
          }, new MessageTemplateLocal(
              "Confirm registration",
              "Please fill out a message template for the 'CONFIRM_REGISTRATION' event"));
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signIn(HttpServletRequest request, HttpServletResponse response) {
    String remoteAddress = SecurityUtils.getIpOfRemoteHost(request);
    LOG.info("Signing in from ip:{}", remoteAddress);

    User user = authService.getCurrentUserOrException();
    LOG.debug("Signing in....: {}", user.getEmail());

    String lang = request.getHeader("lang");
    Language language = getLanguage(lang, request, true, localizationService);

    if (user.getStatus() == NEW) {
      LOG.error("Sign in failed because user:{} has a NEW status", user.getEmail());
      sendNotificationToConfirmRegistration(
          userService.findByIdWithProfile(user.getId()).orElseThrow(IllegalStateException::new),
          language,
          request);
      signout(request, response);
      return ResponseEntity.badRequest().body(new TextInfoDto(
          I,
          AUTH_INFO_NOT_FINISHED_SIGN_UP,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              AUTH_INFO_NOT_FINISHED_SIGN_UP.toString(),
              language,
              "You did not finish the registration."
          )));
    }

    user = userService.afterSuccessLoginUpdate(user, language, remoteAddress);
    if (!language.equals(user.getProfile().getLanguage())) {
      user.getProfile().setLanguage(language);
    }
    user.setLoginLang(language);
    authService.setCurrentUser(user);

    LOG.debug("Signed in for {} successfully", user.getEmail());

    return ResponseEntity.ok(new TextInfoDto(
        AUTH_SUCCESS_SIGNED_IN,
        localizationService.getLocalTextByKeyAndLangOrUseDefault(
            AUTH_SUCCESS_SIGNED_IN.toString(),
            language,
            "Signed in successfully!"
        )));
  }

  @PostMapping("/check")
  public ResponseEntity<?> check() {
    return ResponseEntity.ok(new ResponseValueJson<>(true));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> signOut(HttpServletRequest request, HttpServletResponse response) {
    User user = authService.getCurrentUserOrException();

    signout(request, response);

    return ResponseEntity.ok(new TextInfoDto(
        AUTH_SUCCESS_SIGNED_OUT,
        localizationService.getLocalTextByKeyAndLangOrUseDefault(
            AUTH_SUCCESS_SIGNED_OUT.toString(),
            user.getLoginLang(),
            "Signed out successfully!"
        )));
  }

  private void signout(HttpServletRequest request, HttpServletResponse response) {
    LOG.info("Sign out...");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
      rememberMeServices.logout(request, response, auth);

      String username = ((CurrentUser) auth.getPrincipal()).getUsername();
      helperService.userCleanUp(username);
      LOG.debug("Sign out for {} success", username);
    }
  }
}
