package com.allpay.projeto.DAO;

import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;
import com.allpay.projeto.interfaces.InterfaceUserModelDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserDAO implements InterfaceUserModelDAO {
    private static DataBaseConnection dbConnect;

    public UserDAO(){
        dbConnect = new MySQLDataBaseConnection();
    }
    public UserDAO(DataBaseConnection dbConnect) {
        this.dbConnect = dbConnect;
    }

    public void insert(String id_usuario, String nome_usuario, String senha_acesso, String email){
        String sql = "INSERT INTO usuario (id_usuario, nome_usuario, senha_acesso, email) VALUES (?, ?, ?, ?)";
        String use = "USE allpay;";
        try {

            dbConnect.connect();
            dbConnect.getConnection().createStatement().execute(use);

            PreparedStatement stmt = dbConnect.getConnection().prepareStatement(sql);

            stmt.setString(1, id_usuario);
            stmt.setString(2, nome_usuario);
            stmt.setString(3, senha_acesso);
            stmt.setString(4, email);


            stmt.executeUpdate();

            stmt.close();
            dbConnect.closeConnection();

            System.out.println("Usuário salvo com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public HashMap<String, String> selectById(String id, String senha){
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        HashMap<String, String> dados = new HashMap<>();

        try {

            dbConnect.connect();

            PreparedStatement stmt = dbConnect.getConnection().prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next() && verificarSenha(senha, rs.getString("senha_acesso"))) {
                dados.put("id_usuario", rs.getString("id_usuario"));
                dados.put("nome_usuario", rs.getString("nome_usuario"));
                dados.put("email", rs.getString("email"));
                dados.put("senha_acesso", rs.getString("senha_acesso"));
            } else {
                System.out.println("CPF ou Senha invalidos");
            }
            rs.close();
            stmt.close();
            dbConnect.closeConnection();

        } catch (SQLException e) {
            System.out.println("Desculpe Tente novamente");
        }
    return dados;
    }

    public static boolean verificarSenha(String senhaDigitada, String senhaHash) {
        return BCrypt.checkpw(senhaDigitada, senhaHash);
    }
}


