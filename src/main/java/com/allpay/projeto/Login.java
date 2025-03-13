package com.allpay.projeto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    private String id_usuario;
    private String senha_acesso;

    public Login() {
    }

    public boolean autenticar() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite seu CPF ou CNPJ: ");
        this.id_usuario = sc.next();

        System.out.println("Digite sua Senha: ");
        this.senha_acesso = sc.next();

        String sql = "SELECT * FROM usuario WHERE id_usuario = ? AND senha_acesso = ?";
        try {
            DatabaseConnection2 conn = new DatabaseConnection2();
            conn.connect();

            PreparedStatement stmt = conn.getConn().prepareStatement(sql);
            stmt.setString(1, this.id_usuario);
            stmt.setString(2, this.senha_acesso);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login bem-sucedido!");
                return true;
            } else {
                System.out.println("Credenciais inválidas. Tente novamente.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao autenticar usuário: " + e.getMessage());
            return false;
        }
    }
}
