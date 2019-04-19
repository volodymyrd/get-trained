package online.gettrained.frontend.web.activities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import java.util.List;
import online.gettrained.backend.domain.activities.TimeSlot;
import online.gettrained.backend.domain.activities.TrainerConnectionSchedule;
import online.gettrained.frontend.BaseIntegrationTest;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for {@link CalendarRestController}.
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalendarRestControllerTest extends BaseIntegrationTest {

  @Autowired
  private CalendarRestController calendarRestController;

  private final static long CONNECTION_ID = -1;

  private final static List<TimeSlot> MONDAY = ImmutableList.of(new TimeSlot("09:00", "10:30"));
  private final static List<TimeSlot> MONDAY1 = ImmutableList.of();
  private final static List<TimeSlot> TUESDAY = ImmutableList.of(new TimeSlot("18:30", "20:15"));
  private final static List<TimeSlot> TUESDAY1 = ImmutableList.of(new TimeSlot("18:00", "20:20"));
  private final static List<TimeSlot> WEDNESDAY = ImmutableList.of(new TimeSlot("10:20", "12:30"));
  private final static List<TimeSlot> WEDNESDAY1 = ImmutableList
      .of(new TimeSlot("10:20", "12:30"), new TimeSlot("18:30", "20:15"));
  private final static List<TimeSlot> THURSDAY = ImmutableList
      .of(new TimeSlot("08:30", "09:30"), new TimeSlot("18:00", "20:00"));
  private final static List<TimeSlot> THURSDAY1 = ImmutableList
      .of(new TimeSlot("08:30", "09:30"), new TimeSlot("17:00", "19:00"));
  private final static List<TimeSlot> FRIDAY = ImmutableList
      .of(new TimeSlot("09:00", "10:30"), new TimeSlot("18:00", "20:00"));
  private final static List<TimeSlot> FRIDAY1 = ImmutableList
      .of(new TimeSlot("09:00", "10:30"), new TimeSlot("18:00", "20:00"));
  private final static List<TimeSlot> SATURDAY = ImmutableList.of(new TimeSlot("17:00", "19:00"));
  private final static List<TimeSlot> SATURDAY1 = ImmutableList.of(new TimeSlot("17:00", "19:00"));
  private final static List<TimeSlot> SUNDAY = ImmutableList
      .of(new TimeSlot("09:00", "10:30"), new TimeSlot("21:00", "23:00"));
  private final static List<TimeSlot> SUNDAY1 = ImmutableList
      .of(new TimeSlot("21:00", "23:00"));

  @BeforeClass
  public static void setUpEnv() {
    BaseIntegrationTest.setUpEnv();
    System.setProperty("testEnvironmentCalendar", "TEST-CALENDAR");
  }

  @Test
  public void test_001_getScheduleByTrainer() {
    setUser(TRAINER_USER_ID);
    actAndValidateGetEmptyScheduleBy();
  }

  @Test
  public void test_005_getScheduleByTrainee() {
    setUser(TRAINEE_USER_ID);
    actAndValidateGetEmptyScheduleBy();
  }

  private void actAndValidateGetEmptyScheduleBy() {
    ResponseEntity<?> response = calendarRestController.getSchedule(CONNECTION_ID);

    assertEquals(HTTP_OK, response.getStatusCodeValue());
    TrainerConnectionSchedule schedule = (TrainerConnectionSchedule) response.getBody();
    assertNotNull(schedule);
    assertTrue(schedule.getMonday().isEmpty());
    assertTrue(schedule.getTuesday().isEmpty());
    assertTrue(schedule.getWednesday().isEmpty());
    assertTrue(schedule.getThursday().isEmpty());
    assertTrue(schedule.getFriday().isEmpty());
    assertTrue(schedule.getSaturday().isEmpty());
    assertTrue(schedule.getSunday().isEmpty());
  }

  @Test
  public void test_010_addSchedule() {
    setUser(TRAINER_USER_ID);

    validateSchedule(
        calendarRestController.saveSchedule(getSchedule(
            TRAINEE_USER_ID,
            MONDAY,
            TUESDAY,
            WEDNESDAY,
            THURSDAY,
            FRIDAY,
            SATURDAY,
            SUNDAY)),
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY);
  }

  @Test
  public void test_020_getScheduleByTrainer() {
    setUser(TRAINER_USER_ID);
    validateSchedule(
        calendarRestController.getSchedule(CONNECTION_ID),
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY);
  }

  @Test
  public void test_030_updateSchedule() {
    setUser(TRAINER_USER_ID);

    validateSchedule(
        calendarRestController.saveSchedule(getSchedule(
            TRAINEE_USER_ID,
            MONDAY1,
            TUESDAY1,
            WEDNESDAY1,
            THURSDAY1,
            FRIDAY1,
            SATURDAY1,
            SUNDAY1)),
        MONDAY1,
        TUESDAY1,
        WEDNESDAY1,
        THURSDAY1,
        FRIDAY1,
        SATURDAY1,
        SUNDAY1);
  }

  @Test
  public void test_035_getScheduleByTrainer() {
    setUser(TRAINER_USER_ID);

    validateSchedule(
        calendarRestController.getSchedule(CONNECTION_ID),
        MONDAY1,
        TUESDAY1,
        WEDNESDAY1,
        THURSDAY1,
        FRIDAY1,
        SATURDAY1,
        SUNDAY1);
  }

  private TrainerConnectionSchedule getSchedule(
      long traineeUserId,
      List<TimeSlot> monday,
      List<TimeSlot> tuesday,
      List<TimeSlot> wednesday,
      List<TimeSlot> thursday,
      List<TimeSlot> friday,
      List<TimeSlot> saturday,
      List<TimeSlot> sunday) {
    TrainerConnectionSchedule schedule = new TrainerConnectionSchedule();
    schedule.setTraineeUserId(traineeUserId);
    schedule.setMonday(monday);
    schedule.setTuesday(tuesday);
    schedule.setWednesday(wednesday);
    schedule.setThursday(thursday);
    schedule.setFriday(friday);
    schedule.setSaturday(saturday);
    schedule.setSunday(sunday);
    return schedule;
  }

  private static void validateSchedule(
      ResponseEntity<?> response,
      List<TimeSlot> monday,
      List<TimeSlot> tuesday,
      List<TimeSlot> wednesday,
      List<TimeSlot> thursday,
      List<TimeSlot> friday,
      List<TimeSlot> saturday,
      List<TimeSlot> sunday) {
    assertEquals(HTTP_OK, response.getStatusCodeValue());
    TrainerConnectionSchedule schedule = (TrainerConnectionSchedule) response.getBody();
    assertNotNull(schedule);
    assertThat(monday, is(schedule.getMonday()));
    assertThat(tuesday, is(schedule.getTuesday()));
    assertThat(wednesday, is(schedule.getWednesday()));
    assertThat(thursday, is(schedule.getThursday()));
    assertThat(friday, is(schedule.getFriday()));
    assertThat(saturday, is(schedule.getSaturday()));
    assertThat(sunday, is(schedule.getSunday()));
  }
}
