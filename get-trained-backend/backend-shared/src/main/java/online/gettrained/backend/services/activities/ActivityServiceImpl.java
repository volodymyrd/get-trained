package online.gettrained.backend.services.activities;

import static online.gettrained.backend.constraints.SelectOption.Option.EQ;
import static online.gettrained.backend.constraints.SelectOption.Sign.I;
import static online.gettrained.backend.domain.activities.TrainerConnections.Status.CONNECTED;
import static online.gettrained.backend.domain.activities.TrainerConnections.Status.PENDING_ON_TRAINEE;
import static online.gettrained.backend.exceptions.ErrorCode.ACTIVITY_CONNECTION_REQUEST_EXISTS;
import static online.gettrained.backend.exceptions.ErrorCode.ACTIVITY_YOU_ARE_ALREADY_CONNECTED;
import static online.gettrained.backend.exceptions.ErrorCode.ACTIVITY_YOU_ARE_ALREADY_TRAINER;
import static online.gettrained.backend.repositories.user.RoleRepository.TRAINER_ROLE;
import static online.gettrained.backend.utils.CommonUtils.immutableListOf;
import static online.gettrained.backend.utils.CommonUtils.immutableSetOf;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.Optional;
import online.gettrained.backend.constraints.LongSelectOption;
import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import online.gettrained.backend.domain.activities.Activity;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.activities.Trainer.Status;
import online.gettrained.backend.domain.activities.Trainer.Visibility;
import online.gettrained.backend.domain.activities.TrainerConnections;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.dto.TextInfoDto;
import online.gettrained.backend.dto.TextInfoDto.Type;
import online.gettrained.backend.exceptions.ApplicationException;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.exceptions.NotFoundException;
import online.gettrained.backend.repositories.activities.ActivityDAO;
import online.gettrained.backend.repositories.activities.ActivityRepository;
import online.gettrained.backend.repositories.activities.TrainerConnectionsDAO;
import online.gettrained.backend.repositories.activities.TrainerConnectionsRepository;
import online.gettrained.backend.repositories.activities.TrainerDAO;
import online.gettrained.backend.repositories.activities.TrainerRepository;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link ActivityService}.
 */
@Service
public class ActivityServiceImpl implements ActivityService {

  private static final Logger LOG = LoggerFactory.getLogger(ActivityServiceImpl.class);

  private final LocalizationService localizationService;
  private final UserService userService;
  private final ActivityRepository activityRepository;
  private final TrainerRepository trainerRepository;
  private final TrainerConnectionsRepository trainerConnectionsRepository;
  private final ActivityDAO activityDAO;
  private final TrainerDAO trainerDAO;
  private final TrainerConnectionsDAO connectionsDAO;

  public ActivityServiceImpl(
      LocalizationService localizationService,
      UserService userService,
      ActivityRepository activityRepository,
      TrainerRepository trainerRepository,
      TrainerConnectionsRepository trainerConnectionsRepository,
      ActivityDAO activityDAO,
      TrainerDAO trainerDAO,
      TrainerConnectionsDAO connectionsDAO) {
    this.localizationService = localizationService;
    this.userService = userService;
    this.activityRepository = activityRepository;
    this.trainerRepository = trainerRepository;
    this.trainerConnectionsRepository = trainerConnectionsRepository;
    this.activityDAO = activityDAO;
    this.trainerDAO = trainerDAO;
    this.connectionsDAO = connectionsDAO;
  }

  @Override
  public Page<Activity> findAllActivities(User user, FrontendActivityConstraint constraint) {
    return activityDAO.findAllWithCountTrainers(constraint);
  }

  @Override
  public void addFitnessTrainer(User user) throws NotFoundException, ApplicationException {
    addTrainer(user, FITNESS_ACTIVITY_ID);
  }

  @Override
  @Transactional
  public void addTrainer(User user, long activityId)
      throws NotFoundException, ApplicationException {

    Activity activity = activityRepository.findById(activityId)
        .orElseThrow(() -> new NotFoundException("Not found an activity with id: " + activityId));

    if (trainerRepository
        .existsByActivity_IdAndUser_IdAndDeleted(activityId, user.getId(), false)) {
      LOG.warn("The user with id:{} is already a trainer for the activity with id:{}",
          user.getId(), activity.getId());
      throw new ApplicationException(new ErrorInfoDto(
          ACTIVITY_YOU_ARE_ALREADY_TRAINER,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              ACTIVITY_YOU_ARE_ALREADY_TRAINER.toString(),
              user.getLoginLang(),
              "You are already a trainer")));
    }
    Trainer trainer = new Trainer();
    trainer.setUser(user);
    trainer.setActivity(activity);
    trainer.setStatus(Status.VERIFIED);
    trainer.setVisibility(Visibility.PUBLIC);
    trainer.setDeleted(false);
    trainerRepository.save(trainer);
    User userWithRoles = userService.findByIdWithRoles(user.getId())
        .orElseThrow(() -> new NotFoundException("Not found a user with id: " + user.getId()));
    if (userWithRoles.getRoles().stream().noneMatch(r -> r.getName().equals(TRAINER_ROLE))) {
      userService.addRole(userWithRoles, TRAINER_ROLE);
    }
    LOG.info("New trainer created for the user with id:{} and the activity with id:{}",
        user.getId(), activity.getId());
  }

  @Override
  public void requestFitnessTrainee(User user, String traineeEmail)
      throws NotFoundException, ApplicationException {
    requestTrainee(user, FITNESS_ACTIVITY_ID, traineeEmail);
  }

  @Override
  @Transactional
  public void requestTrainee(User user, long activityId, String traineeEmail)
      throws NotFoundException, ApplicationException {

    activityRepository.findById(activityId)
        .orElseThrow(() -> new NotFoundException("Not found an activity with id: " + activityId));

    Trainer trainer = trainerRepository
        .findByActivity_IdAndUser_IdAndStatusInAndVisibilityInAndDeleted(
            activityId,
            user.getId(),
            immutableSetOf(Status.VERIFIED),
            immutableSetOf(Visibility.PUBLIC),
            false).orElseThrow(() -> new NotFoundException(
            "Not found a verified and public trainer for a user with id:" + user.getId()));

    User traineeUser = userService.findOneByEmail(traineeEmail)
        .orElseThrow(() -> new NotFoundException("Not found a user with email:" + traineeEmail));

    Optional<TrainerConnections> connectionsOptional = trainerConnectionsRepository
        .findByTrainer_IdAndTrainee_Id(trainer.getId(), user.getId());

    TrainerConnections connections;
    if (!connectionsOptional.isPresent()) {
      connections = new TrainerConnections();
      connections.setTrainee(traineeUser);
      connections.setTrainer(trainer);
      connections.setUserTrainer(user);
    } else {
      connections = connectionsOptional.get();
      if (connections.isDeleted()) {
        connections.setDeleted(true);
        connections.setUserLastChanged(user);
      } else if (connections.getStatus() == CONNECTED) {
        throw new ApplicationException(new TextInfoDto(
            Type.I,
            ACTIVITY_YOU_ARE_ALREADY_CONNECTED,
            localizationService.getLocalTextByKeyAndLangOrUseDefault(
                ACTIVITY_YOU_ARE_ALREADY_CONNECTED.toString(),
                user.getLoginLang(),
                "You are already connected")));
      } else {
        throw new ApplicationException(new TextInfoDto(
            Type.I,
            ACTIVITY_CONNECTION_REQUEST_EXISTS,
            localizationService.getLocalTextByKeyAndLangOrUseDefault(
                ACTIVITY_CONNECTION_REQUEST_EXISTS.toString(),
                user.getLoginLang(),
                "Connection request already exists")));
      }
    }

    connections.setStatus(PENDING_ON_TRAINEE);
    connections = trainerConnectionsRepository.save(connections);
    LOG.info("Request with id:{} for trainee created successfully", connections.getId());
  }

  @Override
  public Page<Trainer> findAllTrainers(User user, FrontendActivityConstraint constraint) {
    return trainerDAO.findAll(constraint);
  }

  @Override
  public Page<TrainerConnections> findMyConnections(User user, int offset, int pageSize) {
    boolean isTrainer = userService.hasRoles(user.getId(), immutableListOf(TRAINER_ROLE));
    FrontendActivityConstraint constraint = new FrontendActivityConstraint();
    if (isTrainer) {
      constraint.setPageable(PageRequest.of(offset, pageSize, ASC, "traineeFullName"));
      constraint.setSoUserTrainerIds(immutableSetOf(new LongSelectOption(I, EQ, user.getId())));
    } else {
      constraint.setPageable(PageRequest.of(offset, pageSize, ASC, "trainerFullName"));
      constraint.setSoUserTraineeIds(immutableSetOf(new LongSelectOption(I, EQ, user.getId())));
    }
    return findAllConnections(user, constraint);
  }

  @Override
  public Page<TrainerConnections> findAllConnections(
      User user, FrontendActivityConstraint constraint) {
    return connectionsDAO.findAll(constraint);
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
