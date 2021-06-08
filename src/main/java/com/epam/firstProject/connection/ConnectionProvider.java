package com.epam.firstProject.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectionProvider {

  FileInputStream fileInputStream;
  Properties property = new Properties();
  private final Connection connection;
  private static ConnectionProvider connectionProvider;
  static final Log logger = LogFactory.getLog(ConnectionProvider.class);

  private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
  private static final String PROPERTIES_PATH =
      "C:\\workspace\\interview-preparation-portal\\src\\main\\resources\\application.properties";

  private ConnectionProvider() {

    try {
      fileInputStream = new FileInputStream(PROPERTIES_PATH);
      property.load(fileInputStream);
    }
    catch (IOException e) {
      logger.error("Error access property file", e);
    }

    try {
      Class.forName(DRIVER_CLASS);
    }
    catch (ClassNotFoundException e) {
      logger.error("No suitable driver found", e);
    }

    try {
      this.connection = DriverManager.getConnection(property.getProperty("DB_URL"), property.getProperty("USER"),
          property.getProperty("PASS"));
    }
    catch (SQLException ex) {
      throw new RuntimeException("Error connecting to database", ex);
    }
  }

  public Connection getConnection() {

    return connection;
  }

  public static ConnectionProvider getConnectionProvider() throws SQLException {

    if (connectionProvider == null) {
      connectionProvider = new ConnectionProvider();
    }
    try {
      if (connectionProvider.getConnection()
          .isClosed()) {
        connectionProvider = new ConnectionProvider();
      }
    }
    catch (SQLException throwable) {
      logger.error(throwable);
    }

    return connectionProvider;
  }
}
