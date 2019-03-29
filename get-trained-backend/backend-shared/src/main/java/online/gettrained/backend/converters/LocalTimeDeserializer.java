package online.gettrained.backend.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalTime;

/**
 * Deserializer for {@link LocalTime}.
 */
public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

  protected LocalTimeDeserializer() {
    super(LocalTime.class);
  }

  @Override
  public LocalTime deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    int hour = getValue(parser, "hour");
    int minute = getValue(parser, "minute");
    while(parser.nextToken() != JsonToken.END_OBJECT);
    return LocalTime.of(hour, minute);
  }

  private int getValue(JsonParser parser, String fieldName) throws IOException {
    JsonToken t = parser.nextToken();
    if (t == JsonToken.FIELD_NAME && parser.getValueAsString().equals(fieldName)) {
      t = parser.nextToken();
      if (t == JsonToken.VALUE_NUMBER_INT) {
        return parser.getIntValue();
      }
    }
    throw new RuntimeException("Unexpected token:" + t);
  }
}
