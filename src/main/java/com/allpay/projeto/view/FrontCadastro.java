package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import com.allpay.projeto.controller.UsuarioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class FrontCadastro {
    private final VBox view;
    private final Main main;
    private final UsuarioController userController = new UsuarioController();

    public FrontCadastro(Main main) {
        this.main = main;
        this.view = new VBox(20);
        setupView();
    }

    public Parent getView() {
        return view;
    }

    private void setupView() {
        view.setAlignment(Pos.TOP_CENTER);
        view.setPadding(new Insets(50, 20, 20, 20));
        setBackground();

        Label lblTitulo = new Label("Cadastro");
        lblTitulo.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        lblTitulo.setTextFill(Color.WHITE);

        TextField txtCpfCnpj = createTextField("CPF ou CNPJ");
        TextField txtNome = createTextField("Nome");
        TextField txtEmail = createTextField("E-mail");
        PasswordField txtSenha = createPasswordField("Senha");
        PasswordField txtConfirmarSenha = createPasswordField("Confirmar Senha");

        Label lblErro = createErrorLabel();
        StackPane errorPane = createErrorPane(lblErro);

        Button btnAvancar = createButton("Avançar", "primary");
        Button btnVoltar = createButton("Voltar", "secondary");

        btnAvancar.setOnAction(e -> handleRegistration(
                txtCpfCnpj, txtNome, txtEmail,
                txtSenha, txtConfirmarSenha, lblErro
        ));

        btnVoltar.setOnAction(e -> main.mostrarTelaEntrada());

        view.getChildren().addAll(
                lblTitulo, txtCpfCnpj, txtNome, txtEmail,
                txtSenha, txtConfirmarSenha, errorPane,
                btnAvancar, btnVoltar
        );
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
        label.setFont(Font.font("Montserrat", FontWeight.BOLD, 12));
        label.setTextFill(Color.LIGHTCYAN);
        label.setVisible(false);
        label.setWrapText(true);
        label.setMaxWidth(250);
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }

    private StackPane createErrorPane(Label lblErro) {
        StackPane pane = new StackPane(lblErro);
        pane.setMinHeight(55);
        pane.setPrefHeight(55);
        pane.setMaxHeight(55);
        pane.setMaxWidth(250);
        return pane;
    }

    private Button createButton(String text, String type) {
        Button btn = new Button(text);
        String baseStyle = "-fx-font-size: 14px; -fx-padding: 10px 20px; " +
                "-fx-border-radius: 5px; -fx-background-radius: 5px; " +
                "-fx-min-width: 250px; -fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; -fx-font-weight: bold;";

        if (type.equals("primary")) {
            btn.setStyle(baseStyle + " -fx-background-color: #FFFFFF; -fx-text-fill: #000000;");
        } else {
            btn.setStyle(baseStyle + " -fx-background-color: #000000; -fx-text-fill: #FFFFFF;");
        }
        return btn;
    }

    private void handleRegistration(TextField txtCpfCnpj, TextField txtNome,
                                    TextField txtEmail, PasswordField txtSenha,
                                    PasswordField txtConfirmarSenha, Label lblErro) {
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

            main.mostrarTelaEntrada();

        } catch (IllegalArgumentException ex) {
            lblErro.setText(ex.getMessage());
            lblErro.setTextFill(Color.LIGHTCORAL);
            lblErro.setVisible(true);
        }
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