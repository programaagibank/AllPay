import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GerarComprovantePagamento {
    static final String URL = System.getenv("DB_URL");
    static final String USER = System.getenv("DB_USER");
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static void main(String[] args) {
        try {
            DataBaseConnection dbConnect = new MySQLDataBaseConnection(URL, USER, PASSWORD);
            dbConnect.connect();

            dbConnect.getConnection().createStatement().execute("USE allpay");

            int idPagamento = 1;

            String sql = "SELECT valor_pago, data_pagamento, tipo_pagamento FROM pagamento WHERE id_pagamento = ?";

            PreparedStatement statement = dbConnect.getConnection().prepareStatement(sql);
            statement.setInt(1, idPagamento);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double valorPago = resultSet.getDouble("valor_pago");
                String dataPagamento = resultSet.getString("data_pagamento");
                String tipoPagamento = resultSet.getString("tipo_pagamento");

                System.out.println("═════ COMPROVANTE DE PAGAMENTO ═════");
                System.out.println("ID do Pagamento: " + idPagamento);
                System.out.println("Valor Pago: R$ " + valorPago);
                System.out.println("Data do Pagamento: " + dataPagamento);
                System.out.println("Tipo de Pagamento: " + tipoPagamento);
                System.out.println("════════════════════════════════════");
            } else {
                System.out.println("Pagamento não encontrado para o ID: " + idPagamento);
            }

            resultSet.close();
            statement.close();
            dbConnect.closeConnection();
            System.out.println("Conexão finalizada");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}