package com.allpay.projeto.controller;

import com.allpay.projeto.model.UserModelDAO;

import java.sql.ResultSet;
import java.util.Scanner;

public class UserController {
    private UserModelDAO userModel;

    public UserController(){
        this.userModel =  new UserModelDAO();
    }
    public void select(){
      this.userModel.select();
      }

    public void insert(String cpfTemp, String nomeTemp, String senhaTemp, String emailTemp ){
        Scanner sc = new Scanner(System.in);

        while (!cpfTemp.matches("\\d+") || (cpfTemp.length() != 11 && cpfTemp.length() != 13)) {
            System.out.println("CPF ou CNPJ inválido! Digite novamente: ");
            cpfTemp = sc.nextLine();
        }

        while (!nomeTemp.matches("[a-zA-ZÀ-ÿ\\s]+")){
            System.out.println("Nome só pode ser composto por letras! Digite novamente: ");
            nomeTemp = sc.nextLine();
        }

        System.out.println(senhaTemp);
        while (!senhaTemp.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            System.out.println("Senha inválida! A senha deve conter pelo menos:");
            System.out.println("- Uma letra minúscula");
            System.out.println("- Uma letra maiúscula");
            System.out.println("- Um número");
            System.out.println("- Um caractere especial (@#$%^&+=!)");
            System.out.println("- Ter no mínimo 8 caracteres");

            senhaTemp = sc.nextLine(); // Solicita uma nova senha
        }

        while (!emailTemp.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")) {
            System.out.println("Email inválido! O email deve:");
            System.out.println("- Conter '@'");
            System.out.println("- Ter apenas letras minúsculas e números");
            System.out.println("- Ter um ponto seguido de pelo menos 2 letras minúsculas");
            System.out.println("- Ter um formato válido (ex: exemplo@email.com)");

            emailTemp = sc.nextLine();
        }

        // Salvar no banco de dados
        this.userModel.insert(cpfTemp, nomeTemp, senhaTemp, emailTemp);


    }

}
