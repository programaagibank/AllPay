package com.allpay.projeto.model;

import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;
import com.allpay.projeto.interfaces.ModelDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserModelDAO implements ModelDAO {
    private DataBaseConnection dbConnect;
    @Override
    public ResultSet select() {
        try {
            dbConnect = new MySQLDataBaseConnection();
            dbConnect.connect();

            //sql teste
            String sql = "SELECT id_usuario, nome_usuario FROM usuario";
            //forca o uso do banco allpay
            dbConnect.getConnection().createStatement().execute("USE allpay");
            //prepare e executa o sql
            PreparedStatement statement = dbConnect.getConnection().prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                //pega as colunas
                String id = resultSet.getString("id_usuario");
                String nome = resultSet.getString("nome_usuario");

                // Imprimindo os dados
                System.out.println(id + " - " + nome);
            }
            resultSet.close();
            statement.close();
            dbConnect.connect();

        } catch (Exception e) {
            //mostra no console onde que deu o erro com base na execuçao
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void insert(String id_usuario, String nome_usuario, String senha_acesso, String email){
        String sql = "INSERT INTO usuario (id_usuario, nome_usuario, senha_acesso, email) VALUES (?, ?, ?, ?)";
        String use = "USE allpay;";
        try {
            MySQLDataBaseConnection conn = new MySQLDataBaseConnection();
            conn.connect();
            conn.getConnection().createStatement().execute(use);

            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);

            stmt.setString(1, id_usuario);
            stmt.setString(2, nome_usuario);
            stmt.setString(3, senha_acesso);
            stmt.setString(4, email);

            stmt.executeUpdate();
            System.out.println("Usuário salvo com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public Boolean selectById(String id, String senha){
        String sql = "SELECT * FROM usuario WHERE id_usuario = ? AND senha_acesso = ?";
        try {
            MySQLDataBaseConnection conn = new MySQLDataBaseConnection();
            conn.connect();

            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login bem-sucedido!");
                rs.close();
                stmt.close();
                conn.closeConnection();
                return true;
            } else {
                System.out.println("Credenciais inválidas. Tente novamente.");
                rs.close();
                stmt.close();
                conn.closeConnection();
                return false;
            }




        } catch (SQLException e) {
            System.out.println("Erro ao autenticar usuário: " + e.getMessage());
            return false;
        }

    }
}


