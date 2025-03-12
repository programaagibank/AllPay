package com.allpay.projeto.dbConnection;

import com.allpay.projeto.interfaces.DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDataBaseConnection implements DataBaseConnection {
  private Connection connection;
  private String url;
  private String user;
  private String password;
  public MySQLDataBaseConnection(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public MySQLDataBaseConnection() {

    this.url = System.getenv("DB_URL");
    this.user = System.getenv("DB_USER");
    this.password = System.getenv("DB_PASSWORD");
  }

  @Override
  public void connect() throws SQLException {
    this.connection = DriverManager.getConnection(url, user, password);
  }

  @Override
  public void closeConnection() throws SQLException {
    // Verifica se a conexão está aberta e tenta fechá-la
    if (this.connection != null && !this.connection.isClosed()) {
      this.connection.close();
      System.out.println("Conexão fechada com sucesso!");
    }
  }

  @Override
  public Connection getConnection() {
    return this.connection;
  }
}
