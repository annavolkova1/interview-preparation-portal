package com.epam.firstProject.dao.implementation;

import com.epam.firstProject.connection.ConnectionProvider;
import com.epam.firstProject.dao.UserSkillDao;
import com.epam.firstProject.dao.enums.SkillName;
import com.epam.firstProject.domain.UserSkill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserSkillDaoImpl implements UserSkillDao {

  final static Log logger = LogFactory.getLog(UserSkillDaoImpl.class);
  private static final String INSERT_USER_SKILL = "INSERT INTO user_skills (ID, SKILL_ID, USER_ID) VALUES (?, ?, ?)";
  private static final String SELECT_BY_USER_ID =
      "SELECT user_skills.id, user_skills.skill_id, user_skills.user_id, skills.id, skills.skill_name FROM user_skills LEFT JOIN skills ON user_skills.skill_id = skills.id WHERE user_skills.user_id = ?";
  private static final String DELETE_BY_USER_SKILLS_ID = "DELETE FROM user_skills WHERE id= ?";

  @Override
  public List<UserSkill> getUserSkills(long id) {

    List<UserSkill> userSkillsById = new ArrayList<>();

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)) {

      preparedStatement.setLong(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {

          long userId = resultSet.getLong(3);
          long skillId = resultSet.getLong(2);
          String skill = resultSet.getString(5);
          SkillName skillName = SkillName.valueOf(skill);
          UserSkill userSkillReceivedByUserId = new UserSkill(skillId, userId, skillName);
          userSkillsById.add(userSkillReceivedByUserId);
        }
      }
    }
    catch (SQLException | IllegalArgumentException throwable) {
      logger.error(throwable);
    }
    return userSkillsById;
  }

  @Override
  public boolean addUserSkill(UserSkill userSkill) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SKILL)) {

      getPreparedStatement(userSkill, preparedStatement);

      int rowsAdded = preparedStatement.executeUpdate();

      if (rowsAdded == 1) {
        return true;
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }

    return false;
  }

  @Override
  public boolean delete(long id) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_USER_SKILLS_ID)) {

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

  private void getPreparedStatement(UserSkill userSkill, PreparedStatement preparedStatement) {

    SkillName skillName = userSkill.getSkillName();

    try {
      preparedStatement.setLong(1, userSkill.getId());
      preparedStatement.setLong(2, skillName.getId());
      preparedStatement.setLong(3, userSkill.getUserId());
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
  }
}
