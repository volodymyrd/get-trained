package online.gettrained.backend.services.activities;

import static online.gettrained.backend.exceptions.ErrorCode.ACTIVITY_YOU_ARE_ALREADY_TRAINER;
import static online.gettrained.backend.repositories.user.RoleRepository.TRAINER_ROLE;

import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import online.gettrained.backend.domain.activities.Activity;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.activities.Trainer.Status;
import online.gettrained.backend.domain.activities.Trainer.Visibility;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.exceptions.ApplicationException;
import online.gettrained.backend.exceptions.ErrorInfoDto;
import online.gettrained.backend.exceptions.NotFoundException;
import online.gettrained.backend.repositories.activities.ActivityDAO;
import online.gettrained.backend.repositories.activities.ActivityRepository;
import online.gettrained.backend.repositories.activities.TrainerDAO;
import online.gettrained.backend.repositories.activities.TrainerRepository;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private final ActivityDAO activityDAO;
  private final TrainerDAO trainerDAO;

  public ActivityServiceImpl(
      LocalizationService localizationService,
      UserService userService,
      ActivityRepository activityRepository,
      TrainerRepository trainerRepository,
      ActivityDAO activityDAO,
      TrainerDAO trainerDAO) {
    this.localizationService = localizationService;
    this.userService = userService;
    this.activityRepository = activityRepository;
    this.trainerRepository = trainerRepository;
    this.activityDAO = activityDAO;
    this.trainerDAO = trainerDAO;
  }

  @Override
  public Page<Activity> findAllActivities(User user, FrontendActivityConstraint constraint) {
    return activityDAO.findAllWithCountTrainers(constraint);
  }

  @Override
  @Transactional
  public void addTrainer(User user, long activityId)
      throws NotFoundException, ApplicationException {

    Activity activity = activityRepository.findById(activityId)
        .orElseThrow(() -> new NotFoundException("Not found an activity with id: " + activityId));

    if (trainerRepository.existsByActivity_IdAndUser_Id(activityId, user.getId())) {
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
  public Page<Trainer> findAllTrainers(User user, FrontendActivityConstraint constraint) {
    return trainerDAO.findAll(constraint);
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
