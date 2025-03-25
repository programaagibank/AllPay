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
import javafx.scene.text.TextAlignment;
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
        txtCpfCnpj.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome");
        txtNome.setMaxWidth(250);
        txtNome.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("E-mail");
        txtEmail.setMaxWidth(250);
        txtEmail.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");
        txtSenha.setMaxWidth(250);
        txtSenha.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        PasswordField txtConfirmarSenha = new PasswordField();
        txtConfirmarSenha.setPromptText("Confirmar Senha");
        txtConfirmarSenha.setMaxWidth(250);
        txtConfirmarSenha.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");


        Label lblErro = new Label();
        lblErro.setFont(Font.font("Montserrat", FontWeight.BOLD, 12));
        lblErro.setTextFill(Color.LIGHTCYAN);
        lblErro.setVisible(false);
        lblErro.setWrapText(true);
        lblErro.setMaxWidth(250);
        lblErro.setTextAlignment(TextAlignment.CENTER);
        lblErro.setAlignment(Pos.TOP_CENTER);

        StackPane errorPane = new StackPane(lblErro);
        errorPane.setMinHeight(55);
        errorPane.setPrefHeight(55);
        errorPane.setMaxHeight(55);
        errorPane.setMaxWidth(250);

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
            String cpfCnpj = txtCpfCnpj.getText().trim();
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim().toLowerCase();
            String senha = txtSenha.getText();
            String confirmarSenha = txtConfirmarSenha.getText();

            lblErro.setVisible(false);

            try {

                cpfCnpj = userController.validarId(cpfCnpj);
                nome = userController.validarNome(nome);
                email = userController.validarEmail(email);
                senha = userController.validarSenha(senha);

                if (!senha.equals(confirmarSenha)) {
                    throw new IllegalArgumentException("As senhas não coincidem!");
                }

                userController.insert(cpfCnpj, nome, senha, email);

                lblErro.setText("Cadastro realizado com sucesso!");
                lblErro.setTextFill(Color.LIGHTGREEN);
                lblErro.setVisible(true);

                txtCpfCnpj.clear();
                txtNome.clear();
                txtEmail.clear();
                txtSenha.clear();
                txtConfirmarSenha.clear();

            } catch (IllegalArgumentException ex) {
                lblErro.setText(ex.getMessage());
                lblErro.setTextFill(Color.LIGHTCORAL);
                lblErro.setVisible(true);
            }
        });


        btnSair.setOnAction(e -> {
            primaryStage.close();
            FrontEntrada frontEntrada = new FrontEntrada();
            frontEntrada.start(new Stage());
        });

        layout.getChildren().addAll(lblTitulo, txtCpfCnpj, txtNome, txtEmail, txtSenha, txtConfirmarSenha, errorPane, btnAvancar, btnSair);

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