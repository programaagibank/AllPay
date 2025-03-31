package com.allpay.projeto.view;

import com.allpay.projeto.Main;

import com.allpay.projeto.controller.UsuarioController;
import com.allpay.projeto.model.UsuarioModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;

public class FrontLogin {
    private final VBox view;
    private final Main main;
    private final UsuarioController userController = new UsuarioController();
    private final ImageView loadingGif = new ImageView();

    public FrontLogin(Main main) {
        this.main = main;
        this.view = new VBox(20);
        setupView();
    }

    public Parent getView() {
        return view;
    }

    private void setupView() {
        view.setAlignment(Pos.TOP_CENTER);
        view.setPadding(new Insets(90, 20, 20, 20));
        setBackground();

        Label lblTitulo = new Label("Login");
        lblTitulo.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        lblTitulo.setTextFill(Color.WHITE);

        TextField txtCpfCnpj = createTextField("CPF ou CNPJ");
        PasswordField txtSenha = createPasswordField("Senha");

        Label lblErro = createErrorLabel();
        setupLoadingGif();

        Button btnLogin = createButton("Entrar", "primary");
        Button btnCadastro = createButton("Cadastro", "primary");
        Button btnVoltar = createButton("Voltar", "secondary");

        Region spacer = new Region();
        spacer.setPrefHeight(40);

        view.getChildren().addAll(lblTitulo, spacer, txtCpfCnpj, txtSenha, lblErro, loadingGif, btnLogin, btnCadastro, btnVoltar);

        btnLogin.setOnAction(e -> handleLogin(txtCpfCnpj, txtSenha, lblErro));
        btnCadastro.setOnAction(e -> main.mostrarTelaCadastro());
        btnVoltar.setOnAction(e -> main.mostrarTelaEntrada());
    }

    private TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setMaxWidth(250);
        field.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        return field;
    }

    private PasswordField createPasswordField(String prompt) {
        PasswordField field = new PasswordField();
        field.setPromptText(prompt);
        field.setMaxWidth(250);
        field.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        return field;
    }

    private Label createErrorLabel() {
        Label label = new Label();
        label.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        label.setTextFill(Color.LIGHTCYAN);
        label.setVisible(false);
        return label;
    }

    private void setupLoadingGif() {
        try {
            loadingGif.setImage(new Image(getClass().getResourceAsStream("/images/loading.gif")));
        } catch (Exception e) {
            System.out.println("Erro ao carregar o gif");
        }
        loadingGif.setFitWidth(20);
        loadingGif.setFitHeight(20);
        loadingGif.setVisible(false);
    }

    private Button createButton(String text, String type) {
        Button btn = new Button(text);
        String baseStyle = "-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; -fx-min-width: 250px; -fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; -fx-font-weight: bold;";

        if (type.equals("primary")) {
            btn.setStyle(baseStyle + " -fx-background-color: #FFFFFF; -fx-text-fill: #000000;");
        } else {
            btn.setStyle(baseStyle + " -fx-background-color: #000000; -fx-text-fill: #FFFFFF;");
        }
        return btn;
    }

    private void handleLogin(TextField txtCpfCnpj, PasswordField txtSenha, Label lblErro) {
        String cpfCnpj = txtCpfCnpj.getText();
        String senha = txtSenha.getText();

        loadingGif.setVisible(true);
        lblErro.setVisible(false);

        new Thread(() -> {
            boolean autenticado = userController.autenticar(cpfCnpj, senha);

            javafx.application.Platform.runLater(() -> {
                loadingGif.setVisible(false);

                if (autenticado) {

                    String nomeUsuario = UsuarioModel.getId_usuario();
                    main.mostrarTelaPrincipal(cpfCnpj, nomeUsuario);
                } else {
                    lblErro.setText("Credenciais inválidas. Tente Novamente.");
                    lblErro.setVisible(true);
                }
            });
        }).start();
    }

    private void setBackground() {
        try {
            Image backgroundImage = new Image(getClass().getResource("/images/backgroundImage.png").toExternalForm());
            BackgroundImage bgImage = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true)
            );
            view.setBackground(new Background(bgImage));
        } catch (Exception e) {
            view.setStyle("-fx-background-color: #121212;");
        }
    }
}