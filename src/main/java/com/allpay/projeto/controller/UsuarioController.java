package com.allpay.projeto.controller;

import com.allpay.projeto.DAO.UsuarioDAO;
import com.allpay.projeto.model.UsuarioModel;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;

public class UsuarioController {
    private static UsuarioDAO userDAO;
    private HashMap<String, String> data;

    public UsuarioController() {
        userDAO = new UsuarioDAO();
    }

    public void insert(String cpfTemp, String nomeTemp, String senhaTemp, String emailTemp) {
        String hashSenha = BCrypt.hashpw(senhaTemp, BCrypt.gensalt());
        System.out.println(hashSenha);
        userDAO.insert(cpfTemp, nomeTemp, hashSenha, emailTemp);
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
        HashMap<String, String> data = userDAO.selectById(id_usuario, senha_acesso);
        if (!data.isEmpty()) {
            UsuarioModel.setUserData(
                    data.get("id_usuario"),
                    data.get("nome_usuario"),
                    data.get("email"),
                    data.get("senha_acesso")
            );
            return true;
        }
        return false;
    }

    public static void exit() {
        System.exit(0);
    }

}