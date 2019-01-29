package online.gettrained.frontend.config;

import online.gettrained.backend.props.ApplicationCommonProperties;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 */
@Configuration
public class RestConfiguration extends WebMvcConfigurerAdapter {

  private final ApplicationCommonProperties commonProperties;

  public RestConfiguration(ApplicationCommonProperties commonProperties) {
    this.commonProperties = commonProperties;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowCredentials(true)
      .allowedHeaders("*")
      .exposedHeaders("Access-Control-Allow-Origin")
      .allowedOrigins(commonProperties.getCorsAllowedOrigins())
      .allowedMethods("*");
  }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Stream.of("*").collect(Collectors.toList()));
//        config.addAllowedOrigin("http://localhost:3000");
//        config.addAllowedHeader("*");
//        //config.addExposedHeader("Content-Type:application/json");
//        config.addAllowedMethod("*");
//        //config.addAllowedMethod("PUT");
//        source.registerCorsConfiguration("/**", config);
//        CorsFilter bean = new CorsFilter(source);
//        //bean.setOrder(0);
//        return bean;
//    }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
//        int countMappingJackson2HttpMessageConverter = 0;
//        while (iterator.hasNext()) {
//            if (iterator.next() instanceof MappingJackson2HttpMessageConverter) {
//                if (++countMappingJackson2HttpMessageConverter > 1) {
//                    iterator.remove();
//                }
//            } else {
//                iterator.remove();
//            }
//        }
    converters.stream()
      .filter(c -> c instanceof MappingJackson2HttpMessageConverter)
      .forEach(c ->
        ((MappingJackson2HttpMessageConverter) c)
          .setSupportedMediaTypes(
            Stream.of(
              new MediaType("text", "plain"),
              new MediaType("application", "json"),
              new MediaType("application", "*+json"))
              .collect(Collectors.toList())));
  }

//  @Override
//  public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//    configurer.setDefaultTimeout(-1);
//    configurer.setTaskExecutor(asyncTaskExecutor());
//  }
//
//  @Bean
//  public AsyncTaskExecutor asyncTaskExecutor() {
//    return new SimpleAsyncTaskExecutor("stream-async");
//  }
}
