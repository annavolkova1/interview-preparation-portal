package com.epam.firstProject.dao;

import com.epam.firstProject.domain.ScheduledTimeslot;
import java.sql.Timestamp;
import java.util.List;

public interface ScheduledTimeslotDao {

  List<ScheduledTimeslot> getScheduleTimeslotById(Long idd);

  List<ScheduledTimeslot> getScheduledTimeslotsOverThePeriod(Timestamp start, Timestamp end);

  long createScheduledTimeslot(ScheduledTimeslot scheduledTimeslot);

  void updateScheduledTimeslot(ScheduledTimeslot scheduledTimeslot);

  void deleteById(Long id);
}
