package online.gettrained.backend.utils;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MONDAY;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

/**
 * Date utils
 */
public final class DateUtils {

  public static final String TIMEZONE_OFFSET_HEADER = "timezone-offset";

  public static final String UTC_TIME_ZONE = "UTC";
  public static final String DEFAULT_TIME_ZONE = "Europe/Kiev";

  private static final String FULL_DATE_FORMAT_FOR_FILE_NAME = "yyyy_MM_dd_HH_mm_ss_SSS";
  private static final String FULL_DATE_FORMAT_WITH_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final String FULL_DATE_FORMAT = "dd.MM.yyyy HH:mm:ss.SSS";
  public static final String LONG_DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
  public static final String LONGER_DATE_FORMAT = "dd.MM.yyyy HH:mm";
  public static final String SHORT_DATE_FORMAT = "dd.MM.yyyy";
  private static final String SHORTEST_DATE_FORMAT = "dd.MM.yy";
  public static final String BIG_QUERY_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
  private static final String DATABASE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  private DateUtils() {
  }

  public static DateFormat getFullDateFormatForFileName() {
    return new SimpleDateFormat(FULL_DATE_FORMAT_FOR_FILE_NAME);
  }

  public static DateFormat getFullDateFormatWithZone() {
    return new SimpleDateFormat(FULL_DATE_FORMAT_WITH_ZONE);
  }

  public static DateFormat getFullDateFormat() {
    return new SimpleDateFormat(FULL_DATE_FORMAT);
  }

  public static DateFormat getLongDateFormat() {
    return new SimpleDateFormat(LONG_DATE_FORMAT);
  }

  public static DateFormat getLongerDateFormat() {
    return new SimpleDateFormat(LONGER_DATE_FORMAT);
  }

  public static DateFormat getShortDateFormat() {
    return new SimpleDateFormat(SHORT_DATE_FORMAT);
  }

  public static DateFormat getShortestDateFormat() {
    return new SimpleDateFormat(SHORTEST_DATE_FORMAT);
  }

  public static DateFormat getDatabaseDateTimeFormat() {
    return new SimpleDateFormat(DATABASE_DATE_TIME_FORMAT);
  }

  private static ZoneId fromHourOffset(int timezoneOffset) {
    return ZoneId.ofOffset("GMT", ZoneOffset.ofHours(timezoneOffset));
  }

  public static Date convertFromDefaultTimeZone(Date date, Integer timezoneOffset) {
    if (timezoneOffset == null) {
      return date;
    }
    return convertFromDefaultTimeZone(date, fromHourOffset(timezoneOffset).getId());
  }

  public static Date convertFromDefaultTimeZone(Date date, String toTimeZone) {
    return convert(convertToLocalDateTime(date, toTimeZone), DEFAULT_TIME_ZONE);
  }

  public static Date convertToDefaultTimeZone(Date date, Integer timezoneOffset) {
    if (timezoneOffset == null) {
      return date;
    }
    return convertToDefaultTimeZone(date, fromHourOffset(timezoneOffset).getId());
  }

  private static Date convertToDefaultTimeZone(Date date, String fromTimeZone) {
    return convert(convertToLocalDateTime(date, DEFAULT_TIME_ZONE), fromTimeZone);
  }

  public static Date convert(Date date, String toTimeZone, String fromTimeZone) {
    return convert(convertToLocalDateTime(date, toTimeZone), fromTimeZone);
  }

  public static Date convert(Date date, String toTimeZone) {
    return convert(date, toTimeZone, ZoneId.systemDefault().toString());
  }

  private static LocalDate convertToLocalDate(Date date, String timeZone) {
    return date.toInstant().atZone(ZoneId.of(timeZone)).toLocalDate();
  }

  private static LocalDate convertToLocalDate(Date date) {
    return convertToLocalDate(date, ZoneId.systemDefault().toString());
  }

  private static LocalDateTime convertToLocalDateTime(Date date, String timeZone) {
    return date.toInstant().atZone(ZoneId.of(timeZone)).toLocalDateTime();
  }

  public static Date convert(LocalDate localDate) {
    return convert(localDate, ZoneId.systemDefault().toString());
  }

  public static Date convert(LocalDate localDate, String timeZone) {
    return Date.from(localDate.atStartOfDay(ZoneId.of(timeZone)).toInstant());
  }

  public static Date convert(LocalDateTime localDateTime, String timeZone) {
    return Date.from(localDateTime.atZone(ZoneId.of(timeZone)).toInstant());
  }

  public static Date plusDays(Date date, int n) {
    return convert(convertToLocalDate(date).plusDays(n));
  }

  public static Date plusHours(Date date, int n) {
    String defaultTimeZone = ZoneId.systemDefault().toString();
    return convert(convertToLocalDateTime(date, defaultTimeZone).plusHours(n), defaultTimeZone);
  }

  public static Date plusHours(Date date, int n, String timeZone) {
    return convert(convertToLocalDateTime(date, timeZone).plusHours(n), timeZone);
  }

  public static Date minusDays(Date date, int n) {
    return convert(convertToLocalDate(date).minusDays(n));
  }

  public static Date minusMonths(Date date, int n) {
    return convert(convertToLocalDate(date).minusMonths(n));
  }

  static Date currentDayWithoutTime() {
    return dateOfBeginningOfDay(new Date());
  }

  public static Date dateOfBeginningOfDay(Date date) {
    Calendar calendar = toCalendar(date);
    calendar.set(calendar.get(YEAR), calendar.get(MONTH), calendar.get(DATE), 0, 0, 0);
    calendar.set(MILLISECOND, 0);
    return calendar.getTime();
  }

  /**
   * Returns the beginning of the current working week that starts from {@link Calendar#MONDAY}.
   */
  public static Date dateOfBeginningThisWorkingWeek(Date date) {
    Calendar calendar = toCalendar(date);
    int daysFromMonday = calendar.get(DAY_OF_WEEK) - MONDAY;
    if (daysFromMonday == -1 /*It's SUNDAY*/) {
      daysFromMonday = 6;
    }
    return dateOfBeginningOfDay(minusDays(date, daysFromMonday));
  }

  public static Date dateOfBeginningOfThisMonth(Date date) {
    Calendar calendar = toCalendar(date);
    calendar.set(calendar.get(YEAR), calendar.get(MONTH), 1, 0, 0, 0);
    calendar.set(MILLISECOND, 0);
    return calendar.getTime();
  }

  public static int millisecondsToHours(long milliseconds) {
    return (int) (milliseconds / (1000 * 60 * 60));
  }

  public static int millisecondsToMinutes(long milliseconds) {
    return (int) (milliseconds / (1000 * 60));
  }

  public static int millisecondsToSeconds(long milliseconds) {
    return (int) (milliseconds / 1000);
  }

  public static long hoursBetweenTwoDays(Date fromDate, Date toDate) {
    long secs = (toDate.getTime() - fromDate.getTime()) / 1000;
    return secs / 3600;
  }

  private static Calendar toCalendar(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }
}
