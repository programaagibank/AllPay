package com.allpay.projeto.view;

import com.allpay.projeto.controller.FaturaController;
import com.allpay.projeto.controller.User;
import com.allpay.projeto.model.ModelFaturaDAO;

import java.util.ArrayList;
import java.util.Scanner;

public class FrontBuscarFaturaOld {
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println(AZUL + "╔════════════════════════════════════╗");
        System.out.println("║            Buscar Fatura           ║");
        System.out.println("╚════════════════════════════════════╝" + RESET);
        System.out.println();
        // controller que tem a chamada da model fatura dao
        // scann para ler
        new FaturaController().buscarFaturasByUserId(User.getId_usuario());
        sc.nextLine();
        System.out.println("Digite o id de pagamento da fatura: ");
    }
}