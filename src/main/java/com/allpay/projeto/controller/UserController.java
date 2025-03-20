package com.allpay.projeto.controller;

import com.allpay.projeto.model.UserModelDAO;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Scanner;

public class UserController {
    private static UserModelDAO userModel;
    private HashMap<String, String> data;

    public UserController(){
        userModel =  new UserModelDAO();
    }

    public HashMap<String, String> getUserInfo(){
      return this.data;
      }

    public void insert(String cpfTemp, String nomeTemp, String senhaTemp, String emailTemp ){
        // Salvar no banco de dados
        this.userModel.insert(cpfTemp, nomeTemp, senhaTemp, emailTemp);
    }

    public String validarId(String id_temp){
        Scanner sc = new Scanner(System.in);

        while (!id_temp.matches("\\d+") || (id_temp.length() != 11 && id_temp.length() != 13)) {
            System.out.println("CPF ou CNPJ inválido! Digite novamente: ");
            id_temp = sc.nextLine();
        }
        return id_temp;
    }

    public String validarNome(String nome_temp){
        Scanner sc = new Scanner(System.in);

        while (!nome_temp.matches("[a-zA-ZÀ-ÿ\\s]+")){
            System.out.println("Nome só pode ser composto por letras! Digite novamente: ");
            nome_temp = sc.nextLine();
        }
        return nome_temp;
    }

    public String validarSenha(String senha_temp){
        Scanner sc = new Scanner(System.in);
        while (!senha_temp.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            System.out.println("Senha inválida! A senha deve conter pelo menos:");
            System.out.println("- Uma letra minúscula");
            System.out.println("- Uma letra maiúscula");
            System.out.println("- Um número");
            System.out.println("- Um caractere especial (@#$%^&+=!)");
            System.out.println("- Ter no mínimo 8 caracteres");

            senha_temp = sc.nextLine(); // Solicita uma nova senha
        }
        return senha_temp;
    }
    public String validarEmail(String email_temp){
        Scanner sc = new Scanner(System.in);

        while (!email_temp.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")) {
            System.out.println("Email inválido! O email deve:");
            System.out.println("- Conter '@'");
            System.out.println("- Ter apenas letras minúsculas e números");
            System.out.println("- Ter um ponto seguido de pelo menos 2 letras minúsculas");
            System.out.println("- Ter um formato válido (ex: exemplo@email.com)");

            email_temp = sc.nextLine();
        }
    return email_temp;
    }

    public boolean autenticar(String id_usuario, String senha_acesso) {

        HashMap<String, String> data = userModel.selectById(id_usuario,senha_acesso);
        if (!data.isEmpty()) {
            System.out.println("Autenticado!");
            this.data = data;
            return true;
        } else {
            return false;
        }

    }

    public static void logout() {
        System.out.println("Você escolheu Sair. Obrigado por usar o allPay.");
        System.out.println("Encerrando sessão...");
        System.exit(0);
    }

    public int confirmarInfos(int numConfirmacao){
        Scanner sc = new Scanner(System.in);
        do{

            if (numConfirmacao == 1){
                return 1;
            }
            else if (numConfirmacao == 3){
                System.out.println("Encerrando sessão...");
                logout();
                return 3;
            } else if (numConfirmacao == 2) {
                System.out.println("Recomeçando...");
                return 2;
            }else {
                System.out.println("Opção inválida!");
                return 4;
            }

        }while(numConfirmacao != 1 && numConfirmacao != 2 && numConfirmacao != 3);

    }
}
