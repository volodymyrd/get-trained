package online.gettrained.backend.services.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.profile.Profile;
import online.gettrained.backend.domain.profile.ProfileEmail;
import online.gettrained.backend.domain.profile.ProfilePhone;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.domain.user.UserRole;
import online.gettrained.backend.domain.user.UserToken;
import online.gettrained.backend.exceptions.NoAuthorityException;
import online.gettrained.backend.exceptions.NotFoundException;
import online.gettrained.backend.repositories.blob.BlobDataRepository;
import online.gettrained.backend.repositories.profile.ProfileEmailRepository;
import online.gettrained.backend.repositories.profile.ProfilePhoneRepository;
import online.gettrained.backend.repositories.profile.ProfileRepository;
import online.gettrained.backend.repositories.user.RoleDAO;
import online.gettrained.backend.repositories.user.RoleRepository;
import online.gettrained.backend.repositories.user.UserDAO;
import online.gettrained.backend.repositories.user.UserRepository;
import online.gettrained.backend.repositories.user.UserTokenRepository;
import online.gettrained.backend.services.cache.CacheService;
import online.gettrained.backend.services.contacts.ContactsService;
import online.gettrained.backend.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class UserServiceImpl implements UserService {

  private List<UserRole> ADMIN_ROLES = CommonUtils.immutableListOf(new UserRole("ROLE_ADMIN"));

  private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;
  private final UserDAO userDAO;
  private final RoleRepository roleRepository;
  private final RoleDAO roleDAO;
  private final ProfileRepository profileRepository;
  private final UserTokenRepository userTokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final BlobDataRepository blobDataRepository;
  private final ProfileEmailRepository profileEmailRepository;
  private final ProfilePhoneRepository profilePhoneRepository;
  private final ContactsService contactsService;
  private final CacheService cacheService;

  public UserServiceImpl(
      UserRepository userRepository,
      UserDAO userDAO,
      RoleRepository roleRepository,
      RoleDAO roleDAO,
      ProfileRepository profileRepository,
      UserTokenRepository userTokenRepository,
      PasswordEncoder passwordEncoder,
      BlobDataRepository blobDataRepository,
      ProfileEmailRepository profileEmailRepository,
      ProfilePhoneRepository profilePhoneRepository,
      ContactsService contactsService,
      CacheService cacheService) {
    this.userRepository = userRepository;
    this.userDAO = userDAO;
    this.roleRepository = roleRepository;
    this.roleDAO = roleDAO;
    this.profileRepository = profileRepository;
    this.userTokenRepository = userTokenRepository;
    this.passwordEncoder = passwordEncoder;
    this.blobDataRepository = blobDataRepository;
    this.profileEmailRepository = profileEmailRepository;
    this.profilePhoneRepository = profilePhoneRepository;
    this.contactsService = contactsService;
    this.cacheService = cacheService;
  }

  @Override
  public List<User> findAllUsersWithProfile() {
    return userRepository.findAllWithProfile();
  }

  @Override
  public Optional<User> findUserWithProfileByProfileId(Long profileId) {
    return userRepository.findWithProfileByProfileId(profileId);
  }

  @Override
  //TODO @Cacheable(cacheNames = "findById", key = "#id")
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public Optional<User> findOneByEmail(String email) {
    return userRepository.findOneByEmail(email.toLowerCase());
  }

  @Override
  public Optional<User> findOneByEmailWithProfile(String email) {
    return userRepository.findOneByEmailWithProfile(email.toLowerCase());
  }

  @Override
  public Optional<User> findByIdWithFullProfile(Long id) {
    return userRepository.findByIdWithFullProfile(id);
  }

  @Override
  @Cacheable(cacheNames = "fullUserProfile", key = "#id")
  public Optional<User> findByIdWithFullProfileAndLangAndCountryAndCity(Long id) {
    return userRepository.findByIdWithFullProfileAndLangAndCountryAndCity(id);
  }

  @Override
  @Cacheable(cacheNames = "fullUserProfileWithRoles", key = "#id")
  public Optional<User> findByIdWithFullProfileAndLangAndCountryAndRoles(Long id) {
    return userRepository.findByIdWithFullProfileAndLangAndCountryAndRoles(id);
  }

  @Override
  public Optional<User> findByIdWithProfile(Long id) {
    return userRepository.findByIdWithProfile(id);
  }

  @Override
  public Optional<User> findByIdWithProfileWithLang(Long id) {
    return userRepository.findByIdWithProfileWithLang(id);
  }

  @Override
  public long countByEmail(String email) {
    return userRepository.countByEmail(email.toLowerCase());
  }

  @Override
  public long countByUsername(String username) {
    return userRepository.countByUserName(username.toLowerCase());
  }

  @Override
  public Optional<User> findOneByIdAndRoleNames(Long id, List<String> roleNames) {
    return userRepository.findByIdAndRoles_NameIn(id, roleNames);
  }

  @Override
  public Optional<User> findOneByEmailWithRoles(String email) {
    return userRepository.findOneByEmailWithRoles(email.toLowerCase());
  }

  @Override
  public Optional<User> findOneByUserNameWithRoles(String userName) {
    return userRepository.findOneByUserNameWithRoles(userName.toLowerCase());
  }

  @Override
  public Optional<User> findOneByUserNameWithProfileAndRoles(String userName) {
    return userRepository.findOneByUserNameWithProfileAndRoles(userName.toLowerCase());
  }

  @Override
  @Transactional
  public User saveUser(User user) {
    if (user.getNewPassword() != null && !user.getNewPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(user.getNewPassword()));
      user.setNewPassword(null);
    }
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User saveUserAndDeleteToken(User user, String token) {
    User updatedUser = saveUser(user);
    userTokenRepository.deleteByToken(token);
    return updatedUser;
  }

  @Override
  @Transactional
  public Profile saveProfile(User user, Profile profile) {
    profile = profileRepository.save(profile);
    if (user != null && user.getId() != null) {
      cacheService.evictFullUserProfile(user.getId());
      cacheService.evictFullUserProfileWithRoles(user.getId());
    }
    return profile;
  }

  @Override
  @Transactional
  public Profile saveProfile(User user, Profile profile, Set<String> emails, Set<String> phones) {
    profileEmailRepository.deleteAll(profile.getSetEmails());
    profile.getSetEmails().clear();
    emails.forEach(e -> {
      ProfileEmail profileEmail = profileEmailRepository.save(new ProfileEmail(
          new ProfileEmail.Pk(profile, contactsService.getEmailAndAddIfNotExist(e))));
      if (profileEmail != null) {
        profile.getSetEmails().add(profileEmail);
      }
    });
    profilePhoneRepository.deleteAll(profile.getSetPhones());
    profile.getSetPhones().clear();
    phones.forEach(e -> {
      ProfilePhone profilePhone = profilePhoneRepository.save(new ProfilePhone(
          new ProfilePhone.Pk(profile, contactsService.getPhoneAndAddIfNotExist(e))));
      if (profilePhone != null) {
        profile.getSetPhones().add(profilePhone);
      }
    });
    return saveProfile(user, profile);
  }

  @Override
  @Transactional
  public Profile removeAvatar(User user, Profile profile) {
    if (profile.getAvatarId() == null) {
      return profile;
    }

    blobDataRepository.deleteById(profile.getAvatarId());

    profile.setAvatarId(null);

    return saveProfile(user, profile);
  }

  @Override
  @Transactional
  public User afterSuccessLoginUpdate(User user, Language language, String ip) {
    user = userRepository.findByIdWithProfile(user.getId())
        .orElseThrow(IllegalStateException::new);
    userRepository.afterSuccessLoginUpdate(user.getId(), new Date(), language, ip);
    if (user.getProfile().getLanguage() == null) {
      profileRepository.afterSuccessLoginUpdate(user.getProfile().getId(), language);
    }

    return user;
  }

  @Override
  @Transactional
  public User createDefaultUser(User user, Profile profile) {
    if (user == null) {
      throw new IllegalArgumentException("user must not be null");
    }

    Optional<UserRole> defaultUserRole = roleRepository.findByName(DEFAULT_USER_ROLE_NAME);

    if (!defaultUserRole.isPresent()) {
      throw new RuntimeException("you must config default user role");
    }

    user.setEmail(user.getEmail().toLowerCase());
    user.getRoles().add(defaultUserRole.get());

    if (user.getUserName() == null || user.getUserName().isEmpty()) {
      user.setUserName(user.getEmail());
    }
    user.setProfile(saveProfile(user, profile));
    User persistedUser = saveUser(user);
    LOG.info("User with email {} has been created successfully", user.getEmail());
    return persistedUser;
  }

  @Override
  @Transactional
  public String generateUserToken(User user, UserToken.Type type, Date expiredDate) {
    Optional<UserToken> userTokenOptional = userTokenRepository
        .findByPkAndExpireDateGreaterThan(new UserToken.Pk(user, type), new Date());
    if (userTokenOptional.isPresent()) {
      return userTokenOptional.get().getToken();
    }

    UserToken userToken = new UserToken(user, type, expiredDate, UUID.randomUUID().toString());
    try {
      userToken = userTokenRepository.save(userToken);
      if (userToken != null) {
        return userToken.getToken();
      }
    } catch (Exception ex) {
      LOG.error("Error generating a user token", ex);
    }

    return null;
  }

  @Override
  public Optional<User> findUserByUserToken(String token, Date expiredDate) {
    Optional<UserToken> userTokenOptional =
        userTokenRepository
            .findByTokenAndExpireDateGreaterThanWithUserAndProfile(token, expiredDate);

    return userTokenOptional.map(userToken -> userToken.getPk().getUser());
  }

  @Override
  @Transactional
  public boolean checkAdminAccess(User user) {
    if (user == null) {
      LOG.error("Parameter 'user' must be set");
      return false;
    }

    user = findById(user.getId()).orElseThrow(IllegalStateException::new);

    return user.getRoles().stream().anyMatch(r -> ADMIN_ROLES.contains(r));
  }

  @Override
  public List<UserRole> findAllRolesByIds(List<Long> ids) {
    return roleRepository.findAllById(ids);
  }

  @Override
  public List<UserRole> findAllRolesByScopeAndLangInWithLocals(
      List<UserRole.Scope> scopes,
      String langCode) {
    return roleDAO.findAllByScopeAndLangInWithLocals(scopes, langCode);
  }

  @Override
  @Transactional
  public void unlockUserById(User user, long userId)
      throws NoAuthorityException, NotFoundException {
    if (!checkAdminAccess(user)) {
      throw new NoAuthorityException("No admin authority for a user with id:" + user.getId());
    }
    User lockedUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("Not found a user with id:" + userId));

    unlockUser(lockedUser);
  }

  @Override
  @Transactional
  public void unlockUserByUserName(User user, String userName)
      throws NoAuthorityException, NotFoundException {
    if (!checkAdminAccess(user)) {
      throw new NoAuthorityException("No admin authority for a user with id:" + user.getId());
    }
    unlockUser(userRepository.findOneByUserName(userName.toLowerCase())
        .orElseThrow(() -> new NotFoundException("Not found a user with userName:" + userName)));
  }

  @Override
  @Transactional
  public void unlockUserByEmail(User user, String email)
      throws NoAuthorityException, NotFoundException {
    if (!checkAdminAccess(user)) {
      throw new NoAuthorityException("No admin authority for a user with id:" + user.getId());
    }
    unlockUser(userRepository.findOneByEmail(email.toLowerCase())
        .orElseThrow(() -> new NotFoundException("Not found a user with email:" + email)));
  }

  private void unlockUser(User lockedUser) {
    if (lockedUser.getLocked() == null || !lockedUser.getLocked()) {
      LOG.warn("User with id:{} is not locked", lockedUser.getId());
      return;
    }

    lockedUser.setLocked(false);
    lockedUser.setBadLoginAttempts(0);
    saveUser(lockedUser);
    LOG.info("User with id:{} unlocked successfully", lockedUser.getId());
  }
}
