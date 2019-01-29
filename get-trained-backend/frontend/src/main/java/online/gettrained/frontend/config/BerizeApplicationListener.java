package online.gettrained.frontend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Berize implementation of {@link ApplicationListener}, run just after {@link
 * ConfigFileApplicationListener}.
 */
public class BerizeApplicationListener implements ApplicationListener<ApplicationEvent>,
  Ordered {

  private static final Logger LOG = LoggerFactory.getLogger(BerizeApplicationListener.class);

  private static String PUB_SUB_PROJECT_ID_PROP_NAME = "PUB_SUB_PROJECT_ID";
  private static String PUB_SUB_TOPIC_PROP_NAME = "PUB_SUB_TOPIC";
  private static String PUB_SUB_PATH_TO_JSON_KEY_CREDENTIAL_PROP_NAME = "PUB_SUB_PATH_TO_JSON_KEY_CREDENTIAL";

  private static String STACK_DRIVER_PATH_TO_JSON_KEY_CREDENTIAL_PROP_NAME = "STACK_DRIVER_PATH_TO_JSON_KEY_CREDENTIAL";

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    if (event instanceof ApplicationStartingEvent) {
      LOG.debug("'Start' event...");
      //onApplicationStartingEvent((ApplicationStartingEvent) event);
    } else if (event instanceof ApplicationEnvironmentPreparedEvent) {
      LOG.debug("'Environment Prepared' event...");
      onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
    } else if (event instanceof ApplicationPreparedEvent) {
      LOG.debug("'Application Environment Prepared' event...");
      //onApplicationPreparedEvent((ApplicationPreparedEvent) event);
    } else if (event instanceof ContextClosedEvent) {
      LOG.debug("'Context Closed' event...");
      //onContextClosedEvent();
    } else if (event instanceof ApplicationFailedEvent) {
      LOG.debug("'Application Failed' event...");
      //onApplicationFailedEvent();
    }
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE + 11;
  }

  private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
    ConfigurableEnvironment configurableEnvironment = event.getEnvironment();
    setSystemProperty(configurableEnvironment, PUB_SUB_PROJECT_ID_PROP_NAME,
      "logging.gcp.projectId");
    setSystemProperty(configurableEnvironment, PUB_SUB_TOPIC_PROP_NAME, "logging.gcp.topic");
    setSystemProperty(
      configurableEnvironment,
      PUB_SUB_PATH_TO_JSON_KEY_CREDENTIAL_PROP_NAME,
      "logging.gcp.pathToJsonKeyCredential");

    setSystemProperty(
      configurableEnvironment,
      STACK_DRIVER_PATH_TO_JSON_KEY_CREDENTIAL_PROP_NAME,
      "logging.stack-driver.pathToJsonKeyCredential");
  }

  private void setSystemProperty(
    ConfigurableEnvironment configurableEnvironment, String systemPropertyName,
    String propertyName) {
    setSystemProperty(systemPropertyName, configurableEnvironment.getProperty(propertyName));
  }

  private void setSystemProperty(String name, String value) {
    if (System.getProperty(name) == null && value != null) {
      System.setProperty(name, value);
    }
  }
}
