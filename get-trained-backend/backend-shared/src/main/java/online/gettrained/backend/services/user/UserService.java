package online.gettrained.backend.services.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.domain.user.UserRole;
import online.gettrained.backend.domain.user.UserToken;
import online.gettrained.backend.exceptions.NoAuthorityException;
import online.gettrained.backend.exceptions.NotFoundException;
import online.gettrained.backend.repositories.user.RoleRepository;

/**
 *
 */
public interface UserService {

  String DEFAULT_USER_ROLE_NAME = RoleRepository.USER_ROLE;

  List<User> findAllUsersWithProfile();

  Optional<User> findUserWithProfileByProfileId(Long profileId);

  Optional<User> findById(Long id);

  Optional<User> findOneByEmail(String email);

  Optional<User> findOneByEmailWithProfile(String email);

  Optional<User> findByIdWithFullProfile(Long id);

  Optional<User> findByIdWithFullProfileAndLangAndCountryAndCity(Long id);

  Optional<User> findByIdWithFullProfileAndLangAndCountryAndRoles(Long id);

  Optional<User> findByIdWithProfile(Long id);

  Optional<User> findByIdWithRoles(long id);

  Optional<User> findByIdWithProfileWithLang(Long id);

  long countByEmail(String email);

  long countByUsername(String username);

  Optional<User> findOneByIdAndRoleNames(Long id, List<String> roleNames);

  Optional<User> findOneByEmailWithRoles(String email);

  Optional<User> findOneByUserNameWithRoles(String userName);

  Optional<User> findOneByUserNameWithProfileAndRoles(String userName);

  User saveUser(User user);

  User saveUserAndDeleteToken(User user, String token);

  User addRole(User user, String roleName) throws NotFoundException;

  Profile saveProfile(User user, Profile profile);

  Profile saveProfile(User user, Profile profile, Set<String> emails, Set<String> phones);

  Profile removeAvatar(User user, Profile profile);

  User afterSuccessLoginUpdate(User user, Language language, String ip);

  User createDefaultUser(User user, Profile profile);

  String generateUserToken(User user, UserToken.Type type, Date expiredDate);

  Optional<User> findUserByUserToken(String token, Date expiredDate);

  boolean checkAdminAccess(final User user);

  List<UserRole> findAllRolesByIds(List<Long> ids);

  List<UserRole> findAllRolesByScopeAndLangInWithLocals(
      List<UserRole.Scope> scopes,
      String langCode);

  void unlockUserById(User user, long userId) throws NoAuthorityException, NotFoundException;

  void unlockUserByUserName(User user, String userName)
      throws NoAuthorityException, NotFoundException;

  void unlockUserByEmail(User user, String email) throws NoAuthorityException, NotFoundException;
}
