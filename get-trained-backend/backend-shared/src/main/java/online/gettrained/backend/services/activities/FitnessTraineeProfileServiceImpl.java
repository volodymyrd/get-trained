package online.gettrained.backend.services.activities;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.requireNonNull;
import static online.gettrained.backend.utils.DateUtils.getShortDateFormat;

import java.text.ParseException;
import online.gettrained.backend.constraints.frontend.activities.FrontendTraineeProfileConstraint;
import online.gettrained.backend.domain.activities.FitnessTraineeProfile;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.activities.TrainerConnections;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.exceptions.NotFoundException;
import online.gettrained.backend.repositories.activities.FitnessTraineeProfileDAO;
import online.gettrained.backend.repositories.activities.FitnessTraineeProfileRepository;
import online.gettrained.backend.services.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link FitnessTraineeProfileService}.
 */
@Service
public class FitnessTraineeProfileServiceImpl implements FitnessTraineeProfileService {

  private final UserService userService;
  private final ActivityService activityService;
  private final FitnessTraineeProfileRepository traineeProfileRepository;
  private final FitnessTraineeProfileDAO fitnessTraineeProfileDAO;

  public FitnessTraineeProfileServiceImpl(
      UserService userService,
      ActivityService activityService,
      FitnessTraineeProfileRepository traineeProfileRepository,
      FitnessTraineeProfileDAO fitnessTraineeProfileDAO) {
    this.userService = userService;
    this.activityService = activityService;
    this.traineeProfileRepository = traineeProfileRepository;
    this.fitnessTraineeProfileDAO = fitnessTraineeProfileDAO;
  }

  @Override
  public Profile getProfile(User trainerUser, long traineeUserId)
      throws NotFoundException {
    requireNonNull(trainerUser, "Parameter 'trainerUser' must be filled");
    getConnection(trainerUser, traineeUserId);

    Profile profile = getTrainee(traineeUserId).getProfile();
    if (profile.getBirthday() != null) {
      profile.setBirthdayStr(getShortDateFormat().format(profile.getBirthday()));
    }
    return profile;
  }

  @Override
  @Transactional
  public Profile updateProfile(User trainerUser, Profile profile) throws NotFoundException {
    requireNonNull(trainerUser, "Parameter 'trainerUser' must be filled");
    requireNonNull(profile.getUserId(), "Parameter 'profile.userId' must be filled");

    getConnection(trainerUser, profile.getUserId());

    User traineeUser = getTrainee(profile.getUserId());
    Profile oldProfile = traineeUser.getProfile();
    oldProfile.setGender(profile.getGender());
    if (!isNullOrEmpty(profile.getBirthdayStr())) {
      try {
        oldProfile.setBirthday(getShortDateFormat().parse(profile.getBirthdayStr()));
      } catch (ParseException ex) {
        throw new IllegalArgumentException("Illegal date format:" + profile.getBirthdayStr());
      }
    }
    profile = userService.saveProfile(traineeUser, oldProfile);
    if (profile.getBirthday() != null) {
      profile.setBirthdayStr(getShortDateFormat().format(profile.getBirthday()));
    }
    return profile;
  }

  @Override
  public Page<FitnessTraineeProfile> findAllTraineeProfiles(
      User user, FrontendTraineeProfileConstraint constraint) {
    return fitnessTraineeProfileDAO.findAll(constraint);
  }

  @Override
  @Transactional
  public FitnessTraineeProfile saveTraineeProfile(
      User trainerUser, FitnessTraineeProfile traineeProfile)
      throws NotFoundException {
    requireNonNull(trainerUser, "Parameter 'trainerUser' must be filled");
    requireNonNull(traineeProfile, "Parameter 'traineeProfile' must be filled");
    requireNonNull(traineeProfile.getTraineeUserId(),
        "Parameter 'traineeProfile.traineeUserId' must be filled");
    checkArgument(!isNullOrEmpty(traineeProfile.getMeasure()),
        "Parameter 'traineeProfile.measure' must be filled");

    TrainerConnections connection = getConnection(trainerUser, traineeProfile.getTraineeUserId());
    if (traineeProfile.getTraineeProfileId() == null) {
      traineeProfile.setTrainee(getTrainee(traineeProfile.getTraineeUserId()));
      traineeProfile.setConnection(connection);
    } else {
      long traineeProfileId = traineeProfile.getTraineeProfileId();
      FitnessTraineeProfile oldTraineeProfile =
          traineeProfileRepository.findByIdAndConnection_Id(
              traineeProfile.getTraineeProfileId(), connection.getId())
              .orElseThrow(() ->
                  new NotFoundException("Not found trainee profile with id:" + traineeProfileId));
      oldTraineeProfile.setBiceps(traineeProfile.getBiceps());
      oldTraineeProfile.setCalf(traineeProfile.getCalf());
      oldTraineeProfile.setChest(traineeProfile.getChest());
      oldTraineeProfile.setForearm(traineeProfile.getForearm());
      oldTraineeProfile.setHips(traineeProfile.getHips());
      oldTraineeProfile.setInnerThigh(traineeProfile.getInnerThigh());
      oldTraineeProfile.setNeck(traineeProfile.getNeck());
      oldTraineeProfile.setWaist(traineeProfile.getWaist());
      oldTraineeProfile.setWrist(traineeProfile.getWrist());
      oldTraineeProfile.setMeasure(traineeProfile.getMeasure());

      traineeProfile = oldTraineeProfile;
      traineeProfile.setUserLastChanged(trainerUser);
    }

    try {
      traineeProfile
          .setDateMeasure(getShortDateFormat().parse(traineeProfile.getMeasure()));
    } catch (ParseException e) {
      throw new IllegalArgumentException("Illegal date format:" + traineeProfile.getMeasure());
    }

    return populateFitnessTraineeProfile(traineeProfileRepository.save(traineeProfile));
  }

  @Override
  @Transactional
  public FitnessTraineeProfile getTraineeProfile(
      User trainerUser, long traineeUserId, long traineeProfileId) throws NotFoundException {
    requireNonNull(trainerUser, "Parameter 'trainerUser' must be filled");
    TrainerConnections connection = getConnection(trainerUser, traineeUserId);
    return populateFitnessTraineeProfile(
        traineeProfileRepository.findByIdAndConnection_Id(traineeProfileId, connection.getId())
            .orElseThrow(() -> new NotFoundException(
                "Not found a trainee profile with id:" + traineeProfileId)));
  }

  private FitnessTraineeProfile populateFitnessTraineeProfile(FitnessTraineeProfile profile) {
    profile.setTraineeProfileId(profile.getId());
    profile.setConnectionId(profile.getConnection().getId());
    profile.setTraineeUserId(profile.getTrainee().getId());
    profile.setMeasure(getShortDateFormat().format(profile.getDateMeasure()));
    return profile;
  }

  @Override
  @Transactional
  public void deleteTraineeProfile(
      User trainerUser, long traineeUserId, long traineeProfileId) throws NotFoundException {
    requireNonNull(trainerUser, "Parameter 'trainerUser' must be filled");
    TrainerConnections connection = getConnection(trainerUser, traineeUserId);
    traineeProfileRepository.deleteByIdAndConnection_Id(traineeProfileId, connection.getId());
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
