package online.gettrained.backend.json_serializers;

import static com.fasterxml.jackson.core.JsonToken.END_ARRAY;
import static com.fasterxml.jackson.core.JsonToken.START_ARRAY;
import static online.gettrained.backend.utils.DateUtils.getLongDateFormat;
import static online.gettrained.backend.utils.DateUtils.getLongerDateFormat;
import static online.gettrained.backend.utils.DateUtils.getShortDateFormat;
import static online.gettrained.backend.utils.DateUtils.getShortestDateFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import online.gettrained.backend.constraints.DateSelectOption;
import online.gettrained.backend.constraints.SelectOption.Option;
import online.gettrained.backend.constraints.SelectOption.Sign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON deserializer for {@link Set<DateSelectOption>} object.
 */
public class DateSelectOptionJsonDeserializer extends StdDeserializer<Set<DateSelectOption>> {

  private static final long serialVersionUID = -8607344747925567108L;

  private static final Logger LOG = LoggerFactory.getLogger(DateSelectOptionJsonDeserializer.class);

  public DateSelectOptionJsonDeserializer() {
    super(Set.class);
  }

  @Override
  public Set<DateSelectOption> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    JsonToken currentToken;
    Sign sign = null;
    Option option = null;
    Date low = null;
    Date high = null;
    Set<DateSelectOption> object = new HashSet<>();
    if (p.getCurrentToken() == START_ARRAY) {
      while ((currentToken = p.nextValue()) != END_ARRAY) {
        switch (currentToken) {
          case START_OBJECT:
            sign = null;
            option = null;
            low = null;
            high = null;
            break;
          case END_OBJECT:
            object.add(new DateSelectOption(sign, option, low, high));
            break;
          case VALUE_STRING:
            switch (p.getCurrentName()) {
              case "sign":
                sign = Sign.valueOf(p.getText());
                break;
              case "option":
                option = Option.valueOf(p.getText());
                break;
              case "low":
                low = parseStringToDate(p.getText());
                break;
              case "high":
                high = parseStringToDate(p.getText());
                break;
              default:
                break;
            }
            break;
          case VALUE_NUMBER_INT:
            switch (p.getCurrentName()) {
              case "low":
                low = new Date(Long.parseLong(p.getText()));
                break;
              case "high":
                high = new Date(Long.parseLong(p.getText()));
                break;
              default:
                break;
            }
          default:
            break;
        }
      }
    }
    return object;
  }

  private Date parseStringToDate(String date) {
    try {
      int len = date.length();
      switch (len) {
        // dd.MM.yy
        case 8:
          return getShortestDateFormat().parse(date);
        // dd.MM.yyyy
        case 10:
          return getShortDateFormat().parse(date);
        // dd.MM.yyyy HH:mm
        case 16:
          return getLongerDateFormat().parse(date);
        // dd.MM.yyyy HH:mm:ss
        case 19:
          return getLongDateFormat().parse(date);
      }
    } catch (ParseException ex) {
      LOG.error("Error parsing string {} to date", date);
    }
    return null;
  }
}

