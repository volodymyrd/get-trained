package online.gettrained.backend.services.activities;

import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import online.gettrained.backend.domain.activities.Activity;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.activities.TrainerConnections;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.exceptions.ApplicationException;
import online.gettrained.backend.exceptions.NotFoundException;

/**
 * Main service for activity functionality.
 */
public interface ActivityService {

  long FITNESS_ACTIVITY_ID = -1;

  Page<Activity> findAllActivities(User user, FrontendActivityConstraint constraint);

  void addFitnessTrainer(User user) throws NotFoundException, ApplicationException;

  void addTrainer(User user, long activityId) throws NotFoundException, ApplicationException;

  void requestFitnessTrainee(User user, String traineeEmail)
      throws NotFoundException, ApplicationException;

  void requestTrainee(User user, long activityId, String traineeEmail)
      throws NotFoundException, ApplicationException;

  Page<Trainer> findAllTrainers(User user, FrontendActivityConstraint constraint);

  Page<TrainerConnections> findMyConnections(User user, int offset, int pageSize);

  Page<TrainerConnections> findAllConnections(User user, FrontendActivityConstraint constraint);

  void requestTrainer(User user, long trainerId);

  void confirmConnection(User user, long connectionId);

  void rejectConnection(User user, long connectionId);

  void removeConnection(User user, long connectionId);

  Page<User> findAllTrainees(User user, FrontendActivityConstraint constraint);
}
