package online.gettrained.batch;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import online.gettrained.backend.services.notif.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
//@SpringBootTest(classes = BatchModule.class)
@AutoConfigureTestDatabase(replace = NONE)
@Sql({"file:../config/integration-tests/sql/test-user-data.sql"})
//@TestPropertySource(locations = {
//"file:../config/integration-tests/application-backend-test.properties",
//"file:../config/integration-tests/application-integration-test-mysql.properties",
//"file:../config/integration-tests/application-integration-test-postgresql.properties"
//})
public class BaseITest {

  @MockBean
  @Qualifier("appExecutorThreadPool")
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;
  @MockBean
  private JavaMailSender javaMailSender;
  @MockBean
  protected NotificationService notificationService;
}
