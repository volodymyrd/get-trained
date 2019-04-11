package online.gettrained.backend.repositories.activities;

import java.util.Optional;
import online.gettrained.backend.domain.activities.TrainerConnectionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link TrainerConnectionSchedule}.
 */
public interface TrainerConnectionScheduleRepository extends
    JpaRepository<TrainerConnectionSchedule, Long> {

  Optional<TrainerConnectionSchedule> findByConnection_Id(long connectionId);
}
