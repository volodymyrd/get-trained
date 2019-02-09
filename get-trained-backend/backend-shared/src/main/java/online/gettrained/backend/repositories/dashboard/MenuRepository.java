package online.gettrained.backend.repositories.dashboard;

import java.util.List;
import online.gettrained.backend.domain.dashboard.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for {@link Menu}.
 */
public interface MenuRepository extends JpaRepository<Menu, String> {

  @Query("SELECT DISTINCT(m), l FROM Menu m, Localization l "
      + "INNER JOIN m.roles r INNER JOIN r.users u "
      + "WHERE m.parent IS NULL AND u.id=:userId AND m.localKey=l.localKey AND l.lang.code=:langCode "
      + "ORDER BY m.position")
  List<Object[]> findByParentNullAndUserWithLocale(
      @Param("userId") long userId,
      @Param("langCode") String langCode);

  @Query("SELECT DISTINCT(m), l FROM Menu m, Localization l "
      + "INNER JOIN m.roles r INNER JOIN r.users u "
      + "WHERE m.parent.id=:parentMenuId AND u.id=:userId "
      + " AND m.localKey=l.localKey AND l.lang.code=:langCode "
      + "ORDER BY m.position")
  List<Object[]> findByParentAndUserWithLocale(
      @Param("parentMenuId") String parentMenuId,
      @Param("userId") Long userId,
      @Param("langCode") String langCode);

  @Query("SELECT DISTINCT(m), l FROM Menu m, Localization l "
      + "INNER JOIN m.roles r INNER JOIN r.users u "
      + "WHERE m.id=:menuId AND u.id=:userId AND m.localKey=l.localKey AND l.lang.code=:langCode "
      + "ORDER BY m.position")
  List<Object[]> findByMenuIdAndUserRoleWithLocale(
      @Param("menuId") String menuId,
      @Param("userId") long userId,
      @Param("langCode") String langCode);
}
