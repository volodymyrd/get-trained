package online.gettrained.backend.repositories.localization;

import online.gettrained.backend.domain.localization.Country;
import online.gettrained.backend.domain.localization.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 */
public interface CountryRepository extends JpaRepository<Country, String> {
    @Query("SELECT c.code, value(c.mapLocals) FROM Country c WHERE KEY(c.mapLocals)=:lang ")
    List<Object[]> findAllWithLocalsByLang(@Param("lang") Language lang);
}
