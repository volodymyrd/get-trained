package online.gettrained.frontend.web.activities;

import static online.gettrained.backend.messages.TextCode.ACTIVITY_PROFILE_SUCCESS_REMOVE_FITNESS_PROFILE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.data.domain.Sort.Direction.DESC;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import online.gettrained.backend.constraints.frontend.activities.FrontendTraineeProfileConstraint;
import online.gettrained.backend.domain.activities.FitnessTraineeProfile;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.profile.Profile.Gender;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.dto.TextInfoDto;
import online.gettrained.backend.repositories.activities.FitnessTraineeProfileRepository;
import online.gettrained.frontend.BaseIntegrationTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for {@link FitnessTraineeProfileRestController}.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FitnessTraineeProfileRestControllerTest extends BaseIntegrationTest {

  @Autowired
  private ActivityRestController activityRestController;
  @Autowired
  private FitnessTraineeProfileRestController fitnessTraineeProfileRestController;
  @Autowired
  private FitnessTraineeProfileRepository fitnessTraineeProfileRepository;

  @Test
  public void test_001_addFitnessTrainer() {
    setUser(TRAINER_USER_ID);

    activityRestController.addFitnessTrainer();
    activityRestController.requestFitnessTrainee(TRAINEE_EMAIL);

    setUser(TRAINEE_USER_ID);
    activityRestController.acceptConnection(1);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void test_005_getGenders() {
    // Act
    ResponseEntity<?> response = fitnessTraineeProfileRestController.getGenders();

    // Assert
    Map<String, String> genders = (Map<String, String>) response.getBody();
    assertNotNull(genders);
    assertThat(
        ImmutableMap.of(
            "MALE", "MALE", "FEMALE", "FEMALE", "OTHER", "OTHER", "UNSPECIFIED", "UNSPECIFIED"),
        is(genders));
  }

  @Test
  public void test_010_getProfile() {
    setUser(TRAINER_USER_ID);

    // Act
    ResponseEntity<?> response = fitnessTraineeProfileRestController
        .getTraineeProfile(TRAINEE_USER_ID);

    // Assert
    Profile profile = (Profile) response.getBody();
    assertNotNull(profile);
    assertNull(profile.getBirthdayStr());
    assertNull(profile.getGender());
  }

  @Test
  public void test_015_updateProfile() {
    setUser(TRAINER_USER_ID);

    String birthday = "17.07.1974";
    Profile.Gender mail = Gender.MALE;
    Profile profile = new Profile();
    profile.setUserId(TRAINEE_USER_ID);
    profile.setBirthdayStr(birthday);
    profile.setGender(mail);

    // Act
    ResponseEntity<?> response = fitnessTraineeProfileRestController.updateTraineeProfile(profile);

    // Assert
    profile = (Profile) response.getBody();
    assertNotNull(profile);
    assertEquals(birthday, profile.getBirthdayStr());
    assertEquals(mail, profile.getGender());
  }

  @Test
  public void test_020_getProfile() {
    setUser(TRAINER_USER_ID);

    // Act
    ResponseEntity<?> response = fitnessTraineeProfileRestController
        .getTraineeProfile(TRAINEE_USER_ID);

    // Assert
    Profile profile = (Profile) response.getBody();
    assertNotNull(profile);
    assertEquals("17.07.1974", profile.getBirthdayStr());
    assertEquals(Gender.MALE, profile.getGender());
  }

  @Test
  public void test_030_addFitnessTraineeProfile() {
    setUser(TRAINER_USER_ID);

    String measureDate = "16.03.2019";
    FitnessTraineeProfile profile = new FitnessTraineeProfile();
    profile.setTraineeUserId(TRAINEE_USER_ID);
    profile.setMeasure(measureDate);

    // Act
    ResponseEntity<?> response = fitnessTraineeProfileRestController
        .saveFitnessTraineeProfile(profile);

    // Assert
    profile = (FitnessTraineeProfile) response.getBody();
    assertNotNull(profile);
    assertNotNull(profile.getTraineeProfileId());
    assertNotNull(profile.getTraineeUserId());
    assertEquals(measureDate, profile.getMeasure());
    assertEquals(0.0, profile.getBiceps(), 0.001);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void test_050_getAllTraineeFitnessProfiles() {
    setUser(TRAINER_USER_ID);

    FrontendTraineeProfileConstraint constraint = new FrontendTraineeProfileConstraint();
    constraint.setPageable(PageRequest.of(0, 10, DESC, "measure"));
    constraint.setConnectionId(1);
    constraint.setTraineeUserId(TRAINEE_USER_ID);

    // Act
    ResponseEntity<?> response = fitnessTraineeProfileRestController
        .getAllTraineeProfiles(constraint);

    // Assert
    Page<FitnessTraineeProfile> page = (Page<FitnessTraineeProfile>) response.getBody();
    assertNotNull(page);
    assertEquals(1, page.getCount().longValue());
    assertEquals(1, page.getData().size());
    page.getData().forEach(e -> {
      assertNotNull(e.getTraineeProfileId());
      assertNotNull(e.getConnectionId());
      assertEquals(TRAINEE_USER_ID, e.getTraineeUserId().longValue());
      assertEquals("16.03.2019", e.getMeasure());
    });
  }

  @Test
  public void test_060_updateFitnessTraineeProfile() {
    setUser(TRAINER_USER_ID);

    String measureDate = "17.03.2019";
    FitnessTraineeProfile profile = new FitnessTraineeProfile();
    profile.setTraineeProfileId(1L);
    profile.setTraineeUserId(TRAINEE_USER_ID);
    profile.setMeasure(measureDate);
    profile.setBiceps(50);

    // Act
    ResponseEntity<?> response = fitnessTraineeProfileRestController
        .saveFitnessTraineeProfile(profile);

    // Assert
    profile = (FitnessTraineeProfile) response.getBody();
    assertNotNull(profile);
    assertNotNull(profile.getTraineeProfileId());
    assertNotNull(profile.getTraineeUserId());
    assertEquals(measureDate, profile.getMeasure());
    assertEquals(50.0, profile.getBiceps(), 0.001);
  }

  @Test
  public void test_070_getTraineeFitnessProfile() {
    setUser(TRAINER_USER_ID);

    // Act
    ResponseEntity<?> response = fitnessTraineeProfileRestController
        .getTraineeFitnessProfile(1, TRAINEE_USER_ID);

    // Assert
    FitnessTraineeProfile profile = (FitnessTraineeProfile) response.getBody();
    assertNotNull(profile);
    assertNotNull(profile.getTraineeProfileId());
    assertNotNull(profile.getConnectionId());
    assertEquals(TRAINEE_USER_ID, profile.getTraineeUserId().longValue());
    assertEquals("17.03.2019", profile.getMeasure());
    assertEquals(50.0, profile.getBiceps(), 0.001);
  }

  @Test
  public void test_080_deleteTraineeProfile() {
    setUser(TRAINER_USER_ID);

    // Act
    ResponseEntity<?> response = fitnessTraineeProfileRestController
        .deleteTraineeFitnessProfile(1, TRAINEE_USER_ID);

    // Assert
    TextInfoDto textInfoDto = (TextInfoDto) response.getBody();
    assertNotNull(textInfoDto);
    assertEquals(ACTIVITY_PROFILE_SUCCESS_REMOVE_FITNESS_PROFILE, textInfoDto.getCode());
    assertEquals(0, fitnessTraineeProfileRepository.findAll().size());
  }
}
