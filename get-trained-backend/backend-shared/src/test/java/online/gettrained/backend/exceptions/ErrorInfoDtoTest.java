package online.gettrained.backend.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for {@link ErrorInfoDto}.
 */
public class ErrorInfoDtoTest {

  @Test
  public void testToJSON() throws Exception {
    assertEquals("{\"code\":\"SOMETHING_WENT_WRONG\",\"message\":\"something went wrong\"}",
        new ErrorInfoDto(ErrorCode.SOMETHING_WENT_WRONG, "something went wrong").toJSON()
    );
  }
}
