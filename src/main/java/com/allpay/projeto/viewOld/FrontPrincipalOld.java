package com.allpay.projeto.viewOld;

import com.allpay.projeto.controller.User;
import com.allpay.projeto.controller.UserController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class FrontPrincipalOld {
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public User user;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(AZUL + "╔═════════════════════════╗");
        System.out.println("║         allPay          ║");
        System.out.println("╚═════════════════════════╝" + RESET);
        System.out.println();
        int opcaoEntrada = 0;

        do {
            try {
                System.out.println(AZUL + "╔═════════════════════════════╗");
                System.out.println("║ 1. Pagar fatura             ║");
                System.out.println("║ 2. Buscar fatura            ║");
                System.out.println("║ 3. Informações allPay       ║");
                System.out.println("║ 4. Sair                     ║");
                System.out.println("╚═════════════════════════════╝"+ RESET);
                System.out.print("Escolha uma opção: ");
                opcaoEntrada = sc.nextInt();
                sc.nextLine();

                if (opcaoEntrada == 1) {
                    System.out.println("Você escolheu Pagar fatura. Vamos te redirecionar.");
                    FrontPagarFaturaOld.main(args);
                } else if (opcaoEntrada == 2) {
                    System.out.println("Você escolheu Buscar fatura. Vamos te redirecionar.");
                    FrontBuscarFaturaOld.main(args);
                } else if (opcaoEntrada == 3) {
                    System.out.println("Você escolheu Informações allPay. Vamos te redirecionar.");
                } else if (opcaoEntrada == 4) {
                    UserController.exit();
                }
                else {
                    System.out.println("Opção inválida! Tente novamente.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida! Digite um número entre 1 e 4.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                sc.nextLine();
            }

        } while (opcaoEntrada != 4);


        sc.close();
    }
}