package online.gettrained.frontend.web.common;

import javax.servlet.ServletContext;
import online.gettrained.backend.SharedModuleInfo;
import online.gettrained.frontend.FrontendModuleInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Get common info about app like version, modules etc.
 */
@RestController
@RequestMapping("/common/info")
public class AppInfoController {

  private static final Logger LOG = LoggerFactory.getLogger(AppInfoController.class);

  private static String version;

  @Autowired
  ServletContext context;

  @GetMapping("/version")
  public ResponseEntity<String> version() {
    try {
      if (version == null) {
        version = FrontendModuleInfo.getAppInfo().getVersion();
      }
      return ResponseEntity.ok(version);
    } catch (Exception ex) {
      LOG.error("Error getting app version", ex);
      return ResponseEntity.ok().build();
    }
  }

  @GetMapping("/details")
  public ResponseEntity<String> details() {
    try {
      StringBuilder builder = new StringBuilder();
      builder.append("</br><div style='font-size:larg;font-weight:bold'>Modules:</div>");
      builder.append(FrontendModuleInfo.getAppInfo());
      builder.append(SharedModuleInfo.getAppInfo());
      builder.append("</br>---</br>");
      return ResponseEntity.ok(builder.toString());
    } catch (Exception ex) {
      LOG.error("Error getting app version", ex);
      return ResponseEntity.ok().build();
    }
  }
}
