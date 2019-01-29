package online.gettrained.backend.logger.stackdriver;

import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.utils.SecurityUtils;
import com.google.cloud.logging.LogEntry.Builder;
import com.google.cloud.logging.LoggingEnhancer;
import com.google.common.base.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Logging enhancer for stack-driver logging.
 */
public class StackDriverLoggingEnhancer implements LoggingEnhancer {

  @Override
  public void enhanceLogEntry(Builder logEntry) {
    User user = SecurityUtils.getCurrentUser();
    try {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
          .currentRequestAttributes();

      if (attributes != null) {
        String remoteAddress = SecurityUtils.getIpOfRemoteHost(attributes.getRequest());
        if (!Strings.isNullOrEmpty(remoteAddress)) {
          logEntry.addLabel("remoteAddress", remoteAddress);
        }
      }
    } catch (Exception ex) {
      // ignore it
    }

    if (user != null) {
      logEntry.addLabel("userEmail", user.getEmail());
    }
  }
}
