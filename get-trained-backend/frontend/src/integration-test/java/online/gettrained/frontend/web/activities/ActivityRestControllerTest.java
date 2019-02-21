package online.gettrained.frontend.web.activities;

import online.gettrained.frontend.BaseIntegrationTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for {@link ActivityRestController}.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActivityRestControllerTest extends BaseIntegrationTest {

  @Autowired
  private ActivityRestController activityRestController;

  @Test
  public void test_001_addFitnessTrainer() {
    activityRestController.addFitnessTrainer();
  }
}
