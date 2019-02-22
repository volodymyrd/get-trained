package online.gettrained.backend.repositories.user;

import online.gettrained.backend.domain.localization.Language;
import online.gettrained.backend.domain.user.User;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u INNER JOIN FETCH u.profile p ")
  List<User> findAllWithProfile();

  @Query("SELECT u FROM User u INNER JOIN FETCH u.profile p WHERE p.id=:profileId")
  Optional<User> findWithProfileByProfileId(@Param("profileId") Long profileId);

  @Query("SELECT u FROM User u WHERE lower(u.email)=:email")
  Optional<User> findOneByEmail(@Param("email") String email);

  @Query("SELECT u FROM User u WHERE lower(u.userName)=:userName")
  Optional<User> findOneByUserName(@Param("userName") String userName);

  @Query("SELECT u FROM User u JOIN FETCH u.profile WHERE lower(u.email)=:email")
  Optional<User> findOneByEmailWithProfile(@Param("email") String email);

  @Query("SELECT u FROM User u WHERE lower(u.email) IN :emails")
  List<User> findAllByEmails(@Param("emails") Set<String> emails);

  @Query("SELECT u FROM User u INNER JOIN FETCH u.profile p " +
      " LEFT JOIN FETCH p.setEmails LEFT JOIN FETCH p.setPhones WHERE u.id=:id")
  Optional<User> findByIdWithFullProfile(@Param("id") Long id);

  @Query("SELECT u FROM User u JOIN FETCH u.profile p "
      + " LEFT JOIN FETCH p.language LEFT JOIN FETCH p.country LEFT JOIN FETCH p.city "
      + " LEFT JOIN FETCH p.setEmails LEFT JOIN FETCH p.setPhones WHERE u.id=:id")
  Optional<User> findByIdWithFullProfileAndLangAndCountryAndCity(@Param("id") Long id);

  @Query("SELECT u FROM User u INNER JOIN FETCH u.profile p JOIN FETCH u.roles "
      + " INNER JOIN FETCH p.language LEFT JOIN FETCH p.country"
      + " LEFT JOIN FETCH p.setEmails LEFT JOIN FETCH p.setPhones WHERE u.id=:id")
  Optional<User> findByIdWithFullProfileAndLangAndCountryAndRoles(@Param("id") Long id);

  @Query("SELECT u FROM User u INNER JOIN FETCH u.profile p INNER JOIN FETCH p.language WHERE u.id=:id")
  Optional<User> findByIdWithProfileWithLang(@Param("id") Long id);

  @Query("SELECT u FROM User u INNER JOIN FETCH u.profile p WHERE u.id=:id")
  Optional<User> findByIdWithProfile(@Param("id") Long id);

  @Query("SELECT u FROM User u INNER JOIN FETCH u.roles WHERE u.id=?1")
  Optional<User> findOneByIdWithRoles(Long id);

  @Query("SELECT u FROM User u INNER JOIN FETCH u.roles WHERE lower(u.email)=?1")
  Optional<User> findOneByEmailWithRoles(String email);

  @Query("SELECT u FROM User u INNER JOIN FETCH u.roles " +
      "INNER JOIN FETCH u.profile p INNER JOIN FETCH p.language WHERE lower(u.email)=?1")
  Optional<User> findOneByEmailWithRolesWithProfileWithLang(String email);

  @Query("SELECT u FROM User u INNER JOIN FETCH u.roles WHERE lower(u.userName)=?1")
  Optional<User> findOneByUserNameWithRoles(String userName);

  @Query("SELECT u FROM User u JOIN FETCH u.profile WHERE lower(u.userName)=?1")
  Optional<User> findOneByUserNameWithProfile(String userName);

  @Query("SELECT u FROM User u JOIN FETCH u.profile JOIN FETCH u.roles WHERE lower(u.userName)=?1")
  Optional<User> findOneByUserNameWithProfileAndRoles(String userName);

  @Query("SELECT COUNT(u) FROM User u WHERE lower(u.email)=?1")
  long countByEmail(String email);

  @Query("SELECT COUNT(u) FROM User u WHERE lower(u.email)=?1 AND u.id<>?2")
  long countByEmailExcludeUserId(String email, long userId);

  @Query("SELECT COUNT(u) FROM User u WHERE lower(u.userName)=?1")
  Long countByUserName(String userName);

  @Query("SELECT COUNT(u) FROM User u WHERE lower(u.userName)=?1 AND u.id<>?2")
  Long countByUserNameExcludeUserId(String userName, long userId);

  List<User> findAllBy(Pageable pageable);

  @Modifying
  @Query("UPDATE User u SET u.lastVisit=:lastVisit, u.loginLang=:lang, u.loginIp=:ip, "
      + "u.badLoginAttempts=0, u.dateUpdate=CURRENT_TIMESTAMP, u.version=u.version+1 WHERE u.id=:id")
  void afterSuccessLoginUpdate(
      @Param("id") Long id,
      @Param("lastVisit") Date lastVisit,
      @Param("lang") Language lang,
      @Param("ip") String ip);

  @Modifying
  @Query("UPDATE User u SET u.badLoginAttempts=1, u.dateUpdate=CURRENT_TIMESTAMP, "
      + "u.version=u.version+1 WHERE u.id=:id")
  void setToOneBadLoginAttempts(@Param("id") Long id);

  @Modifying
  @Query("UPDATE User u SET u.badLoginAttempts=u.badLoginAttempts+1, "
      + "u.dateUpdate=CURRENT_TIMESTAMP, u.version=u.version+1 WHERE u.id=:id")
  void incrementBadLoginAttempts(@Param("id") Long id);

  @Modifying
  @Query("UPDATE User u SET u.locked=true, u.dateUpdate=CURRENT_TIMESTAMP, u.version=u.version+1 "
      + "WHERE u.id=:id")
  void lockUser(@Param("id") Long id);

  Optional<User> findByIdAndRoles_NameIn(Long id, List<String> roleNames);

  boolean existsByIdAndRoles_NameIn(Long id, List<String> roleNames);
}
