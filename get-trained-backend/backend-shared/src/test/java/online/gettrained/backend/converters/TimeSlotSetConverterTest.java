package online.gettrained.backend.converters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableSet;
import java.time.LocalTime;
import online.gettrained.backend.domain.activities.TimeSlot;
import org.junit.Test;

/**
 * Tests for {@link TimeSlotSetConverter}.
 */
public class TimeSlotSetConverterTest {

  @Test
  public void testConvertToDatabaseColumn() {
    assertEquals(
        "[{\"start\":{\"hour\":10,\"minute\":15,\"second\":0,\"nano\":0},"
            + "\"end\":{\"hour\":11,\"minute\":15,\"second\":0,\"nano\":0}},"
            + "{\"start\":{\"hour\":22,\"minute\":0,\"second\":0,\"nano\":0},"
            + "\"end\":{\"hour\":23,\"minute\":30,\"second\":0,\"nano\":0}}]",
        new TimeSlotSetConverter().convertToDatabaseColumn(ImmutableSet.of(
            new TimeSlot(LocalTime.of(10, 15), LocalTime.of(11, 15)),
            new TimeSlot(LocalTime.of(22, 00), LocalTime.of(23, 30))
        )));
  }

  @Test
  public void testConvertToEntityAttribute() {
    assertThat(new TimeSlotSetConverter()
            .convertToEntityAttribute(
                "[{\"start\":{\"hour\":10,\"minute\":15},\"end\":{\"hour\":11,\"minute\":15}},"
                    + "{\"start\":{\"hour\":22,\"minute\":0},\"end\":{\"hour\":23,\"minute\":31}}]"),
        is(ImmutableSet.of(
            new TimeSlot(LocalTime.of(10, 15), LocalTime.of(11, 15)),
            new TimeSlot(LocalTime.of(22, 00), LocalTime.of(23, 31)))));
  }
}
