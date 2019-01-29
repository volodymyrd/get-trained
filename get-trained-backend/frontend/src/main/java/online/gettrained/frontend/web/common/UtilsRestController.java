package online.gettrained.frontend.web.common;

import online.gettrained.backend.utils.SecurityUtils;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/common/utils")
public class UtilsRestController {

  private static final Logger LOG = LoggerFactory.getLogger(UtilsRestController.class);

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UtilsRestController(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/encodePassword")
  public String encodePassword(@RequestParam("value") String value) {
    LOG.info("Requested encodePassword utils...");
    return passwordEncoder.encode(value);
  }

  @GetMapping("/rsa-encrypt")
  public String rsaEncrypt(@RequestParam("value") String value) {
    LOG.info("Requested rsaEncrypt utils...");
    try {
      return Arrays.toString(SecurityUtils.rsaEncrypt(value));
    } catch (Exception e) {
      LOG.error("Error rsa encoding", e);
      return "";
    }
  }
}
