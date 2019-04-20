package online.gettrained.backend.services.activities;

import static java.util.Objects.requireNonNull;
import static online.gettrained.backend.domain.activities.TrainerConnections.Status.CONNECTED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import online.gettrained.backend.domain.activities.TraineeTimeSlot;
import online.gettrained.backend.domain.activities.TrainerCalendar;
import online.gettrained.backend.domain.activities.TrainerConnectionSchedule;
import online.gettrained.backend.domain.activities.TrainerConnections;
import online.gettrained.backend.domain.user.User;
import online.gettrained.backend.dto.Page;
import online.gettrained.backend.exceptions.NotFoundException;
import online.gettrained.backend.repositories.activities.TrainerConnectionScheduleRepository;
import org.springframework.stereotype.Service;

/**
 * Implements of {@link CalendarService}.
 */
@Service
public class CalendarServiceImpl implements CalendarService {

  private final ActivityService activityService;
  private final TrainerConnectionScheduleRepository trainerConnectionScheduleRepository;

  public CalendarServiceImpl(
      ActivityService activityService,
      TrainerConnectionScheduleRepository trainerConnectionScheduleRepository) {
    this.activityService = activityService;
    this.trainerConnectionScheduleRepository = trainerConnectionScheduleRepository;
  }

  @Override
  public TrainerConnectionSchedule getSchedule(User user, long connectionId)
      throws NotFoundException {
    TrainerConnections connections = activityService.getConnectionById(user, connectionId);
    if (connections.getStatus() != CONNECTED) {
      throw new NotFoundException("Not found active connection with id:" + connectionId);
    }

    return trainerConnectionScheduleRepository.findByConnection_Id(connectionId)
        .orElse(new TrainerConnectionSchedule());
  }

  @Override
  public TrainerConnectionSchedule saveSchedule(
      User trainerUser, TrainerConnectionSchedule schedule) throws NotFoundException {
    requireNonNull(trainerUser, "Parameter 'trainerUser' must be filled");
    requireNonNull(schedule, "Parameter 'schedule' must be filled");
    requireNonNull(schedule.getTraineeUserId(),
        "Parameter 'schedule.traineeUserId' must be filled");

    TrainerConnections connection = activityService
        .getConnection(trainerUser, schedule.getTraineeUserId());

    Optional<TrainerConnectionSchedule> trainerConnectionSchedule =
        trainerConnectionScheduleRepository.findByConnection_Id(connection.getId());

    if (trainerConnectionSchedule.isPresent()) {
      TrainerConnectionSchedule oldSchedule = trainerConnectionSchedule.get();
      oldSchedule.setUserLastChanged(trainerUser);
      oldSchedule.setMonday(schedule.getMonday());
      oldSchedule.setTuesday(schedule.getTuesday());
      oldSchedule.setWednesday(schedule.getWednesday());
      oldSchedule.setThursday(schedule.getThursday());
      oldSchedule.setFriday(schedule.getFriday());
      oldSchedule.setSaturday(schedule.getSaturday());
      oldSchedule.setSunday(schedule.getSunday());
      schedule = oldSchedule;
    } else {
      schedule.setConnection(connection);
      schedule.setTrainee(activityService.getTrainee(schedule.getTraineeUserId()));
    }

    return trainerConnectionScheduleRepository.save(schedule);
  }

  @Override
  public TrainerCalendar getTrainerCalendar(User trainerUser) {
    requireNonNull(trainerUser, "Parameter 'trainerUser' must be filled");
    int limitConnections = 100;
    Page<TrainerConnections> page = activityService
        .findMyConnections(trainerUser, 0, limitConnections);
    List<TrainerConnectionSchedule> scheduleList = page.getData().stream()
        .map(c -> trainerConnectionScheduleRepository.findByConnection_Id(c.getConnectionId()))
        .filter(Optional::isPresent)
        .map(Optional::get).collect(Collectors.toList());
    if (page.getCount() > limitConnections) {
      page = activityService
          .findMyConnections(trainerUser, limitConnections, page.getCount().intValue());
      scheduleList.addAll(page.getData().stream()
          .map(c -> trainerConnectionScheduleRepository.findByConnection_Id(c.getConnectionId()))
          .filter(Optional::isPresent)
          .map(Optional::get).collect(Collectors.toList()));
    }

    List<TraineeTimeSlot> mondays = new ArrayList<>();
    List<TraineeTimeSlot> tuesdays = new ArrayList<>();
    List<TraineeTimeSlot> wednesdays = new ArrayList<>();
    List<TraineeTimeSlot> thursdays = new ArrayList<>();
    List<TraineeTimeSlot> fridays = new ArrayList<>();
    List<TraineeTimeSlot> saturdays = new ArrayList<>();
    List<TraineeTimeSlot> sundays = new ArrayList<>();

    scheduleList.forEach(s -> {
      if (s.getMonday() != null && !s.getMonday().isEmpty()) {
        mondays.addAll(s.getMonday().stream()
            .map(e -> new TraineeTimeSlot(e, s.getConnection().getId(), s.getTrainee().getId()))
            .collect(Collectors.toList()));
      }
      if (s.getTuesday() != null && !s.getTuesday().isEmpty()) {
        tuesdays.addAll(s.getTuesday().stream()
            .map(e -> new TraineeTimeSlot(e, s.getConnection().getId(), s.getTrainee().getId()))
            .collect(Collectors.toList()));
      }
      if (s.getWednesday() != null && !s.getWednesday().isEmpty()) {
        wednesdays.addAll(s.getWednesday().stream()
            .map(e -> new TraineeTimeSlot(e, s.getConnection().getId(), s.getTrainee().getId()))
            .collect(Collectors.toList()));
      }
      if (s.getThursday() != null && !s.getThursday().isEmpty()) {
        thursdays.addAll(s.getThursday().stream()
            .map(e -> new TraineeTimeSlot(e, s.getConnection().getId(), s.getTrainee().getId()))
            .collect(Collectors.toList()));
      }
      if (s.getFriday() != null && !s.getFriday().isEmpty()) {
        fridays.addAll(s.getFriday().stream()
            .map(e -> new TraineeTimeSlot(e, s.getConnection().getId(), s.getTrainee().getId()))
            .collect(Collectors.toList()));
      }
      if (s.getSaturday() != null && !s.getSaturday().isEmpty()) {
        saturdays.addAll(s.getSaturday().stream()
            .map(e -> new TraineeTimeSlot(e, s.getConnection().getId(), s.getTrainee().getId()))
            .collect(Collectors.toList()));
      }
      if (s.getSunday() != null && !s.getSunday().isEmpty()) {
        sundays.addAll(s.getSunday().stream()
            .map(e -> new TraineeTimeSlot(e, s.getConnection().getId(), s.getTrainee().getId()))
            .collect(Collectors.toList()));
      }
    });

    Collections.sort(mondays);
    Collections.sort(tuesdays);
    Collections.sort(wednesdays);
    Collections.sort(thursdays);
    Collections.sort(fridays);
    Collections.sort(saturdays);
    Collections.sort(sundays);

    return new TrainerCalendar(
        mondays, tuesdays, wednesdays, thursdays, fridays, saturdays, sundays);
  }
}
