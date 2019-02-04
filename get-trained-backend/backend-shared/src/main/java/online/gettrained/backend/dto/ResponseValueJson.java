package online.gettrained.backend.dto;

import java.io.Serializable;

/**
 * Response JSON with one value field.
 */
public final class ResponseValueJson<T> implements Serializable {

  private final T value;

  public ResponseValueJson(T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }
}
