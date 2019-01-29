package online.gettrained.backend.repositories.user;

import online.gettrained.backend.domain.user.UserRole;
import online.gettrained.backend.domain.user.UserRole.Scope;
import online.gettrained.backend.repositories.BaseRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * Role dao
 */
@Repository
public class RoleDAO extends BaseRepository {

  public List<UserRole> findAllByScopeAndLangInWithLocals(
      List<UserRole.Scope> scopes,
      String langCode) {
    return ((List<Object[]>) getEntityManager()
        .createNativeQuery(
            "SELECT r.ID, r.NAME, r.DEFAULT_DESCRIPTION, r.SCOPE, "
                + " rl.DESCRIPTION FROM USR_ROLES r "
                + " LEFT JOIN USR_ROLE_LOCALS rl ON rl.REF_ROLE_ID=r.ID AND rl.LANG_CODE=:langCode "
                + " WHERE r.SCOPE IN :scopes")
        .setParameter("scopes", scopes.stream().map(Enum::name).collect(Collectors.toList()))
        .setParameter("langCode", langCode)
        .getResultList())
        .stream()
        .map(r -> {
          UserRole role = new UserRole();
          role.setRoleId(((BigInteger) r[0]).longValue());
          role.setName((String) r[1]);
          role.setScope(Scope.valueOf((String) r[3]));
          role.setDescription((String) (r[4] != null ? r[4] : r[2]));
          return role;
        })
        .collect(Collectors.toList());
  }
}
