package online.gettrained.frontend.web;

import static java.util.stream.Collectors.toMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import online.gettrained.backend.constraints.SelectOption;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.services.localization.LocalizationService;
import online.gettrained.backend.utils.CommonUtils;
import online.gettrained.backend.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

  private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

  public static Language getLanguage(
      String lang, HttpServletRequest request, LocalizationService localizationService) {
    return getLanguage(lang, request, false, localizationService);
  }

  public static Language getLanguage(
      String lang,
      HttpServletRequest request,
      boolean fromHttpRequest,
      LocalizationService localizationService) {
    User user = SecurityUtils.getCurrentUser();
    if (fromHttpRequest || user == null || user.getLoginLang() == null) {
      if (lang == null || lang.isEmpty()) {
        lang = request.getLocale().getLanguage();
      }
    } else {
      return user.getLoginLang();
    }

    Optional<Language> language = localizationService.findSupportedLangByCode(lang);
    if (!language.isPresent()) {
      language = localizationService.findDefaultLang();
    }
    if (!language.isPresent()) {
      throw new IllegalArgumentException("Not found default language");
    }

    return language.get();
  }

  public static Language getLanguage(User user) {
    return user.getLoginLang();
  }

  public static Language getProfileLanguage(User user) {
    return user.getProfile().getLanguage();
  }

  public static <T> SelectOption<T> convertJsonStringToSelectOption(String json, Class<T> type)
      throws IOException {
    ObjectMapper mapper = CommonUtils.getObjectMapper();

    if (type.equals(Date.class)) {
      mapper.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
      return mapper.readValue(json, new TypeReference<SelectOption<Date>>() {
      });
    } else {
      return mapper.readValue(json, new TypeReference<SelectOption<String>>() {
      });
    }
  }

  public static String getSchemeAndHostAndPort(HttpServletRequest request) {
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();

    StringBuilder url = new StringBuilder();
    url.append(scheme).append("://").append(serverName);

    if (serverPort != 80 && serverPort != 443) {
      url.append(":").append(serverPort);
    }

    return url.append("/").toString();
  }

  public static <T extends Enum<T>> LinkedHashMap<String, String> enumToMapWithLocalSortedByLocalText(
      Class<T> enumClass,
      LocalizationService localizationService,
      Language language,
      String localPrefix) {

    return EnumSet.allOf(enumClass).stream().collect(
        toMap(
            Enum::name,
            e -> localizationService.getLocalTextByKeyAndLangOrUseDefault(
                localPrefix + e.name().toLowerCase(),
                language,
                e.name())
        )
    ).entrySet().stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(toMap(
            Map.Entry::getKey, Map.Entry::getValue,
            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
  }

  public static <T extends Enum<T>> LinkedHashMap<String, String> enumToMapWithLocal(
      Class<T> enumClass,
      LocalizationService localizationService,
      Language language,
      String localPrefix) {

    return EnumSet.allOf(enumClass).stream().collect(
        toMap(
            Enum::name,
            e -> localizationService.getLocalTextByKeyAndLangOrUseDefault(
                localPrefix + e.name().toLowerCase(),
                language,
                e.name()),
            (oldValue, newValue) -> oldValue,
            LinkedHashMap::new
        )
    );
  }
}
