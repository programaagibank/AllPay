package com.allpay.projeto.model;
import java.math.BigInteger;
import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;

public class ModelFaturaDAO {

    DataBaseConnection conn;
    public ArrayList<HashMap<String,String>> data;

    public ModelFaturaDAO() {

        this.conn = new MySQLDataBaseConnection();
        this.data = new ArrayList<>();
    }

    public ArrayList<HashMap<String,String>> getData () {

        return data;
    }

    float result = 0;

    public ArrayList<HashMap<String,String>> buscarFaturasByUserId ( String id_usuarioOut) {

        String query = "SELECT * FROM fatura WHERE id_usuario = ? and status_fatura <> 'PAGA'";

        try {

            conn.connect();

            PreparedStatement stmt = conn.getConnection().prepareStatement(query);
            stmt.setString(1, id_usuarioOut);

            ResultSet rs = stmt.executeQuery();
            //System.out.println(data.get(0).getKey());
            while (rs.next()) {

                HashMap<String,String> dados = new HashMap<>();
                dados.put("id_usuario", rs.getString("id_usuario"));
                dados.put("id_fatura", rs.getString("id_fatura"));
                dados.put("valor_fatura", rs.getString("valor_fatura"));
                dados.put("nome_recebedor", rs.getString("nome_recebedor"));
                dados.put("status_fatura", rs.getString("status_fatura"));
                dados.put("descricao", rs.getString("descricao"));
                System.out.println(dados.put("id_fatura", rs.getString("id_fatura")));
                //System.out.println(i + " - " + id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);

                this.data.add(dados);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return this.data;
    }

    public ArrayList<HashMap<String,String>> buscarFaturasNoUser (int id_faturaOut) {

        String query = "SELECT * FROM fatura WHERE id_fatura = ? and WHERE id_usuario = null";

        try {

            conn.connect();
            conn.getConnection().createStatement().execute("USE allpay");

            PreparedStatement stmt = conn.getConnection().prepareStatement(query);
            stmt.setInt(1, id_faturaOut);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                HashMap<String,String> dados = new HashMap<>();
                dados.put("id_usuario", rs.getString("id_usuario"));
                dados.put("id_fatura", rs.getString("id_fatura"));
                dados.put("valor_fatura", rs.getString("valor_fatura"));
                dados.put("nome_recebedor", rs.getString("nome_recebedor"));
                dados.put("status_fatura", rs.getString("status_fatura"));
                dados.put("descricao", rs.getString("descricao"));

                //System.out.println(i + " - " + id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);

                this.data.add(dados);

                //System.out.println(id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return this.data;
    }

    public ArrayList<HashMap<String,String>> buscarFaturas (String id_usuarioOut) {

        String query = "SELECT * FROM fatura WHERE id_usuario = ?";

        try{
            conn.connect();
            conn.getConnection().createStatement().execute("USE allpay");

            PreparedStatement stmt = conn.getConnection().prepareStatement(query);
            stmt.setString(1, id_usuarioOut);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                HashMap<String,String> dados = new HashMap<>();
                dados.put("id_usuario", rs.getString("id_usuario"));
                dados.put("id_fatura", rs.getString("id_fatura"));
                dados.put("valor_fatura", rs.getString("valor_fatura"));
                dados.put("nome_recebedor", rs.getString("nome_recebedor"));
                dados.put("status_fatura", rs.getString("status_fatura"));
                dados.put("descricao", rs.getString("descricao"));

                //System.out.println(i + " - " + id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);

                this.data.add(dados);

                //System.out.println(id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return this.data;
    }

    public float efetuarPagamentoCartao (String id_usuarioOut, int id_fatura, float valor_fatura, float limite_usuario, String senha_transacao, int id_instituicao) {

        BankAccountModelDAO conta = new BankAccountModelDAO();
        float limite_restante = 0;
        boolean validacao = conta.validarSenha(senha_transacao, id_usuarioOut, id_instituicao);
        float valor_faturaConvert = Float.parseFloat(data.get(id_fatura).get("valor_fatura"));
        int id_faturaConvert = Integer.parseInt(data.get(id_fatura).get("id_fatura"));

        if (limite_usuario >= valor_faturaConvert && validacao == true) {

            limite_restante = limite_usuario - valor_faturaConvert;

            atualizarStatus_fatura(id_faturaConvert);
        } else {

            System.out.println("Transação negada!");
        }

        return limite_restante;
    }

    public float efetuarPagamento (String id_usuarioOut, int id_fatura, float valor_fatura, float saldo_usuario, String senha_transacao, int id_instituicao) {
        System.out.println("aqui");
        BankAccountModelDAO conta = new BankAccountModelDAO();
        float saldo_restante = 0;
        boolean validacao = conta.validarSenha(senha_transacao, id_usuarioOut, id_instituicao);
        float valor_faturaConvert = Float.parseFloat(data.get(id_fatura).get("valor_fatura"));
        int id_faturaConvert = Integer.parseInt(data.get(id_fatura).get("id_fatura"));

        if (saldo_usuario >= valor_faturaConvert && validacao == true) {

            saldo_restante = saldo_usuario - valor_faturaConvert;

            atualizarStatus_fatura(id_faturaConvert);
        } else {

            System.out.println("Transação negada!");
        }

        return saldo_restante;
    }

    public void atualizarStatus_fatura (int id_fatura) {

        String query = "UPDATE fatura SET status_fatura = 'PAGA' WHERE id_fatura = ?";

        try {

            conn.connect();
            conn.getConnection().createStatement().execute("USE allpay");

            PreparedStatement stmt = conn.getConnection().prepareStatement(query);

            stmt.setInt(1, id_fatura);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
