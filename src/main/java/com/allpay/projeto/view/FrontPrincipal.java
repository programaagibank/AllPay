package com.allpay.projeto.view;

import com.allpay.projeto.controller.UserController;
import com.allpay.projeto.DAO.FaturaDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class FrontPrincipal extends Application {

    private final String idUsuario;
    private final String nomeUsuario;
    private static final int WINDOW_WIDTH = 320;
    private static final int WINDOW_HEIGHT = 600;

    public FrontPrincipal(String idUsuario, String nomeUsuario) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox mainLayout = new VBox(15);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setPadding(new Insets(20));
        setBackground(mainLayout, "/images/backgroundImage.png");

        Label lblNomeUsuario = new Label("Olá, " + nomeUsuario);
        lblNomeUsuario.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        lblNomeUsuario.setTextFill(Color.WHITE);

        // Carrossel de bancos
        HBox bancosCarrossel = createBancosCarrossel();
        ScrollPane scrollBancos = new ScrollPane(bancosCarrossel);
        configurarScrollCarrossel(scrollBancos);

        // Botões de menu
        HBox botoesMenu = createBotoesMenu(primaryStage);

        // Botão de buscar faturas
        Button btnBuscarFaturas = criarBotaoRetangular("Buscar Faturas por Código");

        // Lista de faturas
        ListView<HBox> listaFaturas = createListaFaturas();
        listaFaturas.setPrefHeight(200);

        // Ações dos botões
        configurarAcoesBotoes(primaryStage, btnBuscarFaturas);

        // Montagem do layout
        mainLayout.getChildren().addAll(
                lblNomeUsuario,
                scrollBancos,
                botoesMenu,
                btnBuscarFaturas,
                listaFaturas
        );

        Scene scene = new Scene(mainLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("allPay - Principal");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private HBox createBancosCarrossel() {
        HBox carrossel = new HBox(10);
        carrossel.setAlignment(Pos.CENTER_LEFT);
        carrossel.setPadding(new Insets(10));

        ArrayList<HashMap<String, String>> bancos = buscarBancosUsuario();

        if (bancos.isEmpty()) {
            Label lblSemBancos = new Label("Nenhum banco vinculado");
            lblSemBancos.setTextFill(Color.WHITE);
            carrossel.getChildren().add(lblSemBancos);
        } else {
            for (HashMap<String, String> banco : bancos) {
                VBox cardBanco = criarCardBanco(banco);
                carrossel.getChildren().add(cardBanco);
            }
        }

        return carrossel;
    }

    private VBox criarCardBanco(HashMap<String, String> banco) {
        VBox cardBanco = new VBox(10);
        cardBanco.setAlignment(Pos.CENTER);
        cardBanco.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 10; -fx-padding: 15;");
        cardBanco.setMinWidth(WINDOW_WIDTH - 50);
        cardBanco.setPrefWidth(WINDOW_WIDTH - 50);

        Label lblNomeBanco = new Label(banco.get("nome_banco"));
        lblNomeBanco.setTextFill(Color.WHITE);
        lblNomeBanco.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));

        Label lblSaldoBanco = new Label("R$ " + banco.get("saldo"));
        lblSaldoBanco.setTextFill(Color.WHITE);
        lblSaldoBanco.setFont(Font.font("Montserrat", FontWeight.BOLD, 18));

        cardBanco.getChildren().addAll(lblNomeBanco, lblSaldoBanco);
        return cardBanco;
    }

    private void configurarScrollCarrossel(ScrollPane scrollBancos) {
        scrollBancos.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollBancos.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollBancos.setFitToHeight(true);
        scrollBancos.setPrefViewportWidth(WINDOW_WIDTH - 40);
        scrollBancos.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
    }

    private HBox createBotoesMenu(Stage primaryStage) {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER);

        VBox btnMeusBancos = criarBotaoMenu("bank-icon.png", "Meus Bancos", primaryStage);
        VBox btnTrocarConta = criarBotaoMenu("switch-icon.png", "Trocar Conta", primaryStage);
        VBox btnInfos = criarBotaoMenu("info-icon.png", "Infos allPay", primaryStage);
        VBox btnSair = criarBotaoMenu("exit-icon.png", "Sair", primaryStage);

        container.getChildren().addAll(btnMeusBancos, btnTrocarConta, btnInfos, btnSair);
        return container;
    }

    private VBox criarBotaoMenu(String iconPath, String text, Stage primaryStage) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);

        Button btn = new Button();
        btn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 10;");
        btn.setMinSize(50, 50);
        btn.setMaxSize(50, 50);

        try {
            ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/images/" + iconPath)));
            icon.setFitWidth(30);
            icon.setFitHeight(30);
            btn.setGraphic(icon);
        } catch (Exception e) {
            System.out.println("Ícone não encontrado: " + iconPath);
        }

        configurarAcaoBotaoMenu(btn, text, primaryStage);

        Label lblText = new Label(text);
        lblText.setTextFill(Color.WHITE);
        lblText.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
        lblText.setWrapText(true);
        lblText.setMaxWidth(70);
        lblText.setAlignment(Pos.CENTER);

        container.getChildren().addAll(btn, lblText);
        return container;
    }

    private void configurarAcaoBotaoMenu(Button btn, String text, Stage primaryStage) {
        btn.setOnAction(e -> {
            primaryStage.close();
            switch (text) {
                case "Meus Bancos":
                    //new FrontBancos(idUsuario, nomeUsuario).start(new Stage());
                    break;
                case "Trocar Conta":
                    new FrontEntrada().start(new Stage());
                    break;
                case "Infos allPay":
                    //new FrontInfos(idUsuario, nomeUsuario).start(new Stage());
                    break;
                case "Sair":
                    UserController.exit();
                    break;
            }
        });
    }

    private Button criarBotaoRetangular(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000; -fx-font-weight: bold;");
        btn.setMinSize(250, 40);
        btn.setMaxSize(250, 40);
        return btn;
    }

    private ListView<HBox> createListaFaturas() {
        ListView<HBox> listView = new ListView<>();
        listView.setStyle("-fx-background-color: transparent;");

        FaturaDAO faturaDAO = new FaturaDAO();
        ArrayList<HashMap<String, String>> faturas = faturaDAO.buscarFaturas(idUsuario);

        for (HashMap<String, String> fatura : faturas) {
            HBox item = criarItemFatura(fatura);
            listView.getItems().add(item);
        }

        return listView;
    }

    private HBox criarItemFatura(HashMap<String, String> fatura) {
        HBox item = new HBox(15);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 10; -fx-padding: 10;");

        VBox infoBox = new VBox(5);
        Label lblRecebedor = new Label(fatura.get("nome_recebedor"));
        lblRecebedor.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        lblRecebedor.setTextFill(Color.WHITE);

        Label lblData = new Label(fatura.get("data_vencimento"));
        lblData.setTextFill(Color.LIGHTGRAY);

        Label lblValor = new Label("R$" + fatura.get("valor_fatura"));
        lblValor.setTextFill(Color.WHITE);

        Label lblStatus = new Label(fatura.get("status_fatura"));
        if ("PAGA".equals(fatura.get("status_fatura"))) {
            lblStatus.setTextFill(Color.LIGHTGREEN);
        } else {
            lblStatus.setTextFill(Color.LIGHTCORAL);
        }

        infoBox.getChildren().addAll(lblRecebedor, lblData, lblValor, lblStatus);
        item.getChildren().add(infoBox);
        return item;
    }

    private void configurarAcoesBotoes(Stage primaryStage, Button btnBuscarFaturas) {
        btnBuscarFaturas.setOnAction(e -> {
            primaryStage.close();
            //new FrontPagarFatura(idUsuario, nomeUsuario).start(new Stage());
        });
    }

    private ArrayList<HashMap<String, String>> buscarBancosUsuario() {
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

    private void setBackground(Region layout, String imagePath) {
        try {
            Image backgroundImage = new Image(getClass().getResource(imagePath).toExternalForm());
            BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
            layout.setBackground(new Background(bgImage));
        } catch (Exception e) {
            layout.setStyle("-fx-background-color: #1E90FF;");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}