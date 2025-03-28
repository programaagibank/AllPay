package com.allpay.projeto.viewOld;

import com.allpay.projeto.DAO.BankAccountDAO;
import com.allpay.projeto.DAO.FaturaDAO;
import com.allpay.projeto.controller.BankAccountController;
import com.allpay.projeto.controller.FaturaController;
import com.allpay.projeto.model.UserModel;


import java.util.InputMismatchException;
import java.util.Scanner;

public class FrontPagarFaturaOld {
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        BankAccountDAO bankAccountModel = new BankAccountDAO();
        FaturaController faturaController = new FaturaController();
        FaturaDAO modelFaturaDAO = new FaturaDAO();

        int selecaoFaturaPagar;
        int selecaoBancoPagar;
        int selecaoMetPagar;
        String senhaConta;
        float valor_usuario;
        float valor_update;

        System.out.println(AZUL + "╔════════════════════════════════════╗");
        System.out.println("║            Pagar Fatura            ║");
        System.out.println("╚════════════════════════════════════╝" + RESET);
        System.out.println();

        for (int i = 0; i < 15; i++) {
            System.out.print("\rBuscando Faturas" + ".".repeat(i % 4));
            Thread.sleep(400);
        }
        System.out.println("\r✔ Concluído!");
        System.out.println("Essas são suas faturas:\n");
        new FaturaController().buscarFaturasByUserId(UserModel.getId_usuario());
        System.out.println("Selecione qual deseja pagar:");
        selecaoFaturaPagar = sc.nextInt() - 1;
        sc.nextLine();
        System.out.println("\nBancos disponíveis:\n");
//        new BankAccountController().findUserBankAccount(UserModel.getId_usuario());
        System.out.println("\nSelecione qual banco deseja usar (Código do banco): ");
        selecaoBancoPagar = sc.nextInt() - 1;
        System.out.println("Selecione o método de pagamento:\nPIX - 1\nCrédito - 2\nDébito - 3\nTED - 4\nBoleto - 5\n");
        selecaoMetPagar = sc.nextInt() - 1;

        if (selecaoMetPagar >= 0 && selecaoMetPagar <= 4) {
            sc.nextLine();
            String metEscolhido = faturaController.escolherMetodoPag(selecaoMetPagar);
            System.out.println("Digite a senha da conta: ");
            senhaConta = sc.nextLine();
            System.out.println(metEscolhido);
            bankAccountModel.validarSenha(senhaConta, UserModel.getId_usuario(), Integer.parseInt(bankAccountModel.getBancosDisponiveis().get(selecaoBancoPagar).get("id_instituicao")));
            System.out.println(metEscolhido);

            if (!"CRÉDITO".equals(metEscolhido)) {

                valor_usuario = bankAccountModel.escolherBanco(UserModel.getId_usuario(), selecaoBancoPagar);
                valor_update = modelFaturaDAO.efetuarPagamento(UserModel.getId_usuario(), selecaoFaturaPagar, Float.parseFloat(modelFaturaDAO.data.get(selecaoFaturaPagar).get("valor_fatura")), valor_usuario, senhaConta, selecaoBancoPagar);
//                bankAccountModel.saldoUpdate(valor_update, UserModel.getId_usuario());
                System.out.println("PAGAMENTO FEITO!!!!");
            }


            if ("CRÉDITO".equals(metEscolhido)) {

                valor_usuario = bankAccountModel.escolherBancoCartao(UserModel.getId_usuario(), selecaoBancoPagar);
                valor_update = modelFaturaDAO.efetuarPagamentoCartao(UserModel.getId_usuario(), selecaoFaturaPagar, Float.parseFloat(modelFaturaDAO.data.get(selecaoFaturaPagar).get("valor_fatura")), valor_usuario, senhaConta, selecaoBancoPagar);
                bankAccountModel.limiteUpdate(valor_update, UserModel.getId_usuario());
                System.out.println("PAGAMENTO FEITO!");
            }

            modelFaturaDAO.atualizarStatus_fatura(selecaoFaturaPagar);
        }

        else System.out.println("ERRO: Método inválido");

    }
}
