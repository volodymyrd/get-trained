package online.gettrained.backend.services.notif;

import java.util.HashMap;
import java.util.List;
import online.gettrained.backend.utils.CommonUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class TemplateProcessorImplTest {

  @Test
  public void testProcess() throws Exception {
    Assert.assertEquals("User Volodymyr with volodymyrdotsenko@gmail.com",
        new TemplateProcessorImpl().process("User ${firstName} with ${email}",
            new HashMap<String, String>() {{
              put("firstName", "Volodymyr");
              put("email", "volodymyrdotsenko@gmail.com");
            }}));
  }

  @Test
  public void testFindTemplateVariables() throws Exception {
    List<String> variables = new TemplateProcessorImpl()
        .findTemplateVariables("User ${firstName} with ${email}");
    Assert.assertEquals(CommonUtils.immutableListOf("firstName", "email"), variables);
  }

  @Test
  public void testProcessWithNullValue() throws Exception {
    Assert.assertEquals("User  with volodymyrdotsenko@gmail.com",
        new TemplateProcessorImpl().process("User ${firstName} with ${email}",
            new HashMap<String, String>() {{
              put("firstName", null);
              put("email", "volodymyrdotsenko@gmail.com");
            }}));
  }

}
