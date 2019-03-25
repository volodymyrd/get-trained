package online.gettrained.backend.services.activities;

import online.gettrained.backend.constraints.frontend.activities.FrontendTraineeProfileConstraint;
import online.gettrained.backend.domain.activities.FitnessTraineeProfile;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.exceptions.NotFoundException;

/**
 * Service for {@link FitnessTraineeProfile}.
 */
public interface FitnessTraineeProfileService {

  Profile getProfile(User trainerUser, long traineeUserId) throws NotFoundException;

  Profile updateProfile(User trainerUser, Profile profile) throws NotFoundException;

  Page<FitnessTraineeProfile> findAllTraineeProfiles(
      User trainerUser, FrontendTraineeProfileConstraint constraint);

  FitnessTraineeProfile saveTraineeProfile(
      User trainerUser, FitnessTraineeProfile traineeProfile) throws NotFoundException;

  FitnessTraineeProfile getTraineeProfile(
      User trainerUser, long traineeUserId, long traineeProfileId) throws NotFoundException;

  void deleteTraineeProfile(User trainerUser, long traineeUserId, long traineeProfileId)
      throws NotFoundException;
}
