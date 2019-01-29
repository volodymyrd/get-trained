package online.gettrained.frontend.app;

import online.gettrained.frontend.config.BerizeApplicationListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Run by this command java -jar berize.jar --spring.config.location=/Users/vova/work/workspace/berize/github/berize/sources/java/berize/config/application-common.properties,/Users/vova/work/workspace/berize/github/berize/sources/java/berize/config/application-dev.properties
 */
@SpringBootApplication(exclude = {
    LiquibaseAutoConfiguration.class, LdapAutoConfiguration.class, QuartzAutoConfiguration.class})
@ComponentScan(basePackages = "online.gettrained")
@EntityScan(basePackages = {"online.gettrained.backend.domain"})
@EnableJpaRepositories(basePackages = "online.gettrained.backend.repositories")
@EnableCaching
@EnableBatchProcessing
public class Application {

  public static void main(String[] args) {
    SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
    builder.listeners(new BerizeApplicationListener());
    builder.run(args);
  }
}
