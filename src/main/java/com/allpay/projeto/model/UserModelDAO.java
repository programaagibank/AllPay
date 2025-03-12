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
        ResultSet resultSet = null;
        try {
            this.dbConnect = new MySQLDataBaseConnection();

            this.dbConnect.connect();
            System.out.println("Conexao feita");
            //sql teste
            String sql = "SELECT id_usuario, nome_usuario FROM usuario";
            //forca o uso do banco allpay
            dbConnect.getConnection().createStatement().execute("USE allpay");
            //prepare e executa o sql
            PreparedStatement statement = this.dbConnect.getConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();

            //itera e printa cada linha da tabela
            //result.next vem como um ponteiro para cada linha da coluna

            resultSet.close();
            statement.close();
            this.dbConnect.closeConnection();
//            System.out.println("Conexao finalizada");
//            return resultSet;
        } catch (Exception e) {
            //mostra no console onde que deu o erro com base na execuçao
            System.out.println("Erro no SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return resultSet;
    }
}
