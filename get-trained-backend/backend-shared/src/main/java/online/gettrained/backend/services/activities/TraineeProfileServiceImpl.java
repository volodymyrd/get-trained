package online.gettrained.backend.services.activities;

import java.text.ParseException;
import online.gettrained.backend.constraints.frontend.activities.FrontendTraineeProfileConstraint;
import online.gettrained.backend.domain.activities.TraineeProfile;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.activities.TrainerConnections;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.exceptions.NotFoundException;
import online.gettrained.backend.repositories.activities.TraineeProfileRepository;
import online.gettrained.backend.repositories.profile.ProfileRepository;
import online.gettrained.backend.services.user.UserService;
import online.gettrained.backend.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link TraineeProfileService}.
 */
@Service
public class TraineeProfileServiceImpl implements TraineeProfileService {

  private final UserService userService;
  private final ActivityService activityService;
  private final TraineeProfileRepository traineeProfileRepository;
  private final ProfileRepository profileRepository;

  public TraineeProfileServiceImpl(
      UserService userService,
      ActivityService activityService,
      TraineeProfileRepository traineeProfileRepository,
      ProfileRepository profileRepository) {
    this.userService = userService;
    this.activityService = activityService;
    this.traineeProfileRepository = traineeProfileRepository;
    this.profileRepository = profileRepository;
  }

  @Override
  public Profile getProfile(User trainerUser, long traineeUserId)
      throws NotFoundException {
    getConnection(trainerUser, traineeUserId);

    return getTrainee(traineeUserId).getProfile();
  }

  @Override
  @Transactional
  public Profile updateProfile(User trainerUser, long traineeUserId, Profile profile)
      throws NotFoundException {
    getConnection(trainerUser, traineeUserId);

    return userService.saveProfile(getTrainee(traineeUserId), profile);
  }

  @Override
  public Page<TraineeProfile> findAllTraineeProfiles(
      User user, FrontendTraineeProfileConstraint constraint) {
    return null;
  }

  @Override
  @Transactional
  public TraineeProfile saveTraineeProfile(
      User trainerUser, long traineeUserId, TraineeProfile traineeProfile)
      throws NotFoundException {
    TrainerConnections connection = getConnection(trainerUser, traineeUserId);
    if (traineeProfile.getTraineeProfileId() == null) {
      traineeProfile.setTrainee(getTrainee(traineeUserId));
      traineeProfile.setConnection(connection);
    } else {
      long traineeProfileId = traineeProfile.getTraineeProfileId();
      TraineeProfile oldTraineeProfile =
          traineeProfileRepository.findByIdAndConnection_Id(
              traineeProfile.getTraineeProfileId(), connection.getId())
              .orElseThrow(() ->
                  new NotFoundException("Not found trainee profile with id:" + traineeProfileId));
      try {
        oldTraineeProfile
            .setDateMeasure(DateUtils.getShortDateFormat().parse(traineeProfile.getMeasure()));
      } catch (ParseException e) {
        throw new IllegalArgumentException(e);
      }
      oldTraineeProfile.setBiceps(traineeProfile.getBiceps());
      oldTraineeProfile.setCalf(traineeProfile.getCalf());
      oldTraineeProfile.setChest(traineeProfile.getChest());
      oldTraineeProfile.setForearm(traineeProfile.getForearm());
      oldTraineeProfile.setHips(traineeProfile.getHips());
      oldTraineeProfile.setInnerThigh(traineeProfile.getInnerThigh());
      oldTraineeProfile.setNeck(traineeProfile.getNeck());
      oldTraineeProfile.setWaist(traineeProfile.getWaist());
      oldTraineeProfile.setWrist(traineeProfile.getWrist());

      traineeProfile = oldTraineeProfile;
      traineeProfile.setUserLastChanged(trainerUser);
    }

    return traineeProfileRepository.save(traineeProfile);
  }

  @Override
  public TraineeProfile getTraineeProfile(
      User trainerUser, long traineeUserId, long traineeProfileId) throws NotFoundException {
    TrainerConnections connection = getConnection(trainerUser, traineeUserId);

    return null;
  }

  @Override
  @Transactional
  public void deleteTraineeProfile(
      User trainerUser, long traineeUserId, long traineeProfileId) throws NotFoundException {

  }

  private User getTrainee(long traineeUserId) throws NotFoundException {
    return userService.findByIdWithProfile(traineeUserId)
        .orElseThrow(() -> new NotFoundException("Not found a user with id:" + traineeUserId));
  }

  private TrainerConnections getConnection(User trainerUser, long traineeUserId)
      throws NotFoundException {
    Trainer trainer = activityService.findFitnessTrainer(trainerUser)
        .orElseThrow(() ->
            new NotFoundException(
                "Not found a fitness trainer for user with id:" + trainerUser.getId()));
    return activityService
        .findActiveConnectionByTrainerIdAndTraineeUserId(trainer.getId(), traineeUserId)
        .orElseThrow(
            () -> new NotFoundException("Not found a connection for trainee:" + traineeUserId));
  }
}
