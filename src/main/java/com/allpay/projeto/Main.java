package com.allpay.projeto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite seu CPF ou CNPJ: ");
        String cpfTemp = sc.next();

        while (!cpfTemp.matches("\\d+") || (cpfTemp.length() != 11 && cpfTemp.length() != 13)) {
            System.out.println("CPF ou CNPJ inválido! Digite novamente: ");
            cpfTemp = sc.next();
        }
        user.setId_usuario(cpfTemp);

        System.out.println("Digite seu Nome: ");
        String nomeTemp = sc.next();

        while (!nomeTemp.matches("[a-zA-ZÀ-ÿ\\s]+")){
            System.out.println("Nome só pode ser composto por letras! Digite novamente: ");
            nomeTemp = sc.next();
        }
        user.setNome(nomeTemp);

        System.out.println("Digite sua Senha: ");
        String senhaTemp = sc.next();
        while (!senhaTemp.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            System.out.println("Senha inválida! A senha deve conter pelo menos:");
            System.out.println("- Uma letra minúscula");
            System.out.println("- Uma letra maiúscula");
            System.out.println("- Um número");
            System.out.println("- Um caractere especial (@#$%^&+=!)");
            System.out.println("- Ter no mínimo 8 caracteres");

            senhaTemp = sc.next(); // Solicita uma nova senha
        }

        user.setSenha(senhaTemp);


        System.out.println("Digite seu Email: ");
        String emailTemp = sc.next();

        while (!emailTemp.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")) {
            System.out.println("Email inválido! O email deve:");
            System.out.println("- Conter '@'");
            System.out.println("- Ter apenas letras minúsculas e números");
            System.out.println("- Ter um ponto seguido de pelo menos 2 letras minúsculas");
            System.out.println("- Ter um formato válido (ex: exemplo@email.com)");

            emailTemp = sc.next();
        }

        user.setEmail(emailTemp);

        user.mostrarInfos();

        // Salvar no banco de dados
        user.salvarNoBanco();

        sc.close();
    }
}
