package online.gettrained.frontend.web.activities;

import static online.gettrained.backend.constraints.SelectOption.Option.EQ;
import static online.gettrained.backend.constraints.SelectOption.Sign.I;
import static online.gettrained.backend.domain.activities.Trainer.Status.VERIFIED;
import static online.gettrained.backend.domain.activities.Trainer.Visibility.PUBLIC;
import static online.gettrained.backend.messages.TextCode.ACTIVITY_SUCCESS_SENT_TRAINEE_CONNECTION_REQUEST;
import static online.gettrained.backend.messages.TextCode.ACTIVITY_SUCCESS_TRAINER_ADDED;
import static online.gettrained.backend.messages.TextCode.ACTIVITY_SUCCESS_TRAINER_REMOVED;
import static online.gettrained.backend.repositories.user.RoleRepository.TRAINER_ROLE;
import static online.gettrained.backend.services.activities.ActivityService.FITNESS_ACTIVITY_ID;
import static online.gettrained.backend.utils.CommonUtils.immutableSetOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.Optional;
import online.gettrained.backend.constraints.LongSelectOption;
import online.gettrained.backend.constraints.SelectOption;
import online.gettrained.backend.constraints.frontend.activities.FrontendActivityConstraint;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.activities.TrainerConnections;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.dto.TextInfoDto;
import online.gettrained.backend.messages.MessageCode;
import online.gettrained.backend.repositories.activities.TrainerConnectionsRepository;
import online.gettrained.backend.repositories.activities.TrainerRepository;
import online.gettrained.backend.services.user.UserService;
import online.gettrained.frontend.BaseIntegrationTest;
import online.gettrained.frontend.web.dto.FlagResponse;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for {@link ActivityRestController}.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActivityRestControllerTest extends BaseIntegrationTest {

  @Autowired
  private ActivityRestController activityRestController;

  @Autowired
  private TrainerRepository trainerRepository;
  @Autowired
  private TrainerConnectionsRepository trainerConnectionsRepository;
  @Autowired
  private UserService userService;

  @Test
  public void test_000_isFitnessTrainer() {
    assertFalse(isFitnessTrainer());
  }

  @Test
  public void test_001_addFitnessTrainer() {
    addFitnessTrainer();
  }

  @Test
  public void test_002_isFitnessTrainer() {
    assertTrue(isFitnessTrainer());
  }

  @Test
  public void test_005_removeFitnessTrainer() {
    // Act
    ResponseEntity<?> response = activityRestController.removeFitnessTrainer();

    // Assert
    Optional<Trainer> trainerOptional =
        verifyTrainerResponse(response, ACTIVITY_SUCCESS_TRAINER_REMOVED);
    assertTrue(trainerOptional.isPresent());
    assertFalse(trainerRepository.existsByActivity_IdAndUser_IdAndDeleted(
        FITNESS_ACTIVITY_ID, getUserId(), false));
    assertTrue(userService.findByIdWithRoles(getUserId())
        .orElseThrow(IllegalStateException::new)
        .getRoles().stream().noneMatch(r -> r.getName().equals(TRAINER_ROLE)));
  }

  @Test
  public void test_007_isFitnessTrainer() {
    assertFalse(isFitnessTrainer());
  }

  @Test
  public void test_010_addFitnessTrainer() {
    addFitnessTrainer();
  }

  @Test
  public void test_012_isFitnessTrainer() {
    assertTrue(isFitnessTrainer());
  }

  private boolean isFitnessTrainer() {
    // Act
    ResponseEntity<?> response = activityRestController.isFitnessTrainer();

    // Assert
    assertEquals(HTTP_OK, response.getStatusCodeValue());
    FlagResponse flagResponse = (FlagResponse) response.getBody();
    assertNotNull(flagResponse);
    return flagResponse.isFlag();
  }

  private void addFitnessTrainer() {
    // Act
    ResponseEntity<?> response = activityRestController.addFitnessTrainer();

    // Assert
    Optional<Trainer> trainerOptional =
        verifyTrainerResponse(response, ACTIVITY_SUCCESS_TRAINER_ADDED);
    assertTrue(trainerOptional.isPresent());
    assertEquals(PUBLIC, trainerOptional.get().getVisibility());
    assertEquals(VERIFIED, trainerOptional.get().getStatus());
    assertTrue(trainerRepository.existsByActivity_IdAndUser_IdAndDeleted(
        FITNESS_ACTIVITY_ID, getUserId(), false));
    assertTrue(userService.findByIdWithRoles(getUserId())
        .orElseThrow(IllegalStateException::new)
        .getRoles().stream().anyMatch(r -> r.getName().equals(TRAINER_ROLE)));
  }

  private Optional<Trainer> verifyTrainerResponse(ResponseEntity<?> response, MessageCode code) {
    assertEquals(HTTP_OK, response.getStatusCodeValue());
    TextInfoDto infoDto = (TextInfoDto) response.getBody();
    assertNotNull(infoDto);
    assertEquals(code, infoDto.getCode());
    return trainerRepository.findById(1L);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void test_020_getAllTrainers() {
    FrontendActivityConstraint constraint = new FrontendActivityConstraint();
    constraint.setPageable(PageRequest.of(0, 10, ASC, "fullName"));
    constraint.setSoTrainerVisibilities(immutableSetOf(new SelectOption<>(I, EQ, PUBLIC)));
    constraint.setSoTrainerStatuses(immutableSetOf(new SelectOption<>(I, EQ, VERIFIED)));
    constraint.setSoActivityIds(immutableSetOf(new LongSelectOption(I, EQ, FITNESS_ACTIVITY_ID)));

    // Act
    ResponseEntity<?> response = activityRestController.getAllTrainers(constraint);

    // Assert
    assertEquals(HTTP_OK, response.getStatusCodeValue());
    Page<Trainer> trainerPage = (Page<Trainer>) response.getBody();
    assertNotNull(trainerPage);
    pageValidation(trainerPage);
    Trainer trainer = trainerPage.getData().get(0);
    assertEquals(1, trainer.getTrainerId().longValue());
    assertEquals(TRAINER_USER_ID, trainer.getTrainerUserId().longValue());
    assertFalse(trainer.getFullName().isEmpty());
  }

  @Test
  public void test_030_requestFitnessTrainee() {
    // Act
    ResponseEntity<?> response = activityRestController.requestFitnessTrainee(TRAINEE_EMAIL);

    // Assert
    assertEquals(HTTP_OK, response.getStatusCodeValue());
    TextInfoDto infoDto = (TextInfoDto) response.getBody();
    assertNotNull(infoDto);
    assertEquals(ACTIVITY_SUCCESS_SENT_TRAINEE_CONNECTION_REQUEST, infoDto.getCode());
    trainerConnectionsRepository.findByTrainer_IdAndTrainee_Id(getUserId(), TRAINEE_USER_ID);
  }

  @Test
  public void test_040_getMyConnectionsForTrainer() {
    validateConnections(activityRestController.getMyConnections(0, 10));
  }

  @Test
  public void test_050_getMyConnectionsForTrainee() {
    setUser(TRAINEE_USER_ID);

    validateConnections(activityRestController.getMyConnections(0, 10));
  }

  private static void pageValidation(Page<?> page) {
    assertEquals(1, page.getCount().intValue());
    assertEquals(1, page.getData().size());
  }

  @SuppressWarnings("unchecked")
  private static void validateConnections(ResponseEntity<?> response) {
    assertEquals(HTTP_OK, response.getStatusCodeValue());
    Page<TrainerConnections> connections = (Page<TrainerConnections>) response.getBody();
    assertNotNull(connections);
    pageValidation(connections);
    connections.getData().forEach(c -> {
      assertNotNull(c.getConnectionId());
      assertNotNull(c.getTrainerId());
      assertNotNull(c.getTrainerUserId());
      assertFalse(c.getTrainerFullName().isEmpty());
      assertNotNull(c.getTraineeUserId());
      assertFalse(c.getTraineeFullName().isEmpty());
    });
  }
}
