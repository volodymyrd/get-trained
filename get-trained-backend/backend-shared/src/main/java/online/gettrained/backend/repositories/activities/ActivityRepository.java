package online.gettrained.backend.repositories.activities;

import online.gettrained.backend.domain.activities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Activity}.
 */
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
