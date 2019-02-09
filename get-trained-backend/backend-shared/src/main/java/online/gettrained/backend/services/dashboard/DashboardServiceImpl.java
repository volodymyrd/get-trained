package online.gettrained.backend.services.dashboard;

import static java.util.Objects.requireNonNull;

import com.google.common.collect.ImmutableList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import online.gettrained.backend.domain.dashboard.Menu;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.localization.Localization;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.repositories.dashboard.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link DashboardService}.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

  private static final Logger LOG = LoggerFactory.getLogger(DashboardServiceImpl.class);

  private final MenuRepository menuRepository;

  public DashboardServiceImpl(
      MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public List<Menu> findFullMainMenuWithChildrenAndWithLocale(User user, Language language) {
    requireNonNull(user, "Parameter 'user' must be set.");
    requireNonNull(language, "Parameter 'language' must be set.");

    LOG.info("Looking for menu for user:{} and lang:{}", user.getId(), language.getCode());

    ImmutableList.Builder<Menu> resultBuilder = ImmutableList.builder();
    Set<String> menuIds = new HashSet<>();

    List<Object[]> topMenus = menuRepository
        .findByParentNullAndUserWithLocale(user.getId(), language.getCode());
    for (Object[] menus : topMenus) {
      resultBuilder.add(findFullMainMenuWithLocale(((Menu) menus[0]), user, language));
    }
    return resultBuilder.build();
  }

  private Menu findFullMainMenuWithLocale(Menu menu, User user, Language language) {
    List<Object[]> menus = menuRepository.findByMenuIdAndUserRoleWithLocale(
        menu.getMenuId(), user.getId(), language.getCode());
    if (menus.size() > 0) {
      Menu mn = convertToMenu(menus.get(0));
      mn.setTitle(((Localization) menus.get(0)[1]).getText());
      for (Menu m : mn.getChildrenSet()) {
        Menu subMenu = findFullMainMenuWithLocale(m, user, language);
        if (subMenu.getTitle() != null && !subMenu.getTitle().isEmpty()) {
          mn.getItems().add(subMenu);
        }
      }
    }
    return menu;
  }

  private Menu convertToMenu(Object[] menuObject) {
    return (Menu) menuObject[0];
  }
}
