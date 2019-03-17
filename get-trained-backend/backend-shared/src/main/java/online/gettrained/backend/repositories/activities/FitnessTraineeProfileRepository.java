package online.gettrained.backend.repositories.activities;

import java.util.Optional;
import online.gettrained.backend.domain.activities.FitnessTraineeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

/**
 * Repository for {@link FitnessTraineeProfile}.
 */
public interface FitnessTraineeProfileRepository extends
    JpaRepository<FitnessTraineeProfile, Long> {

  Optional<FitnessTraineeProfile> findByIdAndConnection_Id(long id, long connectionId);

  @Modifying
  void deleteByIdAndConnection_Id(long id, long connectionId);
}
