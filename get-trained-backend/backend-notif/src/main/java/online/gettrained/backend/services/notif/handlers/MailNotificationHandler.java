package online.gettrained.backend.services.notif.handlers;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import online.gettrained.backend.props.ApplicationCommonProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailNotificationHandler")
public class MailNotificationHandler implements NotificationHandler {

  private static final Logger LOG = LoggerFactory.getLogger(MailNotificationHandler.class);

  private String[] bcc;

  private final ApplicationCommonProperties applicationCommonProperties;
  private final JavaMailSender mailSender;

  public MailNotificationHandler(
      ApplicationCommonProperties applicationCommonProperties,
      JavaMailSender mailSender) {
    this.applicationCommonProperties = applicationCommonProperties;
    this.mailSender = mailSender;
  }

  @PostConstruct
  public void setUp() {
    bcc = applicationCommonProperties.getMailBccAddresses().split(",");
  }


  private void sendHtmlMessage(String subject, String[] mailTo, String mailFrom, String message,
      boolean canUseBcc)
      throws MessagingException {

    MimeMessage mimeMessage = mailSender.createMimeMessage();

    // use the true flag to indicate you need a multipart message
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setSubject(subject);
    helper.setFrom(mailFrom);
    helper.setTo(mailTo);
    if (canUseBcc) {
      helper.setBcc(bcc);
    }

    // use the true flag to indicate the text included is HTML
    helper.setText(message, true);

    mailSender.send(mimeMessage);

  }

  @Override
  public void send(Map<String, Object> parameters) throws Exception {
    long startTime = System.currentTimeMillis();
    String subject = (String) parameters.get(SUBJECT);
    if (subject == null || subject.isEmpty()) {
      LOG.error("Parameter {} must be set", SUBJECT);
      return;
    }
    String message = (String) parameters.get(MESSAGE);
    if (message == null || message.isEmpty()) {
      LOG.error("Parameter {} must be set", MESSAGE);
      return;
    }
    String[] to = (String[]) parameters.get(TO);
    if (to == null || to.length == 0) {
      LOG.error("Parameter {} must be set", TO);
      return;
    }
    String from = (String) parameters.get(FROM);
    if (from == null || from.isEmpty()) {
      String noreplayAddress = applicationCommonProperties.getMailNoreplayAddress();
      LOG.warn("Parameter {} not set, using default {}", FROM, noreplayAddress);
      from = noreplayAddress;
    }
    Boolean canUseBcc = (Boolean) parameters.get(CAN_USE_BCC);
    if (canUseBcc == null) {
      LOG.warn("Parameter {} not set, using default {}", CAN_USE_BCC, false);
      canUseBcc = false;
    }
    sendHtmlMessage(subject, to, from, message, canUseBcc);
    double sec = (System.currentTimeMillis() - startTime) / 1000.0;
    LOG.info("Message was sent successfully for {} sec.", sec);
  }
}
