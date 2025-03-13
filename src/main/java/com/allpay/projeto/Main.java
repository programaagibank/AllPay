package com.allpay.projeto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();
        Cadastro cadastro = new Cadastro();

        System.out.println("Bem-vindo ao sistema AllPay!");
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Login");
        System.out.println("2 - Cadastro");

        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                if (login.autenticar()) {
                    System.out.println("Acesso concedido!");
                } else {
                    System.out.println("Falha no login!");
                }
                break;
            case 2:
                cadastro.cadastrarUsuario();
                break;
            default:
                System.out.println("Opção inválida!");
        }

        sc.close();
    }
}
