import com.allpay.projeto.controller.UserController;
import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;
//import com.allpay.projeto.model.BankAccountModelDAO;
import com.allpay.projeto.model.BankAccountModelDAO;
import com.allpay.projeto.model.ModelFaturaDAO;
import com.allpay.projeto.model.UserModelDAO;

import java.sql.Connection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static final String URL = System.getenv("DB_URL");
    static final String USER = System.getenv("DB_USER");
    static final String PASSWORD = System.getenv("DB_PASSWORD");
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    Connection conn;


    public static void main(String[] args) throws InterruptedException {
        //GerarComprovantePagamento(1);
        //PaymentModelDAO.GerarComprovantePagamento();
        //FrontEntrada.main(args);
        //new UserController().select();
        //new ModelFaturaDAO().buscarFaturasByUserId("45678912345");

        //HashMap<String, String> teste = new BankAccountModelDAO().selectById("11223344556");
        //System.out.println(teste.get("nome_instituicao") + " | " + teste.get("conta") + " | " + teste.get("limite") + " | " + teste.get("saldo_usuario"));
        GerarComprovantePagamento();
    }

    public static void GerarComprovantePagamento(/*int VarIdPagamento*/) {
        try {
            DataBaseConnection dbConnect = new MySQLDataBaseConnection(URL, USER, PASSWORD);
            dbConnect.connect();
            dbConnect.getConnection().createStatement().execute("USE allpay");

//            int idPagamento = VarIdPagamento;
//
//            String sql = """
//                SELECT
//                    p.valor_pago,
//                    p.data_pagamento,
//                    p.tipo_pagamento,
//                    u.nome_usuario,
//                    f.nome_recebedor,
//                    f.descricao,
//                    f.data_vencimento,
//                    ib.nome_instituicao
//                FROM pagamento p
//                JOIN fatura f ON p.id_fatura = f.id_fatura
//                JOIN usuario u ON f.id_usuario = u.id_usuario
//                JOIN conta c ON u.id_usuario = c.id_usuario
//                JOIN instituicao_bancaria ib ON c.id_instituicao = ib.id_instituicao
//                WHERE p.id_pagamento = ?;
//            """;
//
//            PreparedStatement statement = dbConnect.getConnection().prepareStatement(sql);
//            statement.setInt(1, idPagamento);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                double valorPago = resultSet.getDouble("valor_pago");
//                String dataPagamento = resultSet.getString("data_pagamento");
//                String tipoPagamento = resultSet.getString("tipo_pagamento");
//                String nomeUsuario = resultSet.getString("nome_usuario");
//                String nomeRecebedor = resultSet.getString("nome_recebedor");
//                String descricao = resultSet.getString("descricao");
//                String dataVencimento = resultSet.getString("data_vencimento");
//                String nomeInstituicao = resultSet.getString("nome_instituicao");
//
//                System.out.println("═════ COMPROVANTE DE PAGAMENTO ═════");
//                System.out.println("ID do Pagamento: " + idPagamento);
//                System.out.println("Pago com: " + nomeInstituicao);
//                System.out.println("════════════════════════════════════");
//                System.out.println("Nome do Usuário: " + nomeUsuario);
//                System.out.println("Nome do Recebedor: " + nomeRecebedor);
//                System.out.println("Descrição: " + descricao);
//                System.out.println("════════════════════════════════════");
//                System.out.println("Valor Pago: R$ " + valorPago);
//                System.out.println("Tipo de Pagamento: " + tipoPagamento);
//                System.out.println("Data de Vencimento: " + dataVencimento);
//                System.out.println("Data do Pagamento: " + dataPagamento);
//                System.out.println("════════════════════════════════════");
//            } else {
//                System.out.println("Pagamento não encontrado para o ID: " + idPagamento);
//            }
//
//            resultSet.close();
//            statement.close();
//            dbConnect.closeConnection();


            Scanner sc = new Scanner(System.in);

            ModelFaturaDAO model = new ModelFaturaDAO();

            ArrayList<AbstractMap.SimpleEntry<Integer, Float>> entry = model.getData();

            BankAccountModelDAO conta = new BankAccountModelDAO();

            model.buscarFaturasByUserId("00000000001");

            System.out.println("Fatura q vc quer pagar: ");
            int id;

            String met = UserController.escolherMetodoPag(sc.nextInt());

            if (met.equals(""))
            float escolherBanco = conta.escolherBanco("00000000001", 3);
            float escolherBancoCartao = conta.escolherBancoCartao("00000000001", 3);
            float efetuarPagamentoCartao = model.efetuarPagamentoCartao("00000000001", id = sc.nextInt() - 1, model.data.get(id).getValue(), escolherBancoCartao, "77", 3);
            float efetuarPagamento = model.efetuarPagamento("00000000001", id = sc.nextInt() - 1, model.data.get(id).getValue(),  escolherBanco, "77", 3);
            conta.saldoUpdate(efetuarPagamento, "00000000001");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}