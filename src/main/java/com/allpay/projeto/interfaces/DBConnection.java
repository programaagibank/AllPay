package com.allpay.projeto.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnection {

  void connect() throws SQLException;

  void closeConnect() throws SQLException;

  Connection getConnect();

}
