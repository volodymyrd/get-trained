package online.gettrained.backend.repositories.profile;

import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

  @Modifying
  @Query("UPDATE Profile p SET p.language=:language WHERE p.id=:id")
  void afterSuccessLoginUpdate(@Param("id") Long id,
      @Param("language") Language language);
}
