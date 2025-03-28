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
  public ArrayList<HashMap<String,String>> findUserBankAccount(String id){
    String sql = """
            SELECT ib.nome_instituicao, CONCAT(U.id_usuario, ' - ', ib.id_instituicao) AS conta, C.limite, C.saldo_usuario
            FROM usuario U
            INNER JOIN conta C ON C.id_usuario = U.id_usuario
            INNER JOIN instituicao_bancaria ib ON ib.id_instituicao = C.id_instituicao
            WHERE U.id_usuario = ?;
            """;

    ArrayList<HashMap<String,String>> bancosDisponiveis = new ArrayList();

    try {
      dbConnect.connect();

      PreparedStatement stmt = dbConnect.getConnection().prepareStatement(sql);
      stmt.setString(1, id);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        HashMap<String, String> dados = new HashMap<>();
        dados.put("nome_instituicao", rs.getString("nome_instituicao"));
        dados.put("conta", rs.getString("conta"));
        dados.put("limite", rs.getString("limite"));
        dados.put("saldo_usuario", rs.getString("saldo_usuario"));
        bancosDisponiveis.add(dados);
      }

      rs.close();
      stmt.close();
      dbConnect.closeConnection();

    } catch (SQLException e) {
      System.out.println("Erro ao buscar");
    }
    return bancosDisponiveis;
  }

  public float escolherBancoCartao (String id_usuario, int id_instituicao) {

    String query = "SELECT limite FROM conta WHERE id_instituicao = ? and id_usuario = ?";

    float saldo = 0;

    try {

      dbConnect.connect();
      dbConnect.getConnection().createStatement().execute("USE allpay");

      PreparedStatement stmt = dbConnect.getConnection().prepareStatement(query);
      stmt.setInt(1, id_instituicao);
      stmt.setString(2, id_usuario);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        saldo = rs.getFloat("limite");
      } else {
        // Caso o resultado esteja vazio, você pode definir um valor padrão ou lançar um erro
        System.out.println("Nenhum limite encontrado para o usuário e instituição fornecidos.");
      }

    } catch (SQLException e) {

      e.printStackTrace();
    }

    return saldo;
  }

  public float escolherBanco (String id_usuario, int id_instituicao) {
    System.out.println("escolherbanco");
    String query = "SELECT saldo_usuario FROM conta WHERE id_instituicao = ? and id_usuario = ?";

    float saldo = 0;

    try {

      dbConnect.connect();
      dbConnect.getConnection().createStatement().execute("USE allpay");

      PreparedStatement stmt = dbConnect.getConnection().prepareStatement(query);
      stmt.setInt(1, id_instituicao);
      stmt.setString(2, id_usuario);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        saldo = rs.getFloat("saldo_usuario");
      } else {
        // Caso o resultado esteja vazio, você pode definir um valor padrão ou lançar um erro
        System.out.println("Nenhum saldo encontrado para o usuário e instituição fornecidos.");
      }

    } catch (SQLException e) {

      e.printStackTrace();
    }

    return saldo;
  }

  public boolean validarSenha (String senha_transacao, String id_usuario, int id_instituicao) {

    String query = "SELECT senha_transacao FROM conta WHERE id_instituicao = ? and id_usuario = ?";
    String senha_transacao_bd = "";

    try {

      dbConnect.connect();
      dbConnect.getConnection().createStatement().execute("USE allpay");

      PreparedStatement stmt = dbConnect.getConnection().prepareStatement(query);
      stmt.setInt(1, id_instituicao);
      stmt.setString(2, id_usuario);

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {

        senha_transacao_bd = rs.getString("senha_transacao");
      } else {

        System.out.println("Senha não encontrada");
      }


    } catch (SQLException e) {

      e.printStackTrace();
    }

      boolean validacao = false;

    if (senha_transacao.equals(senha_transacao_bd)) {

      validacao = true;
    } else System.out.println("Senha inválida");

    return validacao;
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

  public void limiteUpdate (float saldo_restante, String id_usuarioOut) {

    String query = "UPDATE conta SET limite = ? WHERE id_usuario = ?";

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
