package online.gettrained.backend.repositories.user;

import java.util.Date;
import java.util.Optional;
import online.gettrained.backend.domain.user.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 */
public interface UserTokenRepository extends JpaRepository<UserToken, UserToken.Pk> {

  Optional<UserToken> findByTokenAndExpireDateGreaterThan(String token, Date expireDate);

  Optional<UserToken> findByPkAndExpireDateGreaterThan(UserToken.Pk pk, Date expireDate);

  @Query("SELECT t FROM UserToken t INNER JOIN FETCH t.pk.user u INNER JOIN FETCH u.profile" +
      " WHERE t.token=:token AND t.expireDate > :expireDate")
  Optional<UserToken> findByTokenAndExpireDateGreaterThanWithUserAndProfile(
      @Param("token") String token,
      @Param("expireDate") Date expireDate);

  @Modifying
  @Query("DELETE FROM UserToken t WHERE t.token=:token")
  void deleteByToken(@Param("token") String token);
}
