package com.allpay.projeto.model;

import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public class BankAccountModelDAO {
  private static DataBaseConnection dbConnect;

  public BankAccountModelDAO(){
    dbConnect = new MySQLDataBaseConnection();
  }
  public HashMap<String, String> findUserBankAccount(String id){
    String sql = """
            SELECT ib.nome_instituicao, CONCAT(U.id_usuario, ' - ', ib.id_instituicao) AS conta, C.limite, C.saldo_usuario
            FROM usuario U
            INNER JOIN conta C ON C.id_usuario = U.id_usuario
            INNER JOIN instituicao_bancaria ib ON ib.id_instituicao = C.id_instituicao
            WHERE U.id_usuario = ?;
            """;
    HashMap<String, String> dados = new HashMap<>();

    try {
      dbConnect.connect();

      PreparedStatement stmt = dbConnect.getConnection().prepareStatement(sql);
      stmt.setString(1, id);

      ResultSet rs = stmt.executeQuery();

      if(rs.next()) {
        dados.put("nome_instituicao", rs.getString("nome_instituicao"));
        dados.put("conta", rs.getString("conta"));
        dados.put("limite", rs.getString("limite"));
        dados.put("saldo_usuario", rs.getString("saldo_usuario"));
      } else {
        System.out.println("Voce nao tem cadastro em nenhuma parceira");
      }
      rs.close();
      stmt.close();
      dbConnect.closeConnection();

    } catch (SQLException e) {
      System.out.println("Erro ao buscar");
    }
    return dados;
  }

  public void saldoUpdate (float saldo_restante, String id_usuarioOut) {

    String query = "UPDATE conta SET saldo_usuario = ? WHERE id_usuario = ?";

    if (saldo_restante != 0) {
      try {

        dbConnect.connect();
        dbConnect.getConnection().createStatement().execute("USE allpay");

        PreparedStatement stmt = dbConnect.getConnection().prepareStatement(query);
        stmt.setFloat(1, saldo_restante);
        stmt.setString(2, id_usuarioOut);

        stmt.executeUpdate();

      } catch (SQLException e) {

        e.printStackTrace();
      }
    }
  }
}
