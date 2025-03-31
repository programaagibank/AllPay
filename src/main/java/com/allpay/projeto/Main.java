package com.allpay.projeto;

import com.allpay.projeto.view.FrontEntrada;
import com.allpay.projeto.view.FrontLogin;
import com.allpay.projeto.view.FrontPrincipal;
import com.allpay.projeto.view.FrontSignUp;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static final String URL = System.getenv("DB_URL");
    static final String USER = System.getenv("DB_USER");
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    private static Stage primaryStage;
    private static boolean primeiroAcesso = true;

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        configurarStage();
        mostrarTelaEntrada();
    }

    private void configurarStage() {
        primaryStage.setTitle("allPay");
        primaryStage.setResizable(false);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public void mostrarTelaEntrada() {
        if (primeiroAcesso) {
            primeiroAcesso = false;
            FrontEntrada.mostrarSplashScreen(this, () -> {
                trocarCena(new FrontEntrada(this).getView());
            });
        } else {
            trocarCena(new FrontEntrada(this).getView());
        }
    }

    public void mostrarTelaLogin() {
        trocarCena(new FrontLogin(this).getView());
    }

    public void mostrarTelaCadastro() {
        trocarCena(new FrontSignUp(this).getView());
    }

    private void trocarCena(Parent view) {
        Scene scene = new Scene(view, 320, 600);
        primaryStage.setScene(scene);
    }

    public void mostrarTelaPrincipal(String idUsuario, String nomeUsuario) {
        FrontPrincipal principal = new FrontPrincipal(this, idUsuario, nomeUsuario);
        trocarCena(principal.getView());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
