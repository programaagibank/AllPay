package com.allpay.projeto.viewOld;

import com.allpay.projeto.controller.User;
import com.allpay.projeto.model.ModelFaturaDAO;

import java.util.Scanner;

public class FrontPagarFaturaOld {
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println(AZUL + "╔════════════════════════════════════╗");
        System.out.println("║            Pagar Fatura            ║");
        System.out.println("╚════════════════════════════════════╝" + RESET);
        System.out.println();

        for (int i = 0; i < 15; i++) {
            System.out.print("\rBuscando Faturas" + ".".repeat(i % 4));
            Thread.sleep(400);
        }
        System.out.println("\r✔ Concluído!");
        System.out.println("Essas são suas faturas:");
        new ModelFaturaDAO().buscarFaturas(User.getId_usuario());
        System.out.println("Selecione qual deseja pagar:");
        //preciso do metodo de pagamento pra terminar o front
    }


}
