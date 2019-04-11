package online.gettrained.backend.services.activities;

import online.gettrained.backend.domain.activities.TrainerConnectionSchedule;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.exceptions.NotFoundException;

/**
 * Service for calendar and schedule of trainings.
 */
public interface CalendarService {

  TrainerConnectionSchedule getSchedule(User user, long connectionId) throws NotFoundException;

  TrainerConnectionSchedule saveSchedule(User trainerUser, TrainerConnectionSchedule schedule)
      throws NotFoundException;

  TrainerConnectionSchedule getMergedSchedule(User trainerUser) throws NotFoundException;
}
