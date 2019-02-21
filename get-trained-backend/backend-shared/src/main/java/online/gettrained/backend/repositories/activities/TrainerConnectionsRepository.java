package online.gettrained.backend.repositories.activities;

import java.util.Optional;
import online.gettrained.backend.domain.activities.TrainerConnections;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link TrainerConnections}.
 */
public interface TrainerConnectionsRepository extends JpaRepository<TrainerConnections, Long> {

  Optional<TrainerConnections> findByTrainer_IdAndTrainee_Id(long trainerId, long traineeId);
}
