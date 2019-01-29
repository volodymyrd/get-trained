package online.gettrained.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class UtilBeans {

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationEntryPoint getAuthenticationEntryPoint() {
    return (request, response, authException) -> {
      if (authException instanceof BadCredentialsException) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
      }
      //HttpStatusCodes httpStatusCode = HttpStatusCodes.valueOf(authException.getMessage());
      //response.setStatus(httpStatusCode.getCode());
    };
  }

//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize("100KB");
//        factory.setMaxRequestSize("100KB");
//        return factory.createMultipartConfig();
//    }
}
