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
            this.dbConnect = new MySQLDataBaseConnection();
            this.dbConnect.connect();

            //sql teste
            String sql = "SELECT id_usuario, nome_usuario FROM usuario";
            //forca o uso do banco allpay
            dbConnect.getConnection().createStatement().execute("USE allpay");
            //prepare e executa o sql
            PreparedStatement statement = this.dbConnect.getConnection().prepareStatement(sql);

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
}
