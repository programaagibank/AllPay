package com.allpay.projeto.controller;

public class User {
    private String id_usuario;
    private String nome_usuario;
    private String email;
    private String senha_acesso;

    public User(String id_usuario ,String nome_usuario, String email, String senha_acesso) {
        this.id_usuario = id_usuario;
        this.nome_usuario = nome_usuario;
        this.email = email;
        this.senha_acesso = senha_acesso;
    }

    public User(String cpf) {
    }

    public String getNome() {
        return nome_usuario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getSenha() {
        return senha_acesso;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome_usuario = nome;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setSenha(String senha) {
        this.senha_acesso = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void mostrarInfos() {
        System.out.println("Nome: " + nome_usuario);
        if (id_usuario.length() == 11) System.out.println("CPF: " + id_usuario);
        else if (id_usuario.length() == 13) {
            System.out.println("CNPJ: " + id_usuario);
        } else System.out.println("CPF/CNPJ inválido!!");

        System.out.println("Senha: " + senha_acesso);
        System.out.println("Email: " + email);
    }
}