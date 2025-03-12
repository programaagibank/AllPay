package com.allpay.projeto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection2 {
    Connection conn;
    private String URL;
    private String USER;
    private String PASSWORD;
    public DatabaseConnection2(){
        this.URL = System.getenv("DB_URL");
        this.USER = System.getenv("DB_USER");
        this.PASSWORD = System.getenv("DB_PASSWORD");
    }
    public void connect() throws SQLException {
        this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public Connection getConn(){
        return this.conn;
    }
}
