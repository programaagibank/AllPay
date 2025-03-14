package com.allpay.projeto.model;
import java.math.BigInteger;
import java.sql.*;

import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;

public class ModelFaturaDAO {

    String url;
    String user;
    String password;
    Connection conn;

    public ModelFaturaDAO() {

        this.url = System.getenv("DB_URL");
        this.user = System.getenv("DB_USER");
        this.password = System.getenv("DB_PASSWORD");
    }

    float result = 0;

    public float buscarFaturasByUserId ( String id_usuarioOut) {

        String query = "SELECT * fatura WHERE id_usuario = ?";

        try {

            conn = DriverManager.getConnection(url, user, password);
            conn.createStatement().execute("USE allpay");

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id_usuarioOut);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String id_usuario = rs.getString("id_usuario");
                int id_fatura = rs.getInt("id_fatura");
                int valor_fatura = rs.getInt("valor_fatura");
                String nome_recebedor = rs.getString("nome_recebedor");
                String status_fatura = rs.getString("status_fatura");
                String descricao = rs.getString("descricao");

                result = valor_fatura;
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return result;
    }

    public float buscarFaturasNoUser (int id_faturaOut) {

        String query = "SELECT * FROM fatura WHERE id_fatura = ? and WHERE id_usuario = null";

        try {

            conn = DriverManager.getConnection(url, user, password);
            conn.createStatement().execute("USE allpay");

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_faturaOut);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String id_usuario = rs.getString("id_usuario");
                int id_fatura = rs.getInt("id_fatura");
                int valor_fatura = rs.getInt("valor_fatura");
                String nome_recebedor = rs.getString("nome_recebedor");
                String status_fatura = rs.getString("status_fatura");
                String descricao = rs.getString("descricao");

                result = valor_fatura;

                //System.out.println(id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return result;
    }

    public float buscarFaturas (String id_usuarioOut) {

        String query = "SELECT * FROM fatura WHERE id_usuario = ?";

        try{
            conn = DriverManager.getConnection(url, user, password);
            conn.createStatement().execute("USE allpay");

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id_usuarioOut);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String id_usuario = rs.getString("id_usuario");
                int id_fatura = rs.getInt("id_fatura");
                float valor_fatura = rs.getFloat("valor_fatura");
                String nome_recebedor = rs.getString("nome_recebedor");
                String status_fatura = rs.getString("status_fatura");
                String descricao = rs.getString("descricao");

                result = valor_fatura;

                //System.out.println(id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return result;
    }
}
