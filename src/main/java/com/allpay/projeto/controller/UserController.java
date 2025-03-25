package com.allpay.projeto.controller;

import com.allpay.projeto.model.UserModelDAO;
import java.util.HashMap;

public class UserController {
    private static UserModelDAO userModel;
    private HashMap<String, String> data;

    public UserController() {
        userModel = new UserModelDAO();
    }

    public HashMap<String, String> getUserInfo() {
        return this.data;
    }

    public void insert(String cpfTemp, String nomeTemp, String senhaTemp, String emailTemp) {
        userModel.insert(cpfTemp, nomeTemp, senhaTemp, emailTemp);
    }

    public String validarId(String id_temp) throws IllegalArgumentException {
        if (!id_temp.matches("\\d+") || (id_temp.length() != 11 && id_temp.length() != 13)) {
            throw new IllegalArgumentException("CPF ou CNPJ inválido! Deve conter 11 (CPF) ou 13 (CNPJ) dígitos.");
        }
        return id_temp;
    }

    public String validarNome(String nome_temp) throws IllegalArgumentException {
        if (!nome_temp.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            throw new IllegalArgumentException("Nome só pode ser composto por letras!");
        }
        return nome_temp;
    }

    public String validarSenha(String senha_temp) throws IllegalArgumentException {
        if (!senha_temp.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            throw new IllegalArgumentException("Senha deve conter: letra minúscula, letra maiúscula, " +
                    "número, caractere especial e ao menos 8 dígitos.");
        }
        return senha_temp;
    }

    public String validarEmail(String email_temp) throws IllegalArgumentException {
        if (!email_temp.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido! Deve seguir o formato: exemplo@email.com");
        }
        return email_temp;
    }

    public boolean autenticar(String id_usuario, String senha_acesso) {
        HashMap<String, String> data = userModel.selectById(id_usuario, senha_acesso);
        if (!data.isEmpty()) {
            this.data = data;
            return true;
        }
        return false;
    }

    public static void exit() {
        System.exit(0);
    }

}