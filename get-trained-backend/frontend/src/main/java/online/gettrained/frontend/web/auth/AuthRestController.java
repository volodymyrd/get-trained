package online.gettrained.frontend.web.auth;

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
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.services.notif.NotificationService;
import online.gettrained.backend.services.user.UserService;
import online.gettrained.backend.utils.CommonUtils;
import online.gettrained.backend.utils.SecurityUtils;
import online.gettrained.frontend.services.HelperService;
import online.gettrained.frontend.web.HttpStatusCodes;
import online.gettrained.frontend.web.Utils;
import online.gettrained.frontend.web.auth.dto.UserRegistrationDto;
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
  public ResponseEntity<?> checkEmailExist(String email) {
    return ResponseEntity.ok(userService.countByEmail(email) > 0);
  }

  @PostMapping("/passwordrestore")
  public ResponseEntity<?> passwordRestore(@RequestParam("email") String email,
      @RequestParam("lang") String lang,
      HttpServletRequest request) {

    LOG.info("Requested the password restore for email:{} for lang:{}", email, lang);

    long number = notificationService
        .countQueuesByEventCodeAndChannelCodeAndStatusInAndExpireDateGreaterThanAndAddressTo(
            NotificationEvent.Code.RESTORE_PASSWORD,
            NotificationChannel.Code.EMAIL,
            CommonUtils.immutableListOf(ENotificationStatus.NEW,
                ENotificationStatus.PROCESSING,
                ENotificationStatus.ERROR),
            new Date(), email
        );
    if (number > 0) {
      LOG.info("The request is already processing");
      return ResponseEntity.ok().build();
    }

    Language language = Utils.getLanguage(lang, request, localizationService);

    Optional<User> userOptional = userService.findOneByEmailWithProfile(email);
    if (!userOptional.isPresent()) {
      return ResponseEntity.notFound().build();
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
              put(MergeTag.EMAIL.getValue(), userOptional.get().getEmail());
              put(MergeTag.FIRST_NAME.getValue(), userOptional.get().getProfile().getFirstName());
              put(MergeTag.AUTO_GENERATED_PASSWORD.getValue(), newPassword);
              put(MergeTag.HOME_URL.getValue(),
                  Utils.getSchemeAndHostAndPort(request));
            }
          }, new MessageTemplateLocal(
              "Restore password",
              "Please fill out a message template for the 'RESTORE_PASSWORD' event"));
    } catch (Exception ex) {
      LOG.error("Error restoring the password ", ex);
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok().build();
  }

  @GetMapping("/regconfirmation")
  public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token,
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
  public ResponseEntity<?> signUp(@RequestBody UserRegistrationDto dto,
      HttpServletRequest request) {

    Language language = Utils.getLanguage(dto.getLang(), request, localizationService);

    if (userService.countByEmail(dto.getEmail()) > 0) {
      return ResponseEntity.status(HttpStatusCodes.AUTH_EMAIL_ALREADY_EXIST.getCode()).build();
    }

    User user = userService.createDefaultUser(dto.toUser(), dto.toProfile(language));

    if (user == null) {
      return ResponseEntity.badRequest().build();
    }

    sendNotificationToConfirmRegistration(user, language, request);

    return ResponseEntity.ok("ok");
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
    if (lang == null || lang.isEmpty()) {
      throw new IllegalStateException("Not specified language");
    }
    Language language = Utils.getLanguage(lang, request, true, localizationService);

    if (user.getStatus() == User.EStatus.NEW) {
      LOG.error("Sign in failed because user:{} is new", user.getEmail());
      sendNotificationToConfirmRegistration(
          userService.findByIdWithProfile(user.getId()).orElseThrow(IllegalStateException::new),
          language,
          request);
      signout(request, response);
      return ResponseEntity.status(HttpStatusCodes.AUTH_NOT_FINISHED_REGISTRATION.getCode())
          .build();
    }

    user = userService.afterSuccessLoginUpdate(user, language, remoteAddress);
    if (!language.equals(user.getProfile().getLanguage())) {
      user.getProfile().setLanguage(language);
    }
    user.setLoginLang(language);
    authService.setCurrentUser(user);

    LOG.debug("Signing in for {} success", user.getEmail());

    return ResponseEntity.ok("ok");
  }

  @PostMapping("/check")
  public ResponseEntity<?> check() {
    return ResponseEntity.ok("ok");
  }

  @PostMapping("/signout")
  public ResponseEntity<?> signOut(HttpServletRequest request, HttpServletResponse response) {
    signout(request, response);

    return ResponseEntity.ok("ok");
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
