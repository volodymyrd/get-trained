package online.gettrained.backend.repositories.localization;

import online.gettrained.backend.domain.localization.City;
import online.gettrained.backend.repositories.BaseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * DAO for {@link City}.
 */
@Repository
public class CityDAO extends BaseRepository {

  @SuppressWarnings("unchecked")
  public List<Object[]> findAllByCountryCodeAndLangCodeWithLocals(
    String countryCode, String langCode) {
    return getEntityManager()
      .createNativeQuery(
        "SELECT c.ID as id, cl.NAME FROM LOCAL_CITIES c " +
          " INNER JOIN LOCAL_CITY_LOCALS cl ON c.ID=cl.CITY_ID AND cl.LANG_CODE=:langCode"
          + " WHERE c.COUNTRY_CODE=:countryCode ORDER BY cl.NAME")
      .setParameter("countryCode", countryCode)
      .setParameter("langCode", langCode)
      .getResultList();
  }

  @SuppressWarnings("unchecked")
  public Optional<City> findCityByNameAndCountryCode(String cityName, String countryCode) {
    List<City> cities = getEntityManager()
      .createNativeQuery(
        "SELECT * FROM LOCAL_CITIES c WHERE c.ID = "
          + "(SELECT CITY_ID FROM LOCAL_CITY_LOCALS cl WHERE cl.NAME=:cityName) "
          + " AND c.COUNTRY_CODE=:countryCode", City.class)
      .setParameter("countryCode", countryCode)
      .setParameter("cityName", cityName)
      .getResultList();
    if (cities == null || cities.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(cities.get(0));
    }
  }
}
