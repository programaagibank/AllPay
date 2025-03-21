package com.allpay.projeto.viewOld;

import com.allpay.projeto.controller.User;
import com.allpay.projeto.controller.UserController;

import java.util.HashMap;
import java.util.Scanner;

public class FrontLoginOld {
    private static UserController userController = new UserController();
    public static final String RESET = "\u001B[0m";
    public static final String AZUL = "\u001B[34m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int tentativas = 0;
        final int MAX_TENTATIVAS = 3;
        boolean autenticado = false;


        System.out.println(AZUL + "╔════════════════════════════════════╗");
        System.out.println("║               Login                ║");
        System.out.println("╚════════════════════════════════════╝" + RESET);
        System.out.println();

        while (tentativas < MAX_TENTATIVAS) {
            System.out.println("Digite o seu CPF ou CNPJ:");
            String id_usuario = sc.nextLine();

            System.out.println("Digite sua senha:");
            String senha_acesso = sc.nextLine();

            if (userController.autenticar(id_usuario, senha_acesso)) {
                autenticado = true;
                System.out.println("Login bem-sucedido!");
                HashMap<String, String> userInfo = userController.getUserInfo();
                User.setUserData(
                        userInfo.get("id_usuario"),
                        userInfo.get("nome_usuario"),
                        userInfo.get("email"),
                        userInfo.get("senha_acesso"));
                User.mostrarInfos();
                FrontPrincipalOld.main(args);
            } else {
                tentativas++;
                System.out.println("Credenciais inválidas. Tentativas restantes: " + (MAX_TENTATIVAS - tentativas));

            }

        }

        if (!autenticado) {
            System.out.println("Número máximo de tentativas excedido. Conta temporariamente bloqueada.");
        }

        sc.close();
    }
}