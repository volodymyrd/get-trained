package online.gettrained.backend.repositories.localization;

import online.gettrained.backend.domain.localization.Language;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 */
public interface LanguageRepository extends JpaRepository<Language, String> {

  @Query("SELECT l FROM Language l WHERE l.issupported=true")
  List<Language> findAllSupported();

  @Query("SELECT l FROM Language l WHERE l.code=?1 AND l.issupported=true")
  Optional<Language> findSupportedByCode(String code);

  @Query("SELECT l FROM Language l WHERE l.isdefault=true")
  Optional<Language> findDefault();
}
