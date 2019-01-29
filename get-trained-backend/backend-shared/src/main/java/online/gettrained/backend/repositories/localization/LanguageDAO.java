package online.gettrained.backend.repositories.localization;

import online.gettrained.backend.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Country DAO
 */
@Repository
public class LanguageDAO extends BaseRepository {
    public List<Object[]> findAllWithLocalsByLangCodeOrderByCode(String langCode) {
        return getEntityManager()
                .createNativeQuery(
                        "SELECT c.CODE as code, cl.NAME FROM LOCAL_LANGUAGES c " +
                                " INNER JOIN LOCAL_LANGUAGE_LOCALS cl ON c.CODE=cl.LANG_CODE " +
                                " WHERE cl.LANG_CODE_LOCAL=:langCode ORDER BY code ")
                .setParameter("langCode", langCode)
                .getResultList();
    }
}
