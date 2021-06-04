package com.epam.firstProject.dao.implementation;

import com.epam.firstProject.connection.ConnectionProvider;
import com.epam.firstProject.dao.InterviewDao;
import com.epam.firstProject.domain.Interview;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InterviewDaoImpl implements InterviewDao {

  final static Log logger = LogFactory.getLog(InterviewDaoImpl.class);

  private static final String INSERT =
      "INSERT INTO interviews (SKILL_ID, INTERVIEWEE_ID, INTERVIEWER_ID, START_TIME, END_TIME, COMMENT) "
          + "VALUES(?, ?, ?, ?, ?, ?)";
  private static final String UPDATE =
      "UPDATE interviews SET ID = ?, SKILL_ID = ?, INTERVIEWEE_ID = ?, INTERVIEWER_ID = ?, START_TIME = ?, "
          + "END_TIME = ?, COMMENT = ? WHERE ID = ?";
  private static final String SELECT_OVER_THE_PERIOD =
      "SELECT ID, SKILL_ID, INTERVIEWEE_ID, INTERVIEWER_ID, START_TIME, END_TIME, COMMENT FROM interviews "
          + "WHERE START_TIME BETWEEN ? AND ?";
  private static final String SELECT_BY_INTERVIEWEE_ID =
      "SELECT ID, SKILL_ID, INTERVIEWEE_ID, INTERVIEWER_ID, START_TIME, END_TIME, COMMENT FROM interviews "
          + "WHERE INTERVIEWEE_ID = ? AND START_TIME BETWEEN ? AND ?";
  private static final String SELECT_BY_INTERVIEWER_ID =
      "SELECT ID, SKILL_ID, INTERVIEWEE_ID, INTERVIEWER_ID, START_TIME, END_TIME, COMMENT FROM interviews "
          + "WHERE INTERVIEWER_ID = ? AND START_TIME BETWEEN ? AND ?";
  private static final String DELETE_BY_ID = "DELETE FROM interviews WHERE ID = ?";

  @Override
  public List<Interview> getInterviewsByIntervieweeId(Long intervieweeId, Timestamp start, Timestamp end) {

    List<Interview> interviewByIntervieweeIdList = new ArrayList<>();

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_INTERVIEWEE_ID)) {
      preparedStatement.setLong(1, intervieweeId);
      preparedStatement.setTimestamp(2, start);
      preparedStatement.setTimestamp(3, end);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          Long id = resultSet.getLong(1);
          Long skillId = resultSet.getLong(2);
          Long interviewerId = resultSet.getLong(4);
          Timestamp startTime = resultSet.getTimestamp(5);
          Timestamp endTime = resultSet.getTimestamp(6);
          String comment = resultSet.getString(7);

          Interview interview = new Interview(id, intervieweeId, skillId, interviewerId, startTime, endTime, comment);
          interviewByIntervieweeIdList.add(interview);
        }
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
    return interviewByIntervieweeIdList;
  }

  @Override
  public List<Interview> getInterviewsByInterviewerId(Long interviewerId, Timestamp start, Timestamp end) {

    List<Interview> interviewByInterviewerIdList = new ArrayList<>();

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_INTERVIEWER_ID)) {
      preparedStatement.setLong(1, interviewerId);
      preparedStatement.setTimestamp(2, start);
      preparedStatement.setTimestamp(3, end);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {
          Long id = resultSet.getLong(1);
          Long skillId = resultSet.getLong(2);
          Long intervieweeId = resultSet.getLong(3);
          Timestamp startTime = resultSet.getTimestamp(5);
          Timestamp endTime = resultSet.getTimestamp(6);
          String comment = resultSet.getString(7);
          Interview interview = new Interview(id, intervieweeId, skillId, interviewerId, startTime, endTime, comment);
          interviewByInterviewerIdList.add(interview);
        }
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
    return interviewByInterviewerIdList;
  }

  @Override
  public List<Interview> getInterviewsOverThePeriod(Timestamp start, Timestamp end) {

    List<Interview> interviewList = new ArrayList<>();

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OVER_THE_PERIOD)) {
      preparedStatement.setTimestamp(1, start);
      preparedStatement.setTimestamp(2, end);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {

        while (resultSet.next()) {

          Long id = resultSet.getLong(1);
          Long skillId = resultSet.getLong(2);
          Long intervieweeId = resultSet.getLong(3);
          Long interviewerId = resultSet.getLong(4);
          Timestamp startTime = resultSet.getTimestamp(5);
          Timestamp endTime = resultSet.getTimestamp(6);
          String comment = resultSet.getString(7);

          Interview interview = new Interview(id, skillId, intervieweeId, interviewerId, startTime, endTime, comment);

          interviewList.add(interview);
        }
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }

    return interviewList;
  }

  @Override
  public void createInterview(Interview interview) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

      preparedStatement.setLong(1, interview.getSkillId());
      preparedStatement.setLong(2, interview.getIntervieweeId());
      preparedStatement.setLong(3, interview.getInterviewerId());
      preparedStatement.setTimestamp(4, interview.getStartTime());
      preparedStatement.setTimestamp(5, interview.getEndTime());
      preparedStatement.setString(6, interview.getComment());

      int rowsAdded = preparedStatement.executeUpdate();
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
  }

  @Override
  public boolean updateInterview(Interview interview) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

      preparedStatement.setLong(1, interview.getId());
      preparedStatement.setLong(2, interview.getSkillId());
      preparedStatement.setLong(3, interview.getIntervieweeId());
      preparedStatement.setLong(4, interview.getInterviewerId());
      preparedStatement.setTimestamp(5, interview.getStartTime());
      preparedStatement.setTimestamp(6, interview.getEndTime());
      preparedStatement.setString(7, interview.getComment());
      preparedStatement.setLong(8, interview.getId());

      int rowsUpdated = preparedStatement.executeUpdate();

      if (rowsUpdated == 1) {
        return true;
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }

    return false;
  }

  @Override
  public boolean deleteById(Long id) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

      preparedStatement.setLong(1, id);
      int rowsDeleted = preparedStatement.executeUpdate();

      if (rowsDeleted == 1) {
        return true;
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }

    return false;
  }
}
