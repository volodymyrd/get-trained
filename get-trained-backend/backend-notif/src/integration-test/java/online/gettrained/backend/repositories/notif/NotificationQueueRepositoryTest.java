package online.gettrained.backend.repositories.notif;

import java.util.Date;
import online.gettrained.backend.BaseITest;
import online.gettrained.backend.domain.notif.ENotificationStatus;
import online.gettrained.backend.domain.notif.NotificationChannel;
import online.gettrained.backend.domain.notif.NotificationEvent;
import online.gettrained.backend.utils.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Sql({"file:../config/integration-tests/sql/test-user-data.sql", "/sql/test-user-data.sql"})
public class NotificationQueueRepositoryTest extends BaseITest {

  @Autowired
  private NotificationQueueRepository notificationQueueRepository;
  @Autowired
  private NotificationChannelRepository notificationChannelRepository;
  @Autowired
  private NotificationEventRepository notificationEventRepository;

  @Test
  public void testFindAllByStatusInAndExpireDateGreaterThan() {
    notificationQueueRepository
        .findAllByStatusInAndExpireDateGreaterThan(
            CommonUtils.immutableListOf(ENotificationStatus.NEW, ENotificationStatus.ERROR),
            new Date());
  }

  @Test
  public void testCountByEventCodeAndChannelCodeAndCompanyIdAndStatusInAndExpireDateGreaterThanAndAddressTo() {
    notificationQueueRepository.
        countByEventCodeAndChannelCodeAndStatusInAndExpireDateGreaterThanAndAddressTo(
            NotificationEvent.Code.CONFIRM_REGISTRATION,
            NotificationChannel.Code.EMAIL,
            CommonUtils.immutableListOf(ENotificationStatus.NEW,
                ENotificationStatus.PROCESSING,
                ENotificationStatus.ERROR),
            new Date(), "volodymyrdotsenko@gmail.com");
  }

  @Test
  public void testDeleteAllWithStatusErrorAndErrorAttemptsGreaterThan() {
    notificationQueueRepository.deleteAllWithStatusErrorAndErrorAttemptsGreaterThan(1);
  }
}

