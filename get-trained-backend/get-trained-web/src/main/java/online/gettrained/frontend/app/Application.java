package online.gettrained.frontend.app;

import online.gettrained.frontend.config.BerizeApplicationListener;
import java.io.File;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude =
    {LiquibaseAutoConfiguration.class, LdapAutoConfiguration.class, QuartzAutoConfiguration.class})
@ComponentScan(basePackages = "online.gettrained")
@EntityScan(basePackages = {"online.gettrained.backend.domain"})
@EnableJpaRepositories(basePackages = "online.gettrained.backend.repositories")
@EnableCaching
@EnableBatchProcessing
public class Application extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(
      SpringApplicationBuilder application) {
    System.out.println("App root path:" + new File("testRoot").getAbsolutePath());
    application.listeners(new BerizeApplicationListener());
    return application.sources(Application.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
