package online.gettrained.backend.services.activities;

import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import online.gettrained.backend.domain.activities.Activity;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ActivityService}.
 */
@Service
public class ActivityServiceImpl implements ActivityService{


  @Override
  public Page<Activity> findAllActivities(User user, FrontendActivityConstraint constraint) {
    return null;
  }

  @Override
  public Page<Trainer> findAllTrainers(User user, FrontendActivityConstraint constraint) {
    return null;
  }

  @Override
  public void requestTrainer(User user, long trainerId) {

  }

  @Override
  public void confirmConnection(User user, long connectionId) {

  }

  @Override
  public void rejectConnection(User user, long connectionId) {

  }

  @Override
  public void removeConnection(User user, long connectionId) {

  }

  @Override
  public Page<User> findAllTrainees(User user, FrontendActivityConstraint constraint) {
    return null;
  }
}
