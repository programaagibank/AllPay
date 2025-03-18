package com.allpay.projeto.view;

import com.allpay.projeto.controller.UserController;

import java.util.Scanner;

public class FrontSignUp {
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(AZUL + "╔════════════════════════════════════╗");
        System.out.println("║               Cadastro             ║");
        System.out.println("╚════════════════════════════════════╝" + RESET);
        System.out.println();

        System.out.println("Digite o seu CPF ou CNPJ:");
        String cpfTemp = sc.nextLine();
        System.out.println("Digite o seu Nome");
        String nomeTemp = sc.nextLine();
        System.out.println("Digite o seu e-mail:");
        String emailTemp = sc.nextLine();

        System.out.println("Digite sua senha:");
        String senhaTemp = sc.nextLine();

//        instacia usercontroler new UserController.insert(cpf,nome,senha,email)
//        chama funcao de inserir e passa os dados para os parametros
        new UserController().insert(cpfTemp, nomeTemp, senhaTemp, emailTemp);

    }
}
