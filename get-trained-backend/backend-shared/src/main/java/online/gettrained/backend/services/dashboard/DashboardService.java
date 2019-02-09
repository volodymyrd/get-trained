package online.gettrained.backend.services.dashboard;

import java.util.List;
import online.gettrained.backend.domain.dashboard.Menu;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.user.User;

/**
 * Dashboard manager service.
 */
public interface DashboardService {

  List<Menu> findFullMainMenuWithChildrenAndWithLocale(User user, Language language);
}
