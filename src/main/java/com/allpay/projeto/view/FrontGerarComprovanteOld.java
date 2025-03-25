package com.allpay.projeto.view;

import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FrontGerarComprovanteOld {
    static final String URL = System.getenv("DB_URL");
    static final String USER = System.getenv("DB_USER");
    static final String PASSWORD = System.getenv("DB_PASSWORD");
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";

    public static void main(String[] args) {
        try {
            DataBaseConnection dbConnect = new MySQLDataBaseConnection(URL, USER, PASSWORD);
            dbConnect.connect();
            dbConnect.getConnection().createStatement().execute("USE allpay");

            int idPagamento = 1;

            String sql = """
                SELECT 
                    p.valor_pago, 
                    p.data_pagamento, 
                    p.tipo_pagamento, 
                    u.nome_usuario, 
                    f.nome_recebedor, 
                    f.descricao, 
                    f.data_vencimento,
                    ib.nome_instituicao
                FROM pagamento p
                JOIN fatura f ON p.id_fatura = f.id_fatura
                JOIN usuario u ON f.id_usuario = u.id_usuario
                JOIN conta c ON u.id_usuario = c.id_usuario
                JOIN instituicao_bancaria ib ON c.id_instituicao = ib.id_instituicao
                WHERE p.id_pagamento = ?;
            """;

            PreparedStatement statement = dbConnect.getConnection().prepareStatement(sql);
            statement.setInt(1, idPagamento);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double valorPago = resultSet.getDouble("valor_pago");
                String dataPagamento = resultSet.getString("data_pagamento");
                String tipoPagamento = resultSet.getString("tipo_pagamento");
                String nomeUsuario = resultSet.getString("nome_usuario");
                String nomeRecebedor = resultSet.getString("nome_recebedor");
                String descricao = resultSet.getString("descricao");
                String dataVencimento = resultSet.getString("data_vencimento");
                String nomeInstituicao = resultSet.getString("nome_instituicao");

                System.out.println("═════ COMPROVANTE DE PAGAMENTO ═════");
                System.out.println("ID do Pagamento: " + idPagamento);
                System.out.println("Pago com: " + nomeInstituicao);
                System.out.println("════════════════════════════════════");
                System.out.println("Nome do Usuário: " + nomeUsuario);
                System.out.println("Nome do Recebedor: " + nomeRecebedor);
                System.out.println("Descrição: " + descricao);
                System.out.println("════════════════════════════════════");
                System.out.println("Valor Pago: R$ " + valorPago);
                System.out.println("Tipo de Pagamento: " + tipoPagamento);
                System.out.println("Data de Vencimento: " + dataVencimento);
                System.out.println("Data do Pagamento: " + dataPagamento);
                System.out.println("════════════════════════════════════");
            } else {
                System.out.println("Pagamento não encontrado para o ID: " + idPagamento);
            }

            resultSet.close();
            statement.close();
            dbConnect.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
