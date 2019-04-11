package online.gettrained.backend.domain.activities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalTime;
import java.util.Objects;
import online.gettrained.backend.converters.LocalTimeDeserializer;

/**
 * Represents a time interval for using in a JSON objects.
 */
public final class TimeSlot implements Comparable<TimeSlot> {

  @JsonDeserialize(using = LocalTimeDeserializer.class)
  private LocalTime start;
  @JsonDeserialize(using = LocalTimeDeserializer.class)
  private LocalTime end;

  public TimeSlot() {
  }

  public TimeSlot(LocalTime start, LocalTime end) {
    this.start = start;
    this.end = end;
  }

  public LocalTime getStart() {
    return start;
  }

  public void setStart(LocalTime start) {
    this.start = start;
  }

  public LocalTime getEnd() {
    return end;
  }

  public void setEnd(LocalTime end) {
    this.end = end;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TimeSlot)) {
      return false;
    }
    TimeSlot timeSlot = (TimeSlot) o;
    return start.equals(timeSlot.start) &&
        end.equals(timeSlot.end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end);
  }

  @Override
  public String toString() {
    return "TimeSlot{" +
        "start=" + start +
        ", end=" + end +
        '}';
  }

  @Override
  public int compareTo(TimeSlot o) {
    return this.start.compareTo(o.start);
  }
}
