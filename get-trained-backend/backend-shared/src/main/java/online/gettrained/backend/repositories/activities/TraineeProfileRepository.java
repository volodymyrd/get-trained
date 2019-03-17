package online.gettrained.backend.repositories.activities;

import java.util.Optional;
import online.gettrained.backend.domain.activities.TraineeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link TraineeProfile}.
 */
public interface TraineeProfileRepository extends JpaRepository<TraineeProfile, Long> {

  Optional<TraineeProfile> findByIdAndConnection_Id(long id, long connectionId);
}
