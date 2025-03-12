import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static final String URL = System.getenv("DB_URL");
    static final String USER = System.getenv("DB_USER");
    static final String PASSWORD = System.getenv("DB_PASSWORD");
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";

    public static void main(String[] args) throws InterruptedException {
        AcessoInicial();
        GerarComprovantePagamento(1);
    }

    public static void GerarComprovantePagamento(int VarIdPagamento) {
        try {
            DataBaseConnection dbConnect = new MySQLDataBaseConnection(URL, USER, PASSWORD);
            dbConnect.connect();
            dbConnect.getConnection().createStatement().execute("USE allpay");

            int idPagamento = VarIdPagamento;

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

    public static void AcessoInicial() throws InterruptedException {

        System.out.println(GREEN + "████████████▓   ▓█   ▒█░  ▒█████████████████████████");
        System.out.println("████████████▒   ▒█    █      ███████████████████████");
        System.out.println("██         ▓▒   ▒█    ███    ▓▓         ▒▒   ░█    ▓");
        System.out.println("█    ░░    ▓▒   ▒█    █      █    ██░   ▒█▒       ▒█");
        System.out.println("▓   ░██░   ▓▒   ░█    █   ░███    ▒▓    ▒██▒     ▒██");
        System.out.println("█░         ▓▒   ░█    █████████         ▒███░   ░███");
        System.out.println("████▓▓▓███████████████████████████████████     ░████");
        System.out.println("██████████████████████████████████████████   ▒██████" + RESET);

        System.out.println("\nCarregando...");
        for (int i = 0; i <= 20; i++) {
            int progress = (i * 100) / 20;
            System.out.print(GREEN + "\r[" + "█".repeat(i) + " ".repeat(20 - i) + "] " + progress + "%");
            Thread.sleep(200);
        }
        System.out.println(RESET + "\n✅ Carregamento concluído!\n");

        Scanner sc = new Scanner(System.in);
        int opcaoEntrada = 0;

        do {
            try {

                System.out.println(GREEN + "╔══════════════════╗");
                System.out.println("║ 1. Login         ║");
                System.out.println("║ 2. Cadastro      ║");
                System.out.println("║ 3. Sair          ║");
                System.out.println("╚══════════════════╝"+ RESET);
                System.out.print("Escolha uma opção: ");

                opcaoEntrada = sc.nextInt();

                if (opcaoEntrada == 1) {
                    System.out.println("Você escolheu Login. Vamos te redirecionar.");
                    Login();
                } else if (opcaoEntrada == 2) {
                    System.out.println("Você escolheu Cadastro. Vamos te redirecionar.");
                    SignUp();
                } else if (opcaoEntrada == 3) {
                    System.out.println("Você escolheu Sair. Obrigado por usar o allPay.");
                } else {
                    System.out.println("Opção inválida! Tente novamente.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida! Digite um número inteiro.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                sc.nextLine();
            }

        } while (opcaoEntrada != 3);

        sc.close();
    }

    public static void Login() {

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║               Login                ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();

        System.out.println("Digite o seu CPF ou CNPJ:");

        System.out.println("Digite sua senha:");
    }

    public static void SignUp() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║               Cadastro             ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();

        System.out.println("Digite o seu CPF ou CNPJ:");

        System.out.println("Digite o seu e-mail:");

        System.out.println("Digite sua senha:");

    }


}


