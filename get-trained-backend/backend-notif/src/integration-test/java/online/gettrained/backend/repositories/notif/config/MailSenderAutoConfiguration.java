package online.gettrained.backend.repositories.notif.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.activation.MimeType;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

/**
 * {@link EnableAutoConfiguration Auto configuration} for email support.
 *
 * @author Oliver Gierke
 * @author Stephane Nicoll
 * @author Eddú Meléndez
 * @since 1.2.0
 */
@Configuration
@ConditionalOnClass({ MimeMessage.class, MimeType.class })
@ConditionalOnMissingBean(MailSender.class)
@EnableConfigurationProperties(MailProperties.class)
public class MailSenderAutoConfiguration {

    private final MailProperties properties;

    private final Session session;

    public MailSenderAutoConfiguration(MailProperties properties,
                                       ObjectProvider<Session> session) {
        this.properties = properties;
        this.session = session.getIfAvailable();
    }

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        if (this.session != null) {
            sender.setSession(this.session);
        }
        else {
            applyProperties(sender);
        }
        return sender;
    }

    private void applyProperties(JavaMailSenderImpl sender) {
        sender.setHost(this.properties.getHost());
        if (this.properties.getPort() != null) {
            sender.setPort(this.properties.getPort());
        }
        sender.setUsername(this.properties.getUsername());
        sender.setPassword(this.properties.getPassword());
        sender.setProtocol(this.properties.getProtocol());
        if (this.properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(this.properties.getDefaultEncoding().name());
        }
        if (!this.properties.getProperties().isEmpty()) {
            sender.setJavaMailProperties(asProperties(this.properties.getProperties()));
        }
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    /**
     * Condition to trigger the creation of a {@link JavaMailSenderImpl}. This kicks in if
     * either the host or jndi name property is set.
     */
    static class MailSenderCondition extends AnyNestedCondition {

        MailSenderCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix = "spring.mail", name = "host")
        static class HostProperty {

        }

        @ConditionalOnProperty(prefix = "spring.mail", name = "jndi-name")
        static class JndiNameProperty {

        }

    }

}

