package online.gettrained.backend.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 */
public class SecurityUtilsTest {

  @Test
  public void testEncodeAndDecode() throws Exception {
    String textToEncode = "simple text to encode!";
    String[] result = SecurityUtils.rsaEncrypt(textToEncode);
    assertEquals(textToEncode, SecurityUtils.rsaDecrypt(result[0], result[1]));
  }
}
