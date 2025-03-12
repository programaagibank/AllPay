package com.allpay.projeto.view;

import java.util.InputMismatchException;
import java.util.Scanner;
    public class FrontEntrada {
        public static final String RESET = "\u001B[0m";
        public static final String GREEN = "\u001B[32m";

        public static void main(String[] args) throws InterruptedException {
            // ASCII Art em verde
            System.out.println("Olá! Você está usando o ");
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
                    // Apenas as opções dentro da box ficam verdes
                    System.out.println(GREEN + "╔══════════════════╗");
                    System.out.println("║ 1. Login         ║");
                    System.out.println("║ 2. Cadastro      ║");
                    System.out.println("║ 3. Sair          ║");
                    System.out.println("╚══════════════════╝"+ RESET);
                    System.out.print("Escolha uma opção: ");

                    opcaoEntrada = sc.nextInt();

                    if (opcaoEntrada == 1) {
                        System.out.println("Você escolheu Login. Vamos te redirecionar.");
                    } else if (opcaoEntrada == 2) {
                        System.out.println("Você escolheu Cadastro. Vamos te redirecionar.");
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
    }
