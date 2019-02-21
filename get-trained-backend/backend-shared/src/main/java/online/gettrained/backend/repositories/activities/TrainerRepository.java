package online.gettrained.backend.repositories.activities;

import java.util.Optional;
import java.util.Set;
import online.gettrained.backend.domain.activities.Trainer;
import online.gettrained.backend.domain.activities.Trainer.Status;
import online.gettrained.backend.domain.activities.Trainer.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Trainer}.
 */
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

  boolean existsByActivity_IdAndUser_IdAndDeleted(long activityId, long userId, boolean deleted);

  Optional<Trainer> findByActivity_IdAndUser_IdAndStatusInAndVisibilityInAndDeleted(
      long activityId,
      long userId,
      Set<Status> statuses,
      Set<Visibility> visibilities,
      boolean deleted);
}
