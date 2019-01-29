package online.gettrained.frontend.config;

import online.gettrained.backend.props.ApplicationCommonProperties;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.servicelocator.ServiceLocator;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

/**
 * Configure Liquibase but run it in {@link ApplicationListenerConfig}
 * after complete initialization of database.
 */
@Configuration
@EnableConfigurationProperties(LiquibaseProperties.class)
public class LiquibaseConfiguration extends SpringLiquibase {

  private final ApplicationCommonProperties applicationCommonProperties;
  private final LiquibaseProperties properties;
  private final ResourceLoader resourceLoader;
  private final DataSource dataSource;

  public LiquibaseConfiguration(
      ApplicationCommonProperties applicationCommonProperties,
      LiquibaseProperties properties,
      ResourceLoader resourceLoader,
      DataSource dataSource) {
    this.applicationCommonProperties = applicationCommonProperties;
    this.properties = properties;
    this.resourceLoader = resourceLoader;
    this.dataSource = dataSource;
  }

  @PostConstruct
  public void setUp() {
    System.setProperty("clientEnvironment",
        applicationCommonProperties.getUserPropertyClientEnvironment());
    if (this.properties.isCheckChangeLogLocation()) {
      Resource resource = this.resourceLoader
          .getResource(this.properties.getChangeLog());
      Assert.state(resource.exists(),
          "Cannot find changelog location: " + resource
              + " (please add changelog or check your Liquibase "
              + "configuration)");
    }
    ServiceLocator serviceLocator = ServiceLocator.getInstance();
//    serviceLocator.addPackageToScan(
//        CommonsLoggingLiquibaseLogger.class.getPackage().getName());
    this.setChangeLog(this.properties.getChangeLog());
    this.setContexts(this.properties.getContexts());
    this.setDataSource(dataSource);
    this.setDefaultSchema(this.properties.getDefaultSchema());
    this.setShouldRun(false);
    this.setLabels(this.properties.getLabels());
    this.setChangeLogParameters(this.properties.getParameters());
  }
}
