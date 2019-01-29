package online.gettrained.backend.services.cache;

import online.gettrained.backend.domain.localization.ELocalModule;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CacheServiceImpl implements CacheService {

  private static final Logger LOG = LoggerFactory.getLogger(CacheServiceImpl.class);

  @Override
  @CacheEvict(cacheNames = "getDashboard", key = "{#user.id, #dashboardId}")
  public void evictDashboard(User user, Long dashboardId) {
    LOG.info("Evicting the 'getDashboard' cache for key - userId:{}, dashboardId:{}",
        user.getId(), dashboardId);
  }

  @Override
  @CacheEvict(cacheNames = "fullUserProfile", key = "#userId")
  public void evictFullUserProfile(Long userId) {
    LOG.info("Evicting the 'fullUserProfile' cache for key - userId:{}", userId);
  }

  @Override
  @CacheEvict(cacheNames = "fullUserProfileWithRoles", key = "#userId")
  public void evictFullUserProfileWithRoles(Long userId) {
    LOG.info("Evicting the 'fullUserProfileWithRoles' cache for key - userId:{}", userId);
  }

  @Override
  @CacheEvict(cacheNames = "localForKeyAndLang", key = "{#key, #language.code}")
  public void evictLocalForKeyAndLang(String key, Language language) {
    LOG.info("Evicting the 'localForKeyAndLang' cache for key - key:{}, langCode:{}",
        key, language.getCode());
  }

  @Override
  @CacheEvict(cacheNames = "localByLangAndModule", key = "{#language.code, #module}")
  public void evictLocalByLangAndModule(Language language, ELocalModule module) {
    LOG.info("Evicting the 'localByLangAndModule' cache for key - language:{}, module:{}",
        language, module);
  }
}
