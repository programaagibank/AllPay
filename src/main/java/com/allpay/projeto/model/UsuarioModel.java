package com.allpay.projeto.model;

public class UsuarioModel {
    private static String id_usuario;
    private static String nome_usuario;
    private static String email;
    private static String senha_acesso;


    public static void setUserData(String id_usuario, String nome_usuario, String email, String senha_acesso){
        UsuarioModel.id_usuario = id_usuario;
        UsuarioModel.nome_usuario = nome_usuario;
        UsuarioModel.email = email;
        UsuarioModel.senha_acesso = senha_acesso;
    }

    public static String getNome() {
        return nome_usuario;
    }

    public static String getId_usuario() {
        return id_usuario;
    }

    public static String getSenha() {
        return senha_acesso;
    }

    public static String getEmail() {
        return email;
    }

    public static void setNome(String nome) {
        nome_usuario = nome;
    }

    public static void setId_usuario(String id_usuario) {
        id_usuario = id_usuario;
    }

    public static void setSenha(String senha) {
        senha_acesso = senha;
    }

    public static void setEmail(String email) {
        email = email;
    }

    public static void mostrarInfos() {
        System.out.println("Nome: " + nome_usuario);
        if (id_usuario.length() == 11) System.out.println("CPF: " + id_usuario);
        else if (id_usuario.length() == 13) {
            System.out.println("CNPJ: " + id_usuario);
        } else System.out.println("CPF/CNPJ inválido!!");

        System.out.println("Senha: " + senha_acesso);
        System.out.println("Email: " + email);
    }

    public static void encerrarSessao(){
        id_usuario = null;
        nome_usuario = null;
        email = null;
        senha_acesso = null;
    }
}

