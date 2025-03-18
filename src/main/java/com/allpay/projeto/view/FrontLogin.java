package com.allpay.projeto.view;

import com.allpay.projeto.controller.User;
import com.allpay.projeto.controller.UserController;

import java.util.HashMap;
import java.util.Scanner;

public class FrontLogin {
    private static UserController userController = new UserController();

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


        boolean auth = userController.autenticar(id_usuario,senha_acesso);

        if (auth) {
            System.out.println("Login bem-sucedido!");
            HashMap<String, String> userInfo = userController.getUserInfo();
            User teste = new User(
                    userInfo.get("id_usuario"),
                    userInfo.get("nome_usuario"),
                    userInfo.get("email"),
                    userInfo.get("senha_acesso"));
            teste.mostrarInfos();
//            chamar a proxima tela e passar o usuaria para ela ter acesso
        } else {
            System.out.println("Credenciais inválidas. Tente novamente.");
        }

//        aqui sera instanciado o controller caso retorno ok
//        aqui sera instaciado o usuario com as informcoes dele
    }
}
