package com.allpay.projeto.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataBaseConnection {

  void connect() throws SQLException;

  void closeConnection() throws SQLException;

  Connection getConnection();

}
