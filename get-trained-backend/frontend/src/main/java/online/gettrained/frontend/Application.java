package online.gettrained.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.jdbc.DataSourcePoolMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {
  LiquibaseAutoConfiguration.class,
  DataSourcePoolMetricsAutoConfiguration.class,
  QuartzAutoConfiguration.class})
@ComponentScan(basePackages = "online.gettrained")
@EntityScan(basePackages = {"online.gettrained.backend.domain"})
@EnableJpaRepositories(basePackages = "online.gettrained.backend.repositories")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
