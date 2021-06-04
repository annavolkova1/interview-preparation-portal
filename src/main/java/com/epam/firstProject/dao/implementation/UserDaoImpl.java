package com.epam.firstProject.dao.implementation;

import com.epam.firstProject.connection.ConnectionProvider;
import com.epam.firstProject.dao.UserDao;
import com.epam.firstProject.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserDaoImpl implements UserDao {

  final static Log logger = LogFactory.getLog(UserDaoImpl.class);
  private static final String SELECT_ALL_USERS = "SELECT id, email, password, first_name, last_name FROM users";
  private static final String INSERT_USER =
      "INSERT INTO users (email, password, first_name, last_name) VALUES (?,?,?,?)";
  private static final String UPDATE_USER_BY_ID =
      "UPDATE users SET id = ?, email = ?, password = ?, first_name = ?, last_name = ? WHERE id = ?";
  private static final String SELECT_BY_EMAIL =
      "SELECT id, email, password, first_name, last_name FROM users WHERE email = ?";

  @Override
  public List<User> getUsers() {

    List<User> users = new ArrayList<>();

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS)) {

      while (resultSet.next()) {
        long id = resultSet.getLong(1);
        String email = resultSet.getString(2);
        String password = resultSet.getString(3);
        String firstName = resultSet.getString(4);
        String lastName = resultSet.getString(5);
        User user = new User(id, email, password, firstName, lastName);
        users.add(user);
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }

    return users;
  }

  private User extractUserFromResultSet(ResultSet resultSet) {

    User user = new User();
    try {
      long id = resultSet.getLong(1);
      String emailReceived = resultSet.getString(2);
      String password = resultSet.getString(3);
      String firstName = resultSet.getString(4);
      String lastName = resultSet.getString(5);
      user = new User(id, emailReceived, password, firstName, lastName);
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }

    return user;
  }

  @Override
  public User getByEmail(String email) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL)) {
      preparedStatement.setString(1, email);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        return extractUserFromResultSet(resultSet);
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }

    return null;
  }

  @Override
  public boolean createUser(User user) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {

      preparedStatement.setString(1, user.getEmail());
      preparedStatement.setString(2, user.getPassword());
      preparedStatement.setString(3, user.getFirstName());
      preparedStatement.setString(4, user.getLastName());
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
  public boolean updateUser(User user) {

    try (Connection connection = ConnectionProvider.getConnectionProvider().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_BY_ID)) {

      preparedStatement.setLong(1, user.getId());
      preparedStatement.setString(2, user.getEmail());
      preparedStatement.setString(3, user.getPassword());
      preparedStatement.setString(4, user.getFirstName());
      preparedStatement.setString(5, user.getLastName());
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
}
