package online.gettrained.backend.repositories.notif;

import online.gettrained.backend.BaseITest;
import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.repositories.notif.template.MessageTemplateDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Sql({"file:../config/integration-tests/sql/test-user-data.sql", "/sql/test-user-data.sql"})
public class MessageTemplateDAOTest extends BaseITest {

  @Autowired
  private MessageTemplateDAO messageTemplateDAO;

  @Test
  public void testSQL_findTemplateWithChannelAndCompanyNull() {
    messageTemplateDAO.findOneTemplateByEventAndChannelIfSetAndLang(
        NotificationEvent.Code.RESTORE_PASSWORD,
        NotificationChannel.Code.EMAIL,
        "EN");
  }

  @Test
  public void testSQL_findTemplateWithChannelNullAndCompanyNull() {
    messageTemplateDAO.findOneTemplateByEventAndChannelIfSetAndLang(
        NotificationEvent.Code.RESTORE_PASSWORD,
        null,
        "EN");
  }

  @Test
  public void testSQL_findTemplateWithChannelAndCompanyNotNull() {
    messageTemplateDAO.findOneTemplateByEventAndChannelIfSetAndLang(
        NotificationEvent.Code.RESTORE_PASSWORD,
        NotificationChannel.Code.EMAIL,
        "EN");
  }

  @Test
  public void testSQL_findTemplateWithChannelNullAndCompanyNotNull() {
    messageTemplateDAO.findOneTemplateByEventAndChannelIfSetAndLang(
        NotificationEvent.Code.RESTORE_PASSWORD,
        null,
        "EN");
  }
}
