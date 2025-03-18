package com.allpay.projeto.view;

import java.util.InputMismatchException;
import java.util.Scanner;
    public class FrontEntrada {
        public static final String RESET = "\u001B[0m";
        public static final String AZUL = "\u001B[34m";

        public static void main(String[] args) throws InterruptedException {
           
            System.out.println("Olá! Você está usando o ");
            System.out.println(AZUL + "████████████▓   ▓█   ▒█░  ▒█████████████████████████");
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
                System.out.print(AZUL + "\r[" + "█".repeat(i) + " ".repeat(20 - i) + "] " + progress + "%");
                Thread.sleep(200);
            }
            System.out.println(RESET + "\n✔ Carregamento concluído!\n");

            Scanner sc = new Scanner(System.in);
            int opcaoEntrada = 0;

            do {
                try {
                    System.out.println(AZUL + "╔══════════════════╗");
                    System.out.println("║ 1. Login         ║");
                    System.out.println("║ 2. Cadastro      ║");
                    System.out.println("║ 3. Sair          ║");
                    System.out.println("╚══════════════════╝"+ RESET);
                    System.out.print("Escolha uma opção: ");
                    opcaoEntrada = sc.nextInt();

                    if (opcaoEntrada == 1) {
                        System.out.println("Você escolheu Login. Vamos te redirecionar.");
                        FrontLogin.main(args);
                    } else if (opcaoEntrada == 2) {
                        System.out.println("Você escolheu Cadastro. Vamos te redirecionar.");
                        FrontSignUp.main(args);
                    } else if (opcaoEntrada == 3) {
                        System.out.println("Você escolheu Sair. Obrigado por usar o allPay.");
                    } else {
                        System.out.println("Opção inválida! Tente novamente.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Erro: Entrada inválida! Digite um número inteiro.");
                    break;
                } catch (Exception e) {
                    System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
                    break;
                }

            } while (opcaoEntrada != 3);

            sc.close();
        }
    }
