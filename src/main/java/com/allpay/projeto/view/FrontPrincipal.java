package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import com.allpay.projeto.DAO.FaturaDAO;
import com.allpay.projeto.controller.UserController;
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

public class FrontPrincipal {
    private final VBox view;
    private final Main main;
    private final String idUsuario;
    private final String nomeUsuario;
    private static final int WINDOW_WIDTH = 320;

    public FrontPrincipal(Main main, String idUsuario, String nomeUsuario) {
        this.main = main;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.view = new VBox(15);
        setupView();
    }

    public Parent getView() {
        return view;
    }

    private void setupView() {
        view.setAlignment(Pos.TOP_CENTER);
        view.setPadding(new Insets(20));
        setBackground();

        Label lblNomeUsuario = new Label("Olá, " + nomeUsuario);
        lblNomeUsuario.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        lblNomeUsuario.setTextFill(Color.WHITE);

        ScrollPane scrollBancos = createBancosCarrossel();
        HBox botoesMenu = createBotoesMenu();
        Button btnBuscarFaturas = criarBotaoRetangular("Buscar Faturas por Código");
        ListView<HBox> listaFaturas = createListaFaturas();
        listaFaturas.setPrefHeight(200);

        //btnBuscarFaturas.setOnAction(e -> main.mostrarTelaPagarFatura(idUsuario, nomeUsuario));

        view.getChildren().addAll(
                lblNomeUsuario, scrollBancos,
                botoesMenu, btnBuscarFaturas, listaFaturas
        );
    }

    private ScrollPane createBancosCarrossel() {
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
                carrossel.getChildren().add(criarCardBanco(banco));
            }
        }

        ScrollPane scroll = new ScrollPane(carrossel);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setFitToHeight(true);
        scroll.setPrefViewportWidth(WINDOW_WIDTH - 40);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        return scroll;
    }

    private VBox criarCardBanco(HashMap<String, String> banco) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 10; -fx-padding: 15;");
        card.setMinWidth(WINDOW_WIDTH - 50);
        card.setPrefWidth(WINDOW_WIDTH - 50);

        Label nome = new Label(banco.get("nome_banco"));
        nome.setTextFill(Color.WHITE);
        nome.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));

        Label saldo = new Label("R$ " + banco.get("saldo"));
        saldo.setTextFill(Color.WHITE);
        saldo.setFont(Font.font("Montserrat", FontWeight.BOLD, 18));

        card.getChildren().addAll(nome, saldo);
        return card;
    }

    private HBox createBotoesMenu() {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER);

        container.getChildren().addAll(
                criarBotaoMenu("bank-icon.png", "Meus Bancos", () -> {}),
                criarBotaoMenu("switch-icon.png", "Trocar Conta", () -> main.mostrarTelaEntrada()),
                criarBotaoMenu("info-icon.png", "Infos allPay", () -> {}),
                criarBotaoMenu("exit-icon.png", "Sair", () -> UserController.exit())
        );

        return container;
    }

    private VBox criarBotaoMenu(String iconPath, String text, Runnable acao) {
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

        btn.setOnAction(e -> acao.run());

        Label lblText = new Label(text);
        lblText.setTextFill(Color.WHITE);
        lblText.setFont(Font.font("Montserrat", FontWeight.BOLD, 10));
        lblText.setWrapText(true);
        lblText.setMaxWidth(70);
        lblText.setAlignment(Pos.CENTER);

        container.getChildren().addAll(btn, lblText);
        return container;
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
            listView.getItems().add(criarItemFatura(fatura));
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
        lblStatus.setTextFill("PAGA".equals(fatura.get("status_fatura")) ? Color.LIGHTGREEN : Color.LIGHTCORAL);

        infoBox.getChildren().addAll(lblRecebedor, lblData, lblValor, lblStatus);
        item.getChildren().add(infoBox);
        return item;
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
            view.setStyle("-fx-background-color: #1E90FF;");
        }
    }
}