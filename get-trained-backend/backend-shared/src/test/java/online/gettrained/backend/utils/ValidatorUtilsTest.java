package online.gettrained.backend.utils;

import static online.gettrained.backend.utils.ValidatorUtils.isEmail;
import static online.gettrained.backend.utils.ValidatorUtils.isPhoneNumber;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for {@link ValidatorUtils}.
 */
public class ValidatorUtilsTest {

  @Test
  public void testValidateEmailSuccess1() {
    assertTrue(isEmail("volodymyrdotsenko@gmail.com"));
  }

  @Test
  public void testValidateEmailSuccess2() {
    assertTrue(isEmail("\"Abc\\@def\"@example.com"));
  }

  @Test
  public void testValidateEmailSuccess3() {
    assertTrue(isEmail("\"Fred Bloggs\"@example.com"));
  }

  @Test
  public void testValidateEmailSuccess4() {
    assertTrue(isEmail("volodymyrDotsenko@gmail.com"));
  }

  @Test
  public void testValidateEmailSuccess5() {
    assertTrue(isEmail("\"Joe\\\\Blow\"@example.com"));
  }

  @Test
  public void testValidateEmailSuccess6() {
    assertTrue(isEmail("\"Abc@def\"@example.com"));
  }

  @Test
  public void testValidateEmailSuccess7() {
    assertTrue(isEmail("customer/department=shipping@example.com"));
  }

  @Test
  public void testValidateEmailSuccess8() {
    assertTrue(isEmail("$A12345@example.com"));
  }

  @Test
  public void testValidateEmailSuccess9() {
    assertTrue(isEmail("!def!xyz%abc@example.com"));
  }

  @Test
  public void testValidateEmailSuccess10() {
    assertTrue(isEmail("_somename@example.com"));
  }

  @Test
  public void testValidateEmailSuccess11() {
    assertTrue(isEmail("\"matteo(this is a comment).corti\"@example.com"));
  }

  @Test
  public void testValidateEmailSuccess12() {
    assertTrue(isEmail("root@[127.0.0.1]"));
  }

  @Test
  public void testValidateEmailSuccess13() {
    assertTrue(isEmail("webmaster@müller.de"));
  }

  @Test
  public void testValidateEmailSuccess14() {
    assertTrue(isEmail("matteo@78.47.122.114"));
  }

  @Test
  public void testValidateEmailSuccess15() {
    assertTrue(isEmail("trainer@get-trained.online"));
  }

  @Test
  public void testValidateWrongEmail() {
    assertFalse(isEmail("volodymyrdotsenko"));
  }

  @Test
  public void testUaValidPhoneNumber() {
    assertTrue(isPhoneNumber("UA", "+380675780575"));
  }
}
