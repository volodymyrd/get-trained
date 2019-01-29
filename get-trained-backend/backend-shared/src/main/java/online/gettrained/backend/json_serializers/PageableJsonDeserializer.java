package online.gettrained.backend.json_serializers;

import static com.fasterxml.jackson.core.JsonToken.END_OBJECT;
import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * JSON deserializer for {@link Pageable} object.
 */
public class PageableJsonDeserializer extends StdDeserializer<Pageable> {

  private static final long serialVersionUID = -8390489593088181548L;

  protected PageableJsonDeserializer() {
    super(PageRequest.class);
  }

  @Override
  public Pageable deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    JsonToken currentToken;
    int page = 0;
    int size = 0;
    List<Order> orders = new ArrayList<>();
    if (p.getCurrentToken() == START_OBJECT) {
      while ((currentToken = p.nextValue()) != END_OBJECT) {
        switch (currentToken) {
          case START_OBJECT:
            if (p.getCurrentName().equals("sort")) {
              String name = "";
              String direction = "";
              while ((currentToken = p.nextValue()) != END_OBJECT) {
                switch (currentToken) {
                  case VALUE_STRING:
                    switch (p.getCurrentName()) {
                      case "name":
                        name = p.getText();
                        break;
                      case "direction":
                        direction = p.getText();
                      default:
                        break;
                    }
                    break;
                  default:
                    break;
                }
              }
              if (!name.isEmpty() && direction.isEmpty()) {
                orders.add(new Order(Direction.ASC, name));
              } else if (!name.isEmpty()) {
                orders.add(new Order(Direction.valueOf(direction), name));
              }
            }
            break;
          case VALUE_NUMBER_INT:
            switch (p.getCurrentName()) {
              case "page":
                page = p.getIntValue();
                break;
              case "size":
                size = p.getIntValue();
              default:
                break;
            }
            break;
          default:
            break;
        }
      }
    }
    if (orders.isEmpty()) {
      return new PageRequest(page, size);
    } else {
      return new PageRequest(page, size, new Sort(orders));
    }
  }
}
