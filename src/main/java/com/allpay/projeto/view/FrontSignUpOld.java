package com.allpay.projeto.view;

import com.allpay.projeto.controller.UserController;

import java.util.Scanner;

public class FrontSignUpOld {
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";

    public static void main(String[] args) {
        UserController userController = new UserController();
        Scanner sc = new Scanner(System.in);
        System.out.println(AZUL + "╔════════════════════════════════════╗");
        System.out.println("║               Cadastro             ║");
        System.out.println("╚════════════════════════════════════╝" + RESET);
        System.out.println();

        System.out.println("Digite o seu CPF ou CNPJ:");
        String  cpfTemp = sc.nextLine();
        cpfTemp = userController.validarId(cpfTemp);

        System.out.println("Digite o seu Nome");
        String nomeTemp = userController.validarNome(sc.nextLine());
        System.out.println("Digite o seu e-mail:");
        String emailTemp = userController.validarEmail(sc.nextLine());
        System.out.println("Digite sua senha:");
        String senhaTemp = userController.validarSenha(sc.nextLine());

        System.out.println("Confirme suas informações!");
        System.out.println("1 - Avançar\n2 - Recomeçar\n3 - Sair");

        int confirmacaoCadastro = userController.confirmarInfos(sc.nextInt());
        if (confirmacaoCadastro == 4) {
            while (confirmacaoCadastro != 1 && confirmacaoCadastro != 2 && confirmacaoCadastro != 3) {
                System.out.println("Confirme suas informações!");
                System.out.println("1 - Avançar\n2 - Recomeçar\n3 - Sair");
                confirmacaoCadastro = userController.confirmarInfos(sc.nextInt());
            }
                userController.insert(cpfTemp, nomeTemp, senhaTemp, emailTemp);
        }
    }
    }
