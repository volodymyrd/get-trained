package online.gettrained.backend.services.localization;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import online.gettrained.backend.constraints.frontend.localization.FrontendLocalConstraint;
import online.gettrained.backend.domain.localization.City;
import online.gettrained.backend.domain.localization.Country;
import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.localization.Localization;
import online.gettrained.backend.domain.localization.Settings;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.dto.localization.LocalExportDto;

/**
 * Localization service
 */
public interface LocalizationService {

  Map<String, String> findAllLanguagesWithLocalsByLang(String langCode);

  Map<String, Country> findAllCountriesWithLocalsByLang(String langCode);

  Optional<Country> findCountryByCode(String code);

  Optional<Language> findLangByCode(String code);

  Optional<Language> findSupportedLangByCode(String code);

  Optional<Language> findDefaultLang();

  List<Language> findAllLang();

  List<Language> findAllSupportedLang();

  Optional<Localization> findLocalByKeyAndLang(String key, Language language);

  Optional<Localization> findLocalByKeyAndLang(String key, String langCode);

  String getLocalTextByKeyAndLangOrUseDefault(String key, Language language, String defaultValue,
      Object... args);

  List<Localization> findLocalByLangAndModule(Language language, ELocalModule module);

  List<Settings> findAllSettingsByType(Settings.Type type);

  Optional<Settings> findSettingByKey(String key);

  Map<Long, String> findAllCitiesByCountryCodeAndLangWithLocals(
      String countryCode, String langCode);

  Optional<City> findCityByIdAndCountryCode(Long cityId, String countryCode);

  Optional<City> findCityByNameAndCountryCode(String cityName, String countryCode);

  Page<Localization> findAllLocalizationsByConstraint(FrontendLocalConstraint constraint);

  Localization save(Localization localization);

  void delete(Set<Long> ids);

  LocalExportDto export();
}
