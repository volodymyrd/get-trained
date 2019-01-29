package online.gettrained.backend.repositories.localization;

import online.gettrained.backend.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Country DAO
 */
@Repository
public class CountryDAO extends BaseRepository {

  public List<Object[]> findAllByLangCodeWithLocals(String langCode) {
    return getEntityManager()
        .createNativeQuery(
            "SELECT c.CODE as code, c.PHONE_CODE, cl.NAME FROM LOCAL_COUNTRIES c " +
                " INNER JOIN LOCAL_COUNTRY_LOCALS cl ON c.CODE=cl.COUNTRY_CODE AND cl.LANG_CODE=:langCode")
        .setParameter("langCode", langCode)
        .getResultList();
  }
}
