package online.gettrained.frontend.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/lp")
public class LandingPageController {

  private static final Logger LOG = LoggerFactory.getLogger(LandingPageController.class);

  @RequestMapping("/")
  public String index(HttpServletRequest httpServletRequest) {
    LOG.debug("Landing page...");
    return "index";
  }
}
