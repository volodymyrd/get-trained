package online.gettrained.backend.services.notif;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TemplateProcessorImpl implements TemplateProcessor {

  private final static String DEFAULT_VALUE = "undefined";
  private final static String NULL_OR_EMPTY_VALUE = "";
  private final static Pattern p = Pattern.compile("\\$\\{" + "(.*?)" + "}");

  @Override
  public String process(String template, final Map<String, String> param) {
    if (template == null || template.length() == 0 || param == null) {
      return template;
    }
    List<String> templateVariables = findTemplateVariables(template);
    for (String variable : templateVariables) {
      String value = param.getOrDefault(variable, DEFAULT_VALUE);
      if (value == null || value.isEmpty()) {
        value = NULL_OR_EMPTY_VALUE;
      }
      template = template.replace("${" + variable + "}", value);
    }
    return template;
  }

  List<String> findTemplateVariables(String text) {
    List<String> tokens = new ArrayList<>();
    Matcher m = p.matcher(text);
    while (m.find()) {
      String var = m.group(1);
      if (var != null && !var.contains(" ")) { // not expression
        tokens.add(var);
      }
    }
    return tokens;
  }
}
