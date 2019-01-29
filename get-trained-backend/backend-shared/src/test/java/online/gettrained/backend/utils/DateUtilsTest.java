package online.gettrained.backend.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

/**
 * Tests for {@link DateUtils}.
 */
public class DateUtilsTest {

  @Test
  public void testCurrentDayWithoutTime() {
    Date date = DateUtils.currentDayWithoutTime();
    assertNotNull(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    assertTrue(calendar.get(Calendar.DAY_OF_YEAR) > 0);
    assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY));
    assertEquals(0, calendar.get(Calendar.MINUTE));
    assertEquals(0, calendar.get(Calendar.SECOND));
    assertEquals(0, calendar.get(Calendar.MILLISECOND));
  }

  @Test
  public void testDateOfBeginningOfDate() throws ParseException {
    Date date = DateUtils.getLongDateFormat().parse("14.10.2019 12:32:15");
    assertEquals(
      DateUtils.getLongDateFormat().parse("14.10.2019 00:00:00"),
      DateUtils.dateOfBeginningOfDay(date));
  }

  @Test
  public void testDateBeginningThisWorkingWeek() throws ParseException {
    Date date = DateUtils.getLongDateFormat().parse("14.10.2018 12:32:15");
    assertEquals(
      DateUtils.getLongDateFormat().parse("08.10.2018 00:00:00"),
      DateUtils.dateOfBeginningThisWorkingWeek(date));
  }

  @Test
  public void testDateBeginningThisWorkingWeekIsBetweenMonth() throws ParseException {
    Date date = DateUtils.getLongDateFormat().parse("02.05.2018 12:32:15");
    assertEquals(
      DateUtils.getLongDateFormat().parse("30.04.2018 00:00:00"),
      DateUtils.dateOfBeginningThisWorkingWeek(date));
  }

  @Test
  public void testDateBeginningThisWorkingWeekIsBetweenYear() throws ParseException {
    Date date = DateUtils.getLongDateFormat().parse("01.01.2017 12:32:15");
    assertEquals(
      DateUtils.getLongDateFormat().parse("26.12.2016 00:00:00"),
      DateUtils.dateOfBeginningThisWorkingWeek(date));
  }

  @Test
  public void testDateOfBeginningOfThisMonth() throws ParseException {
    Date date = DateUtils.getLongDateFormat().parse("14.10.2019 12:32:15");
    assertEquals(
      DateUtils.getLongDateFormat().parse("01.10.2019 00:00:00"),
      DateUtils.dateOfBeginningOfThisMonth(date));
  }

  @Test
  public void testPlusDays() throws ParseException {
    assertEquals(
      "27.07.18",
      DateUtils.getShortestDateFormat()
        .format(DateUtils.plusDays(DateUtils.getShortestDateFormat().parse("17.07.18"), 10)));
  }

  @Test
  public void testCurrentDateForTimeZone() {
    Date date = new Date();
    System.out.println(DateUtils.convert(date, DateUtils.DEFAULT_TIME_ZONE));
    System.out.println(DateUtils.convert(date, "UTC"));
  }

  @Test
  public void testHoursBetweenTwoDays() throws ParseException {
    Date dateTo = DateUtils.getLongDateFormat().parse("02.01.2019 12:32:15");
    Date dateFrom = DateUtils.getLongDateFormat().parse("30.12.2018 10:32:15");
    assertEquals(74, DateUtils.hoursBetweenTwoDays(dateFrom, dateTo));
  }
}
