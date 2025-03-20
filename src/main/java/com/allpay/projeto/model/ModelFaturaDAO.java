package com.allpay.projeto.model;
import java.math.BigInteger;
import java.sql.*;

import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;

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

    public void buscarFaturasNoUser () {

        String query = "SELECT * FROM fatura WHERE id_fatura = 1";

        try {

            conn = DriverManager.getConnection(url, user, password);
            conn.createStatement().execute("USE allpay");

            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String id_usuario = rs.getString("id_usuario");
                int id_fatura = rs.getInt("id_fatura");
                int valor_fatura = rs.getInt("valor_fatura");
                String nome_recebedor = rs.getString("nome_recebedor");
                String status_fatura = rs.getString("status_fatura");
                String descricao = rs.getString("descricao");

                //System.out.println(id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void buscarFaturas (String id_usuarioOut) {

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
                int valor_fatura = rs.getInt("valor_fatura");
                String nome_recebedor = rs.getString("nome_recebedor");
                String status_fatura = rs.getString("status_fatura");
                String descricao = rs.getString("descricao");

                System.out.println("=================================");
                System.out.printf("Usuário: %s%n", id_usuario);
                System.out.printf("ID Fatura: %d%n", id_fatura);
                System.out.printf("Valor: R$ %,.2f%n", (double) valor_fatura);
                System.out.printf("Recebedor: %s%n", nome_recebedor);
                System.out.printf("Status: %s%n", status_fatura);
                System.out.printf("Descrição: %s%n", descricao);
                System.out.println("=================================");
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
