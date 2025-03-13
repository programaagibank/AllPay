package com.allpay.projeto.view;

import com.allpay.projeto.controller.User;
import com.allpay.projeto.controller.UserController;

import java.util.Scanner;

public class FrontLogin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║               Login                ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();

        System.out.println("Digite o seu CPF ou CNPJ:");
        String id_usuario = sc.nextLine();
        System.out.println("Digite sua senha:");
        String senha_acesso = sc.nextLine();

        boolean auth = new UserController().autenticar(id_usuario,senha_acesso);

        if (auth) {
            System.out.println("Login bem-sucedido!");
            new User();
        } else {
            System.out.println("Credenciais inválidas. Tente novamente.");

        }

//        aqui sera instanciado o controller caso retorno ok
//        aqui sera instaciado o usuario com as informcoes dele
    }
}
