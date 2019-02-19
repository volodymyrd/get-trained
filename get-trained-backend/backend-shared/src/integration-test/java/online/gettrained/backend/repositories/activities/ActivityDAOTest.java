package online.gettrained.backend.repositories.activities;

import online.gettrained.backend.BaseITest;
import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Validation SQL tests for {@link ActivityDAO}.
 */
@RunWith(SpringRunner.class)
public class ActivityDAOTest extends BaseITest {

  @Autowired
  private ActivityDAO activityDAO;

  @Test
  public void testFindAllWithCountTrainers() {
    FrontendActivityConstraint constraint = new FrontendActivityConstraint();
    constraint.setPageable(PageRequest.of(0, 10, Direction.DESC, "trainers"));
    constraint.setLangCode("EN");
    activityDAO.findAllWithCountTrainers(constraint);
  }
}
