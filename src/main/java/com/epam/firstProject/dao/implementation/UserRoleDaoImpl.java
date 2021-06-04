package com.epam.firstProject.dao.implementation;

import com.epam.firstProject.connection.ConnectionProvider;
import com.epam.firstProject.dao.UserRoleDao;
import com.epam.firstProject.dao.enums.RoleName;
import com.epam.firstProject.domain.UserRole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserRoleDaoImpl implements UserRoleDao {

  final static Log logger = LogFactory.getLog(UserRoleDaoImpl.class);
  private static final String INSERT_USER_ROLE = "INSERT INTO user_roles (ID, USER_ROLE, USER_ID) VALUES (?, ?, ?)";
  private static final String UPDATE_USER_ROLE =
      "UPDATE user_roles SET ID = ?, USER_ROLE = ?, USER_ID = ? WHERE id = ?";
  private static final String SELECT_BY_USER_ID = "SELECT ID, USER_ROLE, USER_ID FROM user_roles WHERE USER_ID= ?";

  @Override
  public List<UserRole> getUserRoles(long id) {

    List<UserRole> userRolesById = new ArrayList<>();

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID)) {

      preparedStatement.setLong(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {

          RoleName roleName = RoleName.valueOf(resultSet.getString(2));
          long userId = resultSet.getLong(3);
          UserRole userRoleReceivedByUserId = new UserRole(id, roleName, userId);
          userRolesById.add(userRoleReceivedByUserId);
        }
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
    return userRolesById;
  }

  @Override
  public boolean addUserRole(UserRole userRole) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE)) {

      getPreparedStatement(userRole, preparedStatement);

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
  public boolean updateUserRole(UserRole userRole) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_ROLE)) {

      getPreparedStatement(userRole, preparedStatement);
      preparedStatement.setLong(4, userRole.getId());
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

  private void getPreparedStatement(UserRole userRole, PreparedStatement preparedStatement) {

    try {
      preparedStatement.setLong(1, userRole.getId());
      preparedStatement.setString(2, String.valueOf(userRole.getRoleName()));
      preparedStatement.setLong(3, userRole.getUserId());
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }
  }
}
