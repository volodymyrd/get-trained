package online.gettrained.backend.repositories.notif;

import static online.gettrained.backend.services.notif.handlers.NotificationHandler.MESSAGE;
import static online.gettrained.backend.services.notif.handlers.NotificationHandler.SUBJECT;
import static online.gettrained.backend.services.notif.handlers.NotificationHandler.TO;

import online.gettrained.backend.BaseITest;
import online.gettrained.backend.services.notif.handlers.NotificationHandler;
import java.util.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Sql({"file:../config/integration-tests/sql/test-user-data.sql", "/sql/test-user-data.sql"})
public class MailNotificationHandlerTest extends BaseITest {

  @Autowired
  @Qualifier("mailNotificationHandler")
  private NotificationHandler notificationHandler;

  @Test
  public void testSend() throws Exception {
    notificationHandler.send(new HashMap<String, Object>() {{
      put(SUBJECT, "test message");
      put(MESSAGE, "<h3>Test</h3> <p>message</p>");
      put(TO, new String[]{"volodymyrdotsenko@gmail.com"});
    }});
  }
}
