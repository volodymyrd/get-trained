package online.gettrained.backend.repositories.localization;

import online.gettrained.backend.domain.localization.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface SettingsRepository extends JpaRepository<Settings, Settings.Pk> {
    List<Settings> findAllByPk_Type(Settings.Type type);

    Optional<Settings> findByPk_SettingKey(String key);
}
