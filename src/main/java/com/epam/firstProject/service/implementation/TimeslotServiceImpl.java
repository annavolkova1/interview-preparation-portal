package com.epam.firstProject.service.implementation;

import com.epam.firstProject.dao.implementation.ScheduledTimeslotDaoImpl;
import com.epam.firstProject.domain.ScheduledTimeslot;
import com.epam.firstProject.service.TimeslotService;
import java.sql.Timestamp;
import java.util.List;

public class TimeslotServiceImpl implements TimeslotService {

  private final ScheduledTimeslotDaoImpl scheduledTimeslotDao = new ScheduledTimeslotDaoImpl();

  @Override
  public List<ScheduledTimeslot> getScheduleTimeslotById(Long id) {

    return scheduledTimeslotDao.getScheduleTimeslotById(id);
  }

  @Override
  public List<ScheduledTimeslot> getScheduledTimeslotsOverThePeriod(Timestamp start, Timestamp end) {

    return scheduledTimeslotDao.getScheduledTimeslotsOverThePeriod(start, end);
  }

  @Override
  public long createScheduledTimeslot(ScheduledTimeslot scheduledTimeslot) {

    return scheduledTimeslotDao.createScheduledTimeslot(scheduledTimeslot);
  }

  @Override
  public void updateScheduledTimeslot(ScheduledTimeslot scheduledTimeslot) {

    scheduledTimeslotDao.updateScheduledTimeslot(scheduledTimeslot);
  }

  @Override
  public void deleteById(Long id) {

    scheduledTimeslotDao.deleteById(id);
  }
}
