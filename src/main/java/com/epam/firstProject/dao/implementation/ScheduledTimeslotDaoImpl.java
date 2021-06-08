package com.epam.firstProject.dao.implementation;

import com.epam.firstProject.connection.ConnectionProvider;
import com.epam.firstProject.dao.ScheduledTimeslotDao;
import com.epam.firstProject.domain.ScheduledTimeslot;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScheduledTimeslotDaoImpl implements ScheduledTimeslotDao {

  final static Log logger = LogFactory.getLog(ScheduledTimeslotDao.class);

  private static final String SELECT_BY_ID = "SELECT ID, SKILL_ID, INTERVIEWER_ID, START_TIME, END_TIME, COMMENT, "
      + "ACTIVE FROM scheduled_timeslots WHERE ID = ?";
  private static final String SELECT_OVER_THE_PERIOD =
      "SELECT ID, SKILL_ID, INTERVIEWER_ID, START_TIME, END_TIME, COMMENT, ACTIVE FROM scheduled_timeslots WHERE "
          + "START_TIME BETWEEN ? AND ? ";
  private static final String INSERT =
      "INSERT INTO scheduled_timeslots (SKILL_ID, INTERVIEWER_ID, START_TIME, END_TIME, COMMENT, ACTIVE) VALUES(?, ?, "
          + "?, ?, ?, ?)";
  private static final String UPDATE =
      "UPDATE scheduled_timeslots SET ID = ?, SKILL_ID = ?, INTERVIEWER_ID = ?, START_TIME = ?, END_TIME = ?, "
          + "COMMENT = ?, ACTIVE = ? WHERE ID = ?";
  private static final String DELETE_BY_ID = "DELETE FROM scheduled_timeslots WHERE ID = ?";

  @Override
  public List<ScheduledTimeslot> getScheduleTimeslotById(Long idd) {

    List<ScheduledTimeslot> scheduledTimeslots = new ArrayList<>();

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
      preparedStatement.setLong(1, idd);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          getResultSet(scheduledTimeslots, resultSet);
        }
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
    return scheduledTimeslots;
  }

  @Override
  public List<ScheduledTimeslot> getScheduledTimeslotsOverThePeriod(Timestamp start, Timestamp end) {

    List<ScheduledTimeslot> scheduledTimeslots = new ArrayList<>();

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OVER_THE_PERIOD)) {
      preparedStatement.setTimestamp(1, start);
      preparedStatement.setTimestamp(2, end);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          getResultSet(scheduledTimeslots, resultSet);
        }
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }

    return scheduledTimeslots;
  }

  @Override
  public long createScheduledTimeslot(ScheduledTimeslot scheduledTimeslot) {

    long generatedKey = 0;

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setLong(1, scheduledTimeslot.getSkillId());
      preparedStatement.setLong(2, scheduledTimeslot.getInterviewerId());
      preparedStatement.setTimestamp(3, scheduledTimeslot.getStartTime());
      preparedStatement.setTimestamp(4, scheduledTimeslot.getEndTime());
      preparedStatement.setString(5, scheduledTimeslot.getComment());
      preparedStatement.setBoolean(6, scheduledTimeslot.isActive());
      preparedStatement.executeUpdate();
      ResultSet rowsAdded = preparedStatement.getGeneratedKeys();

      if (rowsAdded.next()) {
        generatedKey = rowsAdded.getLong(1);
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
    return generatedKey;
  }

  @Override
  public void updateScheduledTimeslot(ScheduledTimeslot scheduledTimeslot) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
      preparedStatement.setLong(1, scheduledTimeslot.getId());
      preparedStatement.setLong(2, scheduledTimeslot.getSkillId());
      preparedStatement.setLong(3, scheduledTimeslot.getInterviewerId());
      preparedStatement.setTimestamp(4, scheduledTimeslot.getStartTime());
      preparedStatement.setTimestamp(5, scheduledTimeslot.getEndTime());
      preparedStatement.setString(6, scheduledTimeslot.getComment());
      preparedStatement.setBoolean(7, scheduledTimeslot.isActive());
      preparedStatement.setLong(8, scheduledTimeslot.getId());

      int rowsUpdated = preparedStatement.executeUpdate();
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
  }

  @Override
  public void deleteById(Long id) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

      preparedStatement.setLong(1, id);
      int rowsDeleted = preparedStatement.executeUpdate();
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
  }

  private void getResultSet(List<ScheduledTimeslot> scheduledTimeslots, ResultSet resultSet) throws SQLException {

    Long id = resultSet.getLong(1);
    Long skillId = resultSet.getLong(2);
    Long interviewerId = resultSet.getLong(3);
    Timestamp startTime = resultSet.getTimestamp(4);
    Timestamp endTime = resultSet.getTimestamp(5);
    String comment = resultSet.getString(6);
    boolean active = resultSet.getBoolean(7);

    ScheduledTimeslot scheduledTimeslot =
        new ScheduledTimeslot(id, skillId, interviewerId, startTime, endTime, comment, active);
    scheduledTimeslots.add(scheduledTimeslot);
  }
}
