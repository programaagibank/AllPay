package com.allpay.projeto;

import com.allpay.projeto.view.*;
import com.allpay.projeto.view.FrontCadastro;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

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
            FrontEntrada.mostrarTelaSplash(this::run);
        } else {
            trocarCena(new FrontEntrada(this).getView());
        }
    }

    public void mostrarTelaLogin() {
        trocarCena(new FrontLogin(this).getView());
    }

    public void mostrarTelaCadastro() {
        trocarCena(new FrontCadastro(this).getView());
    }

    private void trocarCena(Parent view) {
        Scene scene = new Scene(view, 320, 600);
        primaryStage.setScene(scene);
    }

    public void mostrarTelaPrincipal(String idUsuario, String nomeUsuario) {
        FrontPrincipal principal = new FrontPrincipal(this, idUsuario, nomeUsuario);
        trocarCena(principal.getView());
    }

    public void mostrarTelaPagarFatura(String idUsuario, String idPagamento) {
        FrontPagarFatura pagarFatura = new FrontPagarFatura(this, idUsuario, idPagamento);
        trocarCena(pagarFatura.getView());
    }

    public void mostrarComprovantePagamento(String idUsuario, String idFatura) {
        FrontGerarComprovante comprovante = new FrontGerarComprovante(this, idUsuario, idFatura);
        trocarCena(comprovante.getView()); ///Mudar aqui glr
    }

    public void mostrarTelaInformacao() {
        FrontInfosAllpay pagarFatura = new FrontInfosAllpay();
        trocarCena(pagarFatura.getView());
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void run() {
        trocarCena(new FrontEntrada(this).getView());
    }
}
