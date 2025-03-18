package com.allpay.projeto.model;
import java.math.BigInteger;
import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;

public class ModelFaturaDAO {

    DataBaseConnection conn;
    ArrayList<SimpleEntry<Integer, Float>> data;

    public ModelFaturaDAO() {

        this.conn = new MySQLDataBaseConnection();
        this.data = new ArrayList<>();
    }

    float result = 0;

    public ArrayList<SimpleEntry<Integer, Float>> buscarFaturasByUserId ( String id_usuarioOut) {

        String query = "SELECT * FROM fatura WHERE id_usuario = ?";

        try {

            conn.connect();

            PreparedStatement stmt = conn.getConnection().prepareStatement(query);
            stmt.setString(1, id_usuarioOut);

            ResultSet rs = stmt.executeQuery();

            //System.out.println(data.get(0).getKey());
            while (rs.next()) {

                String id_usuario = rs.getString("id_usuario");
                int id_fatura = rs.getInt("id_fatura");
                float valor_fatura = rs.getInt("valor_fatura");
                String nome_recebedor = rs.getString("nome_recebedor");
                String status_fatura = rs.getString("status_fatura");
                String descricao = rs.getString("descricao");

                int i = 0;
                i ++;

                System.out.println(i + " - " + id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);

                this.data.add(new SimpleEntry<>(id_fatura, valor_fatura));
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return this.data;
    }

    public ArrayList<SimpleEntry<Integer, Float>> buscarFaturasNoUser (int id_faturaOut) {

        String query = "SELECT * FROM fatura WHERE id_fatura = ? and WHERE id_usuario = null";

        try {

            conn.connect();
            conn.getConnection().createStatement().execute("USE allpay");

            PreparedStatement stmt = conn.getConnection().prepareStatement(query);
            stmt.setInt(1, id_faturaOut);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String id_usuario = rs.getString("id_usuario");
                int id_fatura = rs.getInt("id_fatura");
                float valor_fatura = rs.getInt("valor_fatura");
                String nome_recebedor = rs.getString("nome_recebedor");
                String status_fatura = rs.getString("status_fatura");
                String descricao = rs.getString("descricao");

                this.data.add(new SimpleEntry<>(id_fatura, valor_fatura));

                //System.out.println(id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return this.data;
    }

    public ArrayList<SimpleEntry<Integer, Float>> buscarFaturas (String id_usuarioOut) {

        String query = "SELECT * FROM fatura WHERE id_usuario = ?";

        try{
            conn.connect();
            conn.getConnection().createStatement().execute("USE allpay");

            PreparedStatement stmt = conn.getConnection().prepareStatement(query);
            stmt.setString(1, id_usuarioOut);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String id_usuario = rs.getString("id_usuario");
                int id_fatura = rs.getInt("id_fatura");
                float valor_fatura = rs.getFloat("valor_fatura");
                String nome_recebedor = rs.getString("nome_recebedor");
                String status_fatura = rs.getString("status_fatura");
                String descricao = rs.getString("descricao");

                this.data.add(new SimpleEntry<>(id_fatura, valor_fatura));

                //System.out.println(id_usuario + " " + id_fatura + " " + valor_fatura + " " + nome_recebedor + " " + status_fatura + " " + descricao);
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return this.data;
    }

    public float efetuarPagamento (String id_usuarioOut, int id_fatura, float valor_fatura, float saldo_usuario) {

        float saldo_restante = 0;

        saldo_restante =  saldo_usuario - this.data.get(id_fatura).getValue();

        return saldo_restante;
    }
}
