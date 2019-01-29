package online.gettrained.frontend.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
public class ErrorsController implements ErrorController {

  private static final Logger LOG = LoggerFactory.getLogger(ErrorsController.class);

  private static final String PATH = "/error";

  @Autowired
  private ServletContext context;

  @RequestMapping(value = PATH)
  @ExceptionHandler(value = {Exception.class})
  public void error(Model model, HttpServletRequest request,
    HttpServletResponse response, Exception exception) {
    switch (response.getStatus()) {
      case 404:
        try {
          String originalUri = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
          if (originalUri != null && !originalUri.startsWith("/fe/")) {
            response.setStatus(HttpServletResponse.SC_OK);
          }
          context.getRequestDispatcher("/").forward(request, response);
        } catch (Exception e) {
          LOG.error("Error forwarding request: ", e);
        }
      case 403:
        break;
      default:
        LOG.error("Not found behave for HTTP status {}", response.getStatus());
    }
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}
