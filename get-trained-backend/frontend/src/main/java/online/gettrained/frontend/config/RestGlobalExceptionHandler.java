package online.gettrained.frontend.config;

import javax.servlet.http.HttpServletRequest;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.exceptions.ErrorCode;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.props.ApplicationCommonProperties;
import online.gettrained.backend.services.auth.AuthService;
import online.gettrained.backend.services.localization.LocalizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(RestGlobalExceptionHandler.class);

  private final ApplicationCommonProperties applicationCommonProperties;
  private final LocalizationService localizationService;
  private final AuthService authService;

  public RestGlobalExceptionHandler(
      ApplicationCommonProperties applicationCommonProperties,
      LocalizationService localizationService,
      AuthService authService) {
    this.applicationCommonProperties = applicationCommonProperties;
    this.localizationService = localizationService;
    this.authService = authService;
  }

  @ExceptionHandler(MultipartException.class)
  @ResponseBody
  ResponseEntity<?> handleControllerMultipartException(HttpServletRequest request, Throwable ex) {

    LOG.error("Error:", ex);

    HttpStatus status = getStatus(request);

    User user = authService.getCurrentUser();

    LOG.error("Attachment size exceeds the allowable limit {}",
        applicationCommonProperties.getMultipartMaxFileSize());

    return ResponseEntity.status(status)
        .body(new ErrorInfoDto(
            ErrorCode.FILE_SIZE_LIMIT_EXCEEDED,
            localizationService.getLocalTextByKeyAndLangOrUseDefault(
                ErrorCode.FILE_SIZE_LIMIT_EXCEEDED.toString(),
                user != null ? user.getLoginLang() : localizationService.findLangByCode("EN")
                    .orElseThrow(IllegalStateException::new),
                "A file size exceeded the max allowed size")));
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    LOG.error("Error:", ex);
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }
}
