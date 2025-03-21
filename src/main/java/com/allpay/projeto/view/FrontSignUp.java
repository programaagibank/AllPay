package com.allpay.projeto.view;

import com.allpay.projeto.controller.UserController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class FrontSignUp extends Application {

    private UserController userController = new UserController();

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(50, 0, 0, 0));

        setBackground(layout, "/images/backgroundImage.png");

        Label lblTitulo = new Label("Cadastro");
        lblTitulo.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        lblTitulo.setTextFill(Color.WHITE);

        TextField txtCpfCnpj = new TextField();
        txtCpfCnpj.setPromptText("CPF ou CNPJ");
        txtCpfCnpj.setMaxWidth(250);
        txtCpfCnpj.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome");
        txtNome.setMaxWidth(250);
        txtNome.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("E-mail");
        txtEmail.setMaxWidth(250);
        txtEmail.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");
        txtSenha.setMaxWidth(250);
        txtSenha.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        PasswordField txtConfirmarSenha = new PasswordField();
        txtConfirmarSenha.setPromptText("Confirmar Senha");
        txtConfirmarSenha.setMaxWidth(250);
        txtConfirmarSenha.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label lblErro = new Label();
        lblErro.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        lblErro.setTextFill(Color.LIGHTCYAN);
        lblErro.setVisible(false);

        Button btnAvancar = new Button("Avançar");
        btnAvancar.setStyle("-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: #000000; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; " +
                "-fx-min-width: 250px; " +
                "-fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; " +
                "-fx-font-weight: bold;");


        Button btnSair = new Button("Sair");
        btnSair.setStyle("-fx-background-color: #000000; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; " +
                "-fx-min-width: 250px; " +
                "-fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; " +
                "-fx-font-weight: bold;");

        btnAvancar.setOnAction(e -> {
            String cpfCnpj = txtCpfCnpj.getText();
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = txtSenha.getText();
            String confirmarSenha = txtConfirmarSenha.getText();

            if (senha.equals(confirmarSenha)) {
                userController.insert(cpfCnpj, nome, senha, email);
                lblErro.setText("Cadastro realizado com sucesso!");
                lblErro.setTextFill(Color.LIGHTGREEN);
                lblErro.setVisible(true);
            } else {
                lblErro.setText("As senhas não coincidem!");
                lblErro.setTextFill(Color.LIGHTCYAN);
                lblErro.setVisible(true);
            }
        });


        btnSair.setOnAction(e -> {
            primaryStage.close();
            FrontEntrada frontEntrada = new FrontEntrada();
            frontEntrada.start(new Stage());
        });

        Region spacer = new Region();
        spacer.setPrefHeight(5);

        layout.getChildren().addAll(lblTitulo, txtCpfCnpj, txtNome, txtEmail, txtSenha, txtConfirmarSenha, lblErro, spacer, btnAvancar, btnSair);

        Scene scene = new Scene(layout, 320, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cadastro");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setBackground(Region layout, String imagePath) {
        Image backgroundImage = new Image(FrontSignUp.class.getResource(imagePath).toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        layout.setBackground(new Background(bgImage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}