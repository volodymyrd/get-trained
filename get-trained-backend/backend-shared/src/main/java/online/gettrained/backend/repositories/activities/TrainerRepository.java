package online.gettrained.backend.repositories.activities;

import online.gettrained.backend.domain.activities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Trainer}.
 */
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

}
