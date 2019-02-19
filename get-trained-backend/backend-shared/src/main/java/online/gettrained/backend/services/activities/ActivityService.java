package online.gettrained.backend.services.activities;

import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import online.gettrained.backend.domain.activities.Activity;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.exceptions.ApplicationException;
import online.gettrained.backend.exceptions.NotFoundException;

/**
 * Main service for activity functionality.
 */
public interface ActivityService {

  Page<Activity> findAllActivities(User user, FrontendActivityConstraint constraint);

  void addTrainer(User user, long activityId) throws NotFoundException, ApplicationException;

  Page<Trainer> findAllTrainers(User user, FrontendActivityConstraint constraint);

  void requestTrainer(User user, long trainerId);

  void confirmConnection(User user, long connectionId);

  void rejectConnection(User user, long connectionId);

  void removeConnection(User user, long connectionId);

  Page<User> findAllTrainees(User user, FrontendActivityConstraint constraint);
}
