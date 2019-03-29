package online.gettrained.backend.converters;

import static com.google.common.base.Strings.isNullOrEmpty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeConverter;
import online.gettrained.backend.domain.activities.TimeSlot;
import online.gettrained.backend.utils.CommonUtils;

/**
 * JPA converter of {@code string <--> Set<TimeSlot>}.
 */
public class TimeSlotSetConverter implements AttributeConverter<Set<TimeSlot>, String> {

  private final static ObjectMapper objectMapper = CommonUtils.getObjectMapper();

  @Override
  public String convertToDatabaseColumn(Set<TimeSlot> slots) {
    try {
      return objectMapper.writeValueAsString(slots);
    } catch (JsonProcessingException ex) {
      return null;
    }
  }

  @Override
  public Set<TimeSlot> convertToEntityAttribute(String slots) {
    if (!isNullOrEmpty(slots)) {
      try {
        return objectMapper.readValue(slots, new TypeReference<Set<TimeSlot>>() {
        });
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
    return new HashSet<>();
  }
}
