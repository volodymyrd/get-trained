package online.gettrained.backend.services.localization;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import online.gettrained.backend.constraints.frontend.localization.FrontendLocalConstraint;
import online.gettrained.backend.domain.localization.City;
import online.gettrained.backend.domain.localization.Country;
import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.localization.Localization;
import online.gettrained.backend.domain.localization.Settings;
import online.gettrained.backend.domain.localization.Settings.Pk;
import online.gettrained.backend.domain.localization.Settings.Type;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.dto.localization.LocalDto;
import online.gettrained.backend.dto.localization.LocalExportDto;
import online.gettrained.backend.props.ApplicationCommonProperties;
import online.gettrained.backend.repositories.localization.CityDAO;
import online.gettrained.backend.repositories.localization.CityRepository;
import online.gettrained.backend.repositories.localization.CountryDAO;
import online.gettrained.backend.repositories.localization.CountryRepository;
import online.gettrained.backend.repositories.localization.LanguageDAO;
import online.gettrained.backend.repositories.localization.LanguageRepository;
import online.gettrained.backend.repositories.localization.LocalizationDAO;
import online.gettrained.backend.repositories.localization.LocalizationRepository;
import online.gettrained.backend.repositories.localization.SettingsRepository;
import online.gettrained.backend.services.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class LocalizationServiceImpl implements LocalizationService {

  private static final Logger LOG = LoggerFactory.getLogger(LocalizationServiceImpl.class);

  private final ApplicationContext applicationContext;
  private final ApplicationCommonProperties applicationCommonProperties;
  private final LanguageDAO languageDAO;
  private final CountryRepository countryRepository;
  private final CountryDAO countryDAO;
  private final LocalizationRepository localizationRepository;
  private final LocalizationDAO localizationDAO;
  private final LanguageRepository languageRepository;
  private final SettingsRepository settingsRepository;
  private final CityRepository cityRepository;
  private final CityDAO cityDAO;
  private final CacheService cacheService;

  public LocalizationServiceImpl(
      ApplicationContext applicationContext,
      ApplicationCommonProperties applicationCommonProperties,
      LanguageDAO languageDAO,
      CountryRepository countryRepository,
      CountryDAO countryDAO,
      LocalizationRepository localizationRepository,
      LocalizationDAO localizationDAO,
      LanguageRepository languageRepository,
      SettingsRepository settingsRepository,
      CityRepository cityRepository,
      CityDAO cityDAO,
      CacheService cacheService) {
    this.applicationContext = applicationContext;
    this.applicationCommonProperties = applicationCommonProperties;
    this.languageDAO = languageDAO;
    this.countryRepository = countryRepository;
    this.countryDAO = countryDAO;
    this.localizationRepository = localizationRepository;
    this.localizationDAO = localizationDAO;
    this.languageRepository = languageRepository;
    this.settingsRepository = settingsRepository;
    this.cityRepository = cityRepository;
    this.cityDAO = cityDAO;
    this.cacheService = cacheService;
  }

  @Override
  @Cacheable(cacheNames = "languagesWithLocals", key = "#langCode")
  public Map<String, String> findAllLanguagesWithLocalsByLang(String langCode) {
    Map<String, String> languages = new LinkedHashMap<>();
    for (Object[] values : languageDAO.findAllWithLocalsByLangCodeOrderByCode(langCode)) {
      languages.put((String) values[0], (String) values[1]);
    }
    return languages;
  }

  @Override
  @Cacheable(cacheNames = "countriesWithLocals", key = "#langCode")
  public Map<String, Country> findAllCountriesWithLocalsByLang(String langCode) {
    return countryDAO.findAllByLangCodeWithLocals(langCode)
        .stream()
        .collect(
            Collectors.toMap(
                c -> (String) c[0],
                c -> new Country((String) c[0], (String) c[1], (String) c[2])));
  }

  @Override
  @Cacheable(cacheNames = "country", key = "#code")
  public Optional<Country> findCountryByCode(String code) {
    return countryRepository.findById(code);
  }

  @Override
  @Cacheable(cacheNames = "language", key = "#code")
  public Optional<Language> findLangByCode(String code) {
    return languageRepository.findById(code);
  }

  @Override
  @Cacheable(cacheNames = "supportedLanguage", key = "#code")
  public Optional<Language> findSupportedLangByCode(String code) {
    return languageRepository.findSupportedByCode(code);
  }

  @Override
  @Cacheable(cacheNames = "defaultLanguage", key = "#root.methodName")
  public Optional<Language> findDefaultLang() {
    return languageRepository.findDefault();
  }

  @Override
  @Cacheable(cacheNames = "languages", key = "#root.methodName")
  public List<Language> findAllLang() {
    return languageRepository.findAll();
  }

  @Override
  @Cacheable(cacheNames = "supportedLanguages", key = "#root.methodName")
  public List<Language> findAllSupportedLang() {
    return languageRepository.findAllSupported();
  }

  @Override
  @Cacheable(cacheNames = "localForKeyAndLang", key = "{#key, #language.code}")
  public Optional<Localization> findLocalByKeyAndLang(String key, Language language) {
    return localizationRepository.findOneByKeyAndLang(key, language);
  }

  @Override
  public Optional<Localization> findLocalByKeyAndLang(String key, String langCode) {
    return localizationRepository.findByLocalKeyAndLang_Code(key, langCode);
  }

  @Override
  public String getLocalTextByKeyAndLangOrUseDefault(
      String key,
      Language language,
      String defaultValue,
      Object... args) {
    Optional<Localization> localization = getSpringProxy().findLocalByKeyAndLang(key, language);
    if (localization.isPresent()) {
      return MessageFormat.format(localization.get().getText(), (Object[]) args);
    } else {
      LOG.warn("Not found local value for key:{} and lang:{}, using default", key,
          language != null ? language.getCode() : "");
      return MessageFormat.format(defaultValue, (Object[]) args);
    }
  }

  @Override
  @Cacheable(cacheNames = "localByLangAndModule", key = "{#language.code, #module}")
  public List<Localization> findLocalByLangAndModule(Language language, ELocalModule module) {
    return localizationRepository.findByLangAndModule(language, module);
  }

  @Override
  @Cacheable(cacheNames = "settingsByType", key = "#type")
  public List<Settings> findAllSettingsByType(Settings.Type type) {
    List<Settings> settings = settingsRepository.findAllByPk_Type(type);

    String helpDeskUrl = applicationCommonProperties.getHelpDeskUrl();
    if (helpDeskUrl != null && !helpDeskUrl.isEmpty()) {
      settings.add(new Settings(new Pk(Type.FRONTEND, "f_custom_prop_help_desk_url"), helpDeskUrl));
    }

    return settings;
  }

  @Override
  @Cacheable(cacheNames = "settingsByKey", key = "#key")
  public Optional<Settings> findSettingByKey(String key) {
    return settingsRepository.findByPk_SettingKey(key);
  }

  /**
   * Use proxy to hit cache
   */
  private LocalizationService getSpringProxy() {
    return applicationContext.getBean(LocalizationService.class);
  }

  @Override
  public Map<Long, String> findAllCitiesByCountryCodeAndLangWithLocals(
      String countryCode, String langCode) {
    return cityDAO
        .findAllByCountryCodeAndLangCodeWithLocals(countryCode, langCode)
        .stream().collect(
            Collectors.toMap(
                city -> ((BigInteger) city[0]).longValue(),
                city -> (String) city[1])
        );
  }

  @Override
  public Optional<City> findCityByIdAndCountryCode(Long cityId, String countryCode) {
    return cityRepository.findByIdAndCountry_Code(cityId, countryCode);
  }

  @Override
  public Optional<City> findCityByNameAndCountryCode(String cityName, String countryCode) {
    return cityDAO.findCityByNameAndCountryCode(cityName, countryCode);
  }

  @Override
  public Page<Localization> findAllLocalizationsByConstraint(FrontendLocalConstraint constraint) {
    return localizationDAO.findAll(constraint);
  }

  @Override
  @Transactional
  public Localization save(Localization localization) {
    Localization l = localizationRepository.save(localization);
    cacheService.evictLocalForKeyAndLang(l.getLocalKey(), l.getLang());
    cacheService.evictLocalByLangAndModule(l.getLang(), l.getModule());
    return l;
  }

  @Override
  @Transactional
  public void delete(Set<Long> ids) {
    ids.stream().filter(Objects::nonNull).forEach(id -> {
      Optional<Localization> localizationOptional = localizationRepository.findById(id);
      if (localizationOptional.isPresent()) {
        Localization localization = localizationOptional.get();
        localizationRepository.delete(localization);
        cacheService.evictLocalForKeyAndLang(localization.getLocalKey(), localization.getLang());
        cacheService.evictLocalByLangAndModule(localization.getLang(), localization.getModule());
      }
    });
  }

  @Override
  public LocalExportDto export() {
    LocalExportDto locals = new LocalExportDto();
    localizationRepository.findAllByOrderByModuleAscLocalKeyAsc()
        .forEach(l -> {
          LocalDto localExportDto = locals.get(l.getLocalKey());
          if (localExportDto == null) {
            SortedMap<String, String> localMap = new TreeMap<>();
            localMap.put(l.getLang().getCode(), l.getText()
                .replaceAll("\r", " ")
                .replaceAll("\n", " "));
            localExportDto = new LocalDto(l.getModule(), l.getLocalKey(), localMap);
            locals.put(l.getLocalKey(), localExportDto);
          } else {
            localExportDto.getLocalMap().put(l.getLang().getCode(), l.getText());
          }
          locals.addColumn(l.getLang().getCode());
        });
    return locals;
  }
}
