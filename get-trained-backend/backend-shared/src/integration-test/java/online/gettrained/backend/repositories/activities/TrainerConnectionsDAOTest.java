package online.gettrained.backend.repositories.activities;

import static online.gettrained.backend.constraints.SelectOption.Option.EQ;
import static online.gettrained.backend.constraints.SelectOption.Sign.I;
import static online.gettrained.backend.domain.activities.TrainerConnections.Status.PENDING_ON_TRAINEE;
import static online.gettrained.backend.utils.CommonUtils.immutableSetOf;
import static org.springframework.data.domain.Sort.Direction.ASC;

import online.gettrained.backend.BaseITest;
import online.gettrained.backend.constraints.LongSelectOption;
import online.gettrained.backend.constraints.SelectOption;
import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SQL validation  tests for {@link TrainerConnectionsDAO}.
 */
@RunWith(SpringRunner.class)
public class TrainerConnectionsDAOTest extends BaseITest {

  @Autowired
  private TrainerConnectionsDAO trainerConnectionsDAO;

  @Test
  @SuppressWarnings("unchecked")
  public void testFindAllWithCountTrainers() {
    FrontendActivityConstraint constraint = new FrontendActivityConstraint();
    constraint.setPageable(PageRequest.of(0, 10, ASC, "trainerFullName"));
    constraint.setSoTrainerIds(immutableSetOf(new LongSelectOption(I, EQ, 1)));
    constraint.setSoUserTrainerIds(immutableSetOf(new LongSelectOption(I, EQ, 1)));
    constraint.setSoUserTraineeIds(immutableSetOf(new LongSelectOption(I, EQ, 1)));
    constraint
        .setSoConnectionsStatuses(immutableSetOf(new SelectOption<>(I, EQ, PENDING_ON_TRAINEE)));
    trainerConnectionsDAO.findAll(constraint);
  }
}
