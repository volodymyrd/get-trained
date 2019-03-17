package online.gettrained.backend.services.activities;

import online.gettrained.backend.constraints.frontend.activities.FrontendTraineeProfileConstraint;
import online.gettrained.backend.domain.activities.TraineeProfile;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.exceptions.NotFoundException;

/**
 * Service for {@link TraineeProfile}.
 */
public interface TraineeProfileService {

  Profile getProfile(User trainerUser, long traineeUserId) throws NotFoundException;

  Profile updateProfile(User trainerUser, long traineeUserId, Profile profile)
      throws NotFoundException;

  Page<TraineeProfile> findAllTraineeProfiles(
      User trainerUser, FrontendTraineeProfileConstraint constraint);

  TraineeProfile saveTraineeProfile(
      User trainerUser, long traineeUserId, TraineeProfile traineeProfile)
      throws NotFoundException;

  TraineeProfile getTraineeProfile(User trainerUser, long traineeUserId, long traineeProfileId)
      throws NotFoundException;

  void deleteTraineeProfile(User trainerUser, long traineeUserId, long traineeProfileId)
      throws NotFoundException;
}
