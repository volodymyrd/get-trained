package online.gettrained.backend.services.notif;

import java.util.Map;

/**
 * Template processor.
 */
public interface TemplateProcessor {

  String process(String template, final Map<String, String> param);
}
