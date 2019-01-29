package online.gettrained.backend.repositories.localization;

import online.gettrained.backend.domain.localization.City;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface CityRepository extends JpaRepository<City, String> {

  Optional<City> findByIdAndCountry_Code(Long id, String countryCode);
}
