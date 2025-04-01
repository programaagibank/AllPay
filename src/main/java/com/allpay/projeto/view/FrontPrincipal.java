package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import com.allpay.projeto.DAO.FaturaDAO;
import com.allpay.projeto.controller.ContaBancoController;
import com.allpay.projeto.controller.UsuarioController;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FrontPrincipal {
    private final VBox view;
    private final Main main;
    private final String idUsuario;
    private final String nomeUsuario;
    private final ContaBancoController contaBancoController;
    private static final int WINDOW_WIDTH = 320;

    public FrontPrincipal(Main main, String idUsuario, String nomeUsuario) {
        this.main = main;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.contaBancoController = new ContaBancoController();
        contaBancoController.findUserBankAccount();
        this.view = new VBox(10);
        setupView();
    }

    public Parent getView() {
        return view;
    }

    private void setupView() {
        configureViewStyle();
        view.getChildren().addAll(
                createUserHeader(),
                createBankCarousel(),
                createMenuButtons(),
                createInvoiceButton(),
                createInvoiceList()
        );
    }

    private void configureViewStyle() {
        view.setAlignment(Pos.TOP_CENTER);
        view.setPadding(new Insets(10));
        view.setStyle("-fx-background-insets: 0; -fx-padding: 0;");
        setBackground();
    }

    private Label createUserHeader() {
        Label header = new Label("Olá, " + nomeUsuario);
        header.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        header.setTextFill(Color.WHITE);
        return header;
    }

    private ScrollPane createBankCarousel() {
        HBox carousel = new HBox(10);
        carousel.setAlignment(Pos.CENTER_LEFT);

        ArrayList<HashMap<String, String>> bancos = contaBancoController.getBancosDisponiveis();

        if (bancos.isEmpty()) {
            carousel.getChildren().add(createNoBanksLabel());
        } else {
            bancos.forEach(banco -> carousel.getChildren().add(createBankCard(banco)));
        }

        ScrollPane scroll = new ScrollPane(carousel);
        scroll.setStyle("-fx-background: transparent;");
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setFitToHeight(true);
        scroll.setPrefViewportWidth(WINDOW_WIDTH - 40);

        return scroll;
    }

    private VBox createBankCard(HashMap<String, String> banco) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 10; -fx-padding: 15;");
        card.setMinWidth(WINDOW_WIDTH - 50);

        Label name = new Label(banco.get("nome_instituicao"));
        name.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 16px;");

        Label balance = new Label("R$ " + banco.get("saldo_usuario"));
        balance.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 18px;");

        card.getChildren().addAll(name, balance);
        return card;
    }

    private HBox createMenuButtons() {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(
                createMenuButton("bank-icon.png", "Meus Bancos", () -> {}),
                createMenuButton("switch-icon.png", "Trocar Conta", () -> main.mostrarTelaEntrada()),
                createMenuButton("info-icon.png", "Infos allPay", () -> {}),
                createMenuButton("exit-icon.png", "Sair", () -> UsuarioController.exit())
        );
        return container;
    }

    private VBox createMenuButton(String iconPath, String text, Runnable action) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);

        Button btn = new Button();
        btn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 10;");
        btn.setMinSize(50, 50);
        btn.setGraphic(loadIcon(iconPath));
        btn.setOnAction(e -> action.run());

        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 10px;");
        label.setMaxWidth(70);

        container.getChildren().addAll(btn, label);
        return container;
    }

    private ImageView loadIcon(String path) {
        try {
            ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/images/" + path)));
            icon.setFitWidth(30);
            icon.setFitHeight(30);
            return icon;
        } catch (Exception e) {
            System.out.println("Ícone não encontrado: " + path);
            return new ImageView();
        }
    }

    private Button createInvoiceButton() {
        Button btn = new Button("Buscar Faturas por Código");
        btn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-weight: bold;");
        btn.setMinSize(250, 40);
        return btn;
    }

    private ListView<HBox> createInvoiceList() {
        ListView<HBox> listView = new ListView<>();
        listView.setStyle("-fx-background-color: transparent;");
        listView.setPrefHeight(200);

        FaturaDAO faturaDAO = new FaturaDAO();
        faturaDAO.buscarFaturas(idUsuario).forEach(fatura ->
                listView.getItems().add(createInvoiceItem(fatura))
        );

        return listView;
    }

    private HBox createInvoiceItem(HashMap<String, String> fatura) {
        HBox item = new HBox(15);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 10; -fx-padding: 10;");

        VBox infoBox = new VBox(5);
        infoBox.getChildren().addAll(
                createInvoiceLabel(fatura.get("nome_recebedor"), "14px", FontWeight.BOLD, Color.WHITE),
                createInvoiceLabel(fatura.get("data_vencimento"), "12px", FontWeight.NORMAL, Color.LIGHTGRAY),
                createInvoiceLabel("R$" + fatura.get("valor_fatura"), "12px", FontWeight.NORMAL, Color.WHITE),
                createInvoiceLabel(fatura.get("status_fatura"), "12px", FontWeight.NORMAL,
                        "PAGA".equals(fatura.get("status_fatura")) ? Color.LIGHTGREEN : Color.LIGHTCORAL)
        );

        item.getChildren().add(infoBox);
        return item;
    }

    private Label createInvoiceLabel(String text, String fontSize, FontWeight weight, Color color) {
        Label label = new Label(text);
        label.setFont(Font.font("Montserrat", weight, Double.parseDouble(fontSize)));
        label.setTextFill(color);
        return label;
    }

    private ArrayList<HashMap<String, String>> getBankAccounts() {
        ArrayList<HashMap<String, String>> bancos = new ArrayList<>();

        HashMap<String, String> banco1 = new HashMap<>();
        banco1.put("nome_banco", "AgiBank");
        banco1.put("saldo", "2,500.00");

        HashMap<String, String> banco2 = new HashMap<>();
        banco2.put("nome_banco", "Santander");
        banco2.put("saldo", "3,000.00");

        bancos.add(banco1);
        bancos.add(banco2);

        return bancos;
    }

    private Label createNoBanksLabel() {
        Label label = new Label("Nenhum banco vinculado");
        label.setTextFill(Color.WHITE);
        return label;
    }

    private void setBackground() {
        try {
            Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("/images/backgroundImage.png")).toExternalForm());
            view.setBackground(new Background(new BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true)
            )));
        } catch (Exception e) {
            view.setStyle("-fx-background-color: #121212;");
        }
    }
}