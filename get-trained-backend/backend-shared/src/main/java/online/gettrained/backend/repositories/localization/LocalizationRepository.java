package online.gettrained.backend.repositories.localization;

import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.localization.Localization;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 */
public interface LocalizationRepository extends JpaRepository<Localization, Long> {

  Optional<Localization> findByLocalKeyAndLang_Code(String key, String langCode);

  List<Localization> findAllByOrderByModuleAscLocalKeyAsc();

  @Query("SELECT l FROM Localization l WHERE l.localKey=?1 AND l.lang=?2")
  Optional<Localization> findOneByKeyAndLang(String key, Language language);

  @Query("SELECT l FROM Localization l WHERE l.localKey=?1")
  List<Localization> findAllByKey(String key);

  @Query("SELECT l FROM Localization l WHERE l.lang=?1 AND l.module IN ('COMMON', ?2)")
  List<Localization> findByLangAndModule(Language language, ELocalModule module);
}
