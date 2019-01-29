package online.gettrained.backend.services.cache;

import javax.annotation.Nullable;
import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.user.User;

/**
 *
 */
public interface CacheService {

  void evictDashboard(User user, @Nullable Long dashboardId);

  void evictFullUserProfile(Long userId);

  void evictFullUserProfileWithRoles(Long userId);

  void evictLocalForKeyAndLang(String key, Language language);

  void evictLocalByLangAndModule(Language language, ELocalModule module);
}
