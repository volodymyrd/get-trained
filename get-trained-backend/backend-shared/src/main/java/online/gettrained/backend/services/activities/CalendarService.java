package online.gettrained.backend.services.activities;

import java.util.Optional;
import online.gettrained.backend.domain.activities.TrainerConnectionSchedule;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.exceptions.NotFoundException;

/**
 * Service for calendar and schedule of trainings.
 */
public interface CalendarService {

  Optional<TrainerConnectionSchedule> getSchedule(User trainerUser, long connectionId)
      throws NotFoundException;

  TrainerConnectionSchedule saveSchedule(User trainerUser, TrainerConnectionSchedule schedule)
      throws NotFoundException;
}
