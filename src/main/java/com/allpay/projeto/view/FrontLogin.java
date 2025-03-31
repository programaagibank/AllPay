package com.allpay.projeto.view;

import com.allpay.projeto.controller.UserController;
import com.allpay.projeto.model.UserModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;

public class FrontLogin extends Application {

    private static UserController userController = new UserController();
    private ImageView loadingGif = new ImageView();

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(90, 0, 0, 0));

        setBackground(layout, "/images/backgroundImage.png");

        Label lblTitulo = new Label("Login");
        lblTitulo.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        lblTitulo.setTextFill(Color.WHITE);

        TextField txtCpfCnpj = new TextField();
        txtCpfCnpj.setPromptText("CPF ou CNPJ");
        txtCpfCnpj.setMaxWidth(250);
        txtCpfCnpj.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");
        txtSenha.setMaxWidth(250);
        txtSenha.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        Label lblErro = new Label();
        lblErro.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        lblErro.setTextFill(Color.LIGHTCYAN);
        lblErro.setVisible(false);

        Button btnLogin = new Button("Entrar");
        btnLogin.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-min-width: 250px; -fx-min-height: 40px; -fx-font-family: 'Montserrat'; -fx-font-weight: bold;");

        Button btnCadastro = new Button("Cadastro");
        btnCadastro.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-min-width: 250px; -fx-min-height: 40px; -fx-font-family: 'Montserrat'; -fx-font-weight: bold;");

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle("-fx-background-color: #000000; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-min-width: 250px; -fx-min-height: 40px; -fx-font-family: 'Montserrat'; -fx-font-weight: bold;");

        try {
            loadingGif.setImage(new Image(getClass().getResourceAsStream("/images/loading.gif")));
        } catch (Exception e) {
            System.out.println("Erro ao carregar o gif");
        }
        loadingGif.setFitWidth(20);
        loadingGif.setFitHeight(20);
        loadingGif.setVisible(false);

        Region spacer = new Region();
        spacer.setPrefHeight(40);


        layout.getChildren().addAll(lblTitulo, spacer, txtCpfCnpj, txtSenha, lblErro, loadingGif, btnLogin, btnCadastro, btnVoltar);

        btnLogin.setOnAction(e -> {
            String cpfCnpj = txtCpfCnpj.getText();
            String senha = txtSenha.getText();

            loadingGif.setVisible(true);
            lblErro.setVisible(false);

            new Thread(() -> {
                boolean autenticado = userController.autenticar(cpfCnpj, senha);

                javafx.application.Platform.runLater(() -> {
                    if (autenticado) {

                        String nomeUsuario = UserModel.getNome();

                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        stage.close();
                        new FrontPrincipal(cpfCnpj, nomeUsuario).start(new Stage());
                    } else {
                        loadingGif.setVisible(false);
                        lblErro.setText("Credenciais inválidas. Tente Novamente.");
                        lblErro.setVisible(true);
                    }
                });
            }).start();
        });

        btnCadastro.setOnAction(e -> {
            Stage stage = (Stage) btnCadastro.getScene().getWindow();
            stage.close();
            new FrontSignUp().start(new Stage());
        });

        btnVoltar.setOnAction(e -> {
            primaryStage.close();
            new FrontEntrada().start(new Stage());
        });

        Scene scene = new Scene(layout, 320, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private static void setBackground(Region layout, String imagePath) {
        Image backgroundImage = new Image(FrontLogin.class.getResource(imagePath).toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        layout.setBackground(new Background(bgImage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}