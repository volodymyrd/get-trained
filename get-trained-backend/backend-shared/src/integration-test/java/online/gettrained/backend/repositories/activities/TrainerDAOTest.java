package online.gettrained.backend.repositories.activities;

import static online.gettrained.backend.constraints.SelectOption.Option.EQ;
import static online.gettrained.backend.constraints.SelectOption.Sign.I;
import static online.gettrained.backend.domain.activities.Trainer.Status.VERIFIED;
import static online.gettrained.backend.domain.activities.Trainer.Visibility.PUBLIC;
import static online.gettrained.backend.utils.CommonUtils.immutableSetOf;

import online.gettrained.backend.BaseITest;
import online.gettrained.backend.constraints.LongSelectOption;
import online.gettrained.backend.constraints.SelectOption;
import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SQL validation  tests for {@link TrainerDAO}.
 */
@RunWith(SpringRunner.class)
public class TrainerDAOTest extends BaseITest {

  @Autowired
  private TrainerDAO trainerDAO;

  @Test
  @SuppressWarnings("unchecked")
  public void testFindAllWithCountTrainers() {
    FrontendActivityConstraint constraint = new FrontendActivityConstraint();
    constraint.setPageable(PageRequest.of(0, 10, Direction.ASC, "fullName"));
    constraint.setLangCode("EN");
    constraint.setSoActivityIds(immutableSetOf(new LongSelectOption(I, EQ, 1)));
    constraint.setSoTrainerStatuses(immutableSetOf(new SelectOption<>(I, EQ, VERIFIED)));
    constraint.setSoTrainerVisibilities(immutableSetOf(new SelectOption<>(I, EQ, PUBLIC)));
    trainerDAO.findAll(constraint);
  }
}
