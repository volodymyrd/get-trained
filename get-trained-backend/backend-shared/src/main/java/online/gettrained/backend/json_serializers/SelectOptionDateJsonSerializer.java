package online.gettrained.backend.json_serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import online.gettrained.backend.constraints.SelectOption;

/**
 * Base class for serializer of {@link Set<SelectOption<Date>>} object.
 */
public abstract class SelectOptionDateJsonSerializer extends
    JsonSerializer<Set<SelectOption<Date>>> {

  @Override
  public void serialize(
      Set<SelectOption<Date>> value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value != null) {
      gen.writeStartArray();
      for (SelectOption<Date> selectOption : value) {
        gen.writeStartObject();
        gen.writeObjectField("sign", selectOption.getSign());
        gen.writeObjectField("option", selectOption.getOption());
        gen.writeObjectField("low",
            selectOption.getLow() != null ? convertDateToString(selectOption.getLow()) : null);
        gen.writeObjectField("high",
            selectOption.getHigh() != null ? convertDateToString(selectOption.getHigh()) : null);
        gen.writeEndObject();
      }
      gen.writeEndArray();
    }
  }

  protected abstract String convertDateToString(Date date);
}
