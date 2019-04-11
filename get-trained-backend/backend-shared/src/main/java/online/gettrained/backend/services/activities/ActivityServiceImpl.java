package online.gettrained.backend.services.activities;

import static java.util.Objects.requireNonNull;
import static online.gettrained.backend.constraints.SelectOption.Option.EQ;
import static online.gettrained.backend.constraints.SelectOption.Sign.I;
import static online.gettrained.backend.domain.activities.TrainerConnections.Status.CONNECTED;
import static online.gettrained.backend.domain.activities.TrainerConnections.Status.PENDING_ON_TRAINEE;
import static online.gettrained.backend.exceptions.ErrorCode.ACTIVITY_CANNOT_BE_TRAINER_FOR_YOURSELF;
import static online.gettrained.backend.exceptions.ErrorCode.ACTIVITY_NOT_FOUND_TRAINEE_WITH_EMAIL;
import static online.gettrained.backend.messages.TextCode.ACTIVITY_CONNECTION_REQUEST_EXISTS;
import static online.gettrained.backend.messages.TextCode.ACTIVITY_YOU_ARE_ALREADY_CONNECTED;
import static online.gettrained.backend.messages.TextCode.ACTIVITY_YOU_ARE_ALREADY_TRAINER;
import static online.gettrained.backend.repositories.user.RoleRepository.TRAINER_ROLE;
import static online.gettrained.backend.utils.CommonUtils.immutableListOf;
import static online.gettrained.backend.utils.CommonUtils.immutableSetOf;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.Date;
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

    requireNonNull(user, "Parameter 'user' must be filled.");

    Activity activity = activityRepository.findById(activityId)
        .orElseThrow(() -> new NotFoundException("Not found an activity with id: " + activityId));

    Optional<Trainer> trainerOptional = trainerRepository
        .findByActivity_IdAndUser_Id(activityId, user.getId());

    Trainer trainer;
    if (trainerOptional.isPresent()) {
      trainer = trainerOptional.get();
      if (!trainer.isDeleted()) {
        LOG.warn("The user with id:{} is already a trainer for the activity with id:{}",
            user.getId(), activity.getId());
        throw new ApplicationException(new TextInfoDto(
            Type.I,
            ACTIVITY_YOU_ARE_ALREADY_TRAINER,
            localizationService.getLocalTextByKeyAndLangOrUseDefault(
                ACTIVITY_YOU_ARE_ALREADY_TRAINER.toString(),
                user.getLoginLang(),
                "You are already a trainer")));
      }
    } else {
      trainer = new Trainer();
      trainer.setUser(user);
      trainer.setActivity(activity);
    }

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
  public void removeFitnessTrainer(User user) throws NotFoundException {
    removeTrainer(user, FITNESS_ACTIVITY_ID);
  }

  @Override
  @Transactional
  public void removeTrainer(User user, long activityId) throws NotFoundException {
    activityRepository.findById(activityId)
        .orElseThrow(() -> new NotFoundException("Not found an activity with id: " + activityId));

    requireNonNull(user, "Parameter 'user' must be filled.");

    Optional<Trainer> trainerOptional =
        trainerRepository.findByActivity_IdAndUser_IdAndDeleted(activityId, user.getId(), false);

    if (trainerOptional.isPresent()) {
      Trainer trainer = trainerOptional.get();
      trainer.setUserLastChanged(user);
      trainer.setDeleted(true);
      trainer.setDateDeleted(new Date());
      trainerRepository.save(trainer);
      if (!trainerRepository.existsByUser_IdAndDeleted(user.getId(), false)) {
        User userWithRoles = userService.findByIdWithRoles(user.getId())
            .orElseThrow(() -> new NotFoundException("Not found a user with id: " + user.getId()));
        if (userWithRoles.getRoles().stream().anyMatch(r -> r.getName().equals(TRAINER_ROLE))) {
          userService.removeRole(userWithRoles, TRAINER_ROLE);
        }
      }
      LOG.info("Trainer with id {} deleted successfully", trainer.getId());
    } else {
      LOG.warn("Not found a trainer for user with id:{} and activity with id:{}",
          user.getId(), activityId);
    }
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

    requireNonNull(user, "Parameter 'user' must be filled.");

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
        .orElseThrow(() -> new ApplicationException(new ErrorInfoDto(
            ACTIVITY_NOT_FOUND_TRAINEE_WITH_EMAIL,
            localizationService.getLocalTextByKeyAndLangOrUseDefault(
                ACTIVITY_NOT_FOUND_TRAINEE_WITH_EMAIL.toString(),
                user.getLoginLang(),
                "Not found a trainee with such email."))));

    if (traineeUser.getId().equals(user.getId())) {
      throw new ApplicationException(new ErrorInfoDto(
          ACTIVITY_CANNOT_BE_TRAINER_FOR_YOURSELF,
          localizationService.getLocalTextByKeyAndLangOrUseDefault(
              ACTIVITY_CANNOT_BE_TRAINER_FOR_YOURSELF.toString(),
              user.getLoginLang(),
              "You cannot be a trainer for yourself.")));
    }

    Optional<TrainerConnections> connectionsOptional = trainerConnectionsRepository
        .findByTrainer_IdAndTrainee_Id(trainer.getId(), traineeUser.getId());

    TrainerConnections connections;
    if (!connectionsOptional.isPresent()) {
      connections = new TrainerConnections();
      connections.setTrainee(traineeUser);
      connections.setTrainer(trainer);
      connections.setUserTrainer(user);
    } else {
      connections = connectionsOptional.get();
      if (connections.isDeleted()) {
        connections.setDeleted(false);
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
    LOG.info("Request with id:{} for trainee with email {} created successfully",
        connections.getId(), traineeEmail);
  }

  @Override
  @Transactional
  public void acceptConnectionRequest(User user, long connectionId) throws NotFoundException {
    requireNonNull(user, "Parameter 'user' must be filled.");

    TrainerConnections connections = getConnectionById(user, connectionId);

    switch (connections.getStatus()) {
      case PENDING_ON_TRAINEE:
        if (!connections.getTrainee().getId().equals(user.getId())) {
          LOG.error("Trainee with id:{} cannot accept a foreign connection with id:{}",
              user.getId(), connectionId);
          throw new IllegalArgumentException("Trainee cannot accept a foreign connection.");
        }
        break;
      case CONNECTED:
        LOG.info("Connection with id:{} already accepted", connectionId);
        return;
    }

    connections.setStatus(CONNECTED);
    connections.setUserLastChanged(user);
    trainerConnectionsRepository.save(connections);

    LOG.info("Connection with id:{} accepted by user with id:{}", connectionId, user.getId());
  }

  @Override
  @Transactional
  public void removeConnection(User user, long connectionId) throws NotFoundException {
    requireNonNull(user, "Parameter 'user' must be filled.");

    TrainerConnections connections = trainerConnectionsRepository.findById(connectionId)
        .orElseThrow(
            () -> new NotFoundException("Not found a connection with id: " + connectionId));

    if (connections.isDeleted()) {
      LOG.warn("Connection with id:{} is deleted, nothing to change", connectionId);
      return;
    }

    if (!connections.getUserTrainer().getId().equals(user.getId())
        && !connections.getTrainee().getId().equals(user.getId())) {
      LOG.error("User with id:{} cannot delete a foreign connection with id:{}",
          user.getId(), connectionId);
      throw new IllegalArgumentException("User cannot delete a foreign connection.");
    }

    trainerConnectionsRepository.deleteById(connectionId);

    LOG.info("Trying to delete the connection with id:{}", connectionId);
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
  public boolean isFitnessTrainer(User user) throws NotFoundException {
    return isTrainer(user, FITNESS_ACTIVITY_ID);
  }

  @Override
  public boolean isTrainer(User user, long activityId) throws NotFoundException {
    activityRepository.findById(activityId)
        .orElseThrow(() -> new NotFoundException("Not found an activity with id: " + activityId));

    if (!userService.hasRoles(user.getId(), immutableListOf(TRAINER_ROLE))) {
      LOG.warn("User with id {} doesn't have a role {}", user.getId(), TRAINER_ROLE);
      return false;
    }

    return trainerRepository
        .existsByActivity_IdAndUser_IdAndDeleted(activityId, user.getId(), false);
  }

  @Override
  public Optional<TrainerConnections> findActiveConnectionByTrainerIdAndTraineeUserId(
      long trainerId, long traineeUserId) {
    return trainerConnectionsRepository.findByTrainer_IdAndTrainee_IdAndStatusAndDeleted(
        trainerId, traineeUserId, CONNECTED, false);
  }

  @Override
  public Optional<Trainer> findFitnessTrainer(User user) throws NotFoundException {
    return findTrainer(user, FITNESS_ACTIVITY_ID);
  }

  @Override
  public Optional<Trainer> findTrainer(User user, long activityId) throws NotFoundException {
    activityRepository.findById(activityId)
        .orElseThrow(() -> new NotFoundException("Not found an activity with id: " + activityId));

    if (!userService.hasRoles(user.getId(), immutableListOf(TRAINER_ROLE))) {
      LOG.warn("User with id {} doesn't have a role {}", user.getId(), TRAINER_ROLE);
      throw new NotFoundException("Not found a trainer with id: " + user.getId());
    }

    return trainerRepository.findByActivity_IdAndUser_IdAndStatusInAndVisibilityInAndDeleted(
        activityId,
        user.getId(),
        immutableSetOf(Status.VERIFIED),
        immutableSetOf(Visibility.PUBLIC),
        false);
  }

  @Override
  public void requestTrainer(User user, long trainerId) {

  }

  @Override
  public Page<User> findAllTrainees(User user, FrontendActivityConstraint constraint) {
    return null;
  }

  @Override
  public User getTrainee(long traineeUserId) throws NotFoundException {
    return userService.findByIdWithProfile(traineeUserId)
        .orElseThrow(() -> new NotFoundException("Not found a user with id:" + traineeUserId));
  }

  @Override
  public TrainerConnections getConnection(User trainerUser, long traineeUserId)
      throws NotFoundException {
    Trainer trainer = findFitnessTrainer(trainerUser)
        .orElseThrow(() ->
            new NotFoundException(
                "Not found a fitness trainer for user with id:" + trainerUser.getId()));
    return findActiveConnectionByTrainerIdAndTraineeUserId(trainer.getId(), traineeUserId)
        .orElseThrow(
            () -> new NotFoundException("Not found a connection for trainee:" + traineeUserId));
  }

  @Override
  public TrainerConnections getConnectionById(User user, long connectionId)
      throws NotFoundException {
    TrainerConnections connections = trainerConnectionsRepository.findById(connectionId)
        .orElseThrow(
            () -> new NotFoundException("Not found a connection with id: " + connectionId));

    if (connections.isDeleted()) {
      throw new NotFoundException("Connection is deleted with id: " + connectionId);
    }

    if (!connections.getUserTrainer().getId().equals(user.getId())
        && !connections.getTrainee().getId().equals(user.getId())) {
      LOG.error("User with id:{} doesn't belong to connection with id:{}",
          user.getId(), connectionId);
      throw new NotFoundException("Connection doesn't belong to the user with id:" + user.getId());
    }

    return connections;
  }
}
