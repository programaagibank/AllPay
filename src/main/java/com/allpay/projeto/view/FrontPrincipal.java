package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import com.allpay.projeto.DAO.FaturaDAO;
import com.allpay.projeto.controller.ContaBancoController;
import com.allpay.projeto.controller.UsuarioController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

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
        contaBancoController.encontrarContaBancoUsuario();
        this.view = new VBox(10);
        setupView();
    }

    public Parent getView() {
        return view;
    }

    private void setupView() {
        configurarEstiloView();
        view.getChildren().addAll(
                criarHeaderUsuario(),
                criarCarroselBanco(),
                criarMenuBotao(),
                criarBotaoFatura(),
                criarListaFatura()
        );
    }

    private void configurarEstiloView() {
        view.setAlignment(Pos.TOP_CENTER);
        view.setPadding(new Insets(90, 20, 20, 20));
        setBackground();
    }

    private Label criarHeaderUsuario() {
        Label header = new Label("Olá, " + nomeUsuario);
        header.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        header.setTextFill(Color.WHITE);
        return header;
    }

    private ScrollPane criarCarroselBanco() {
        HBox carousel = new HBox(10);
        carousel.setAlignment(Pos.CENTER_LEFT);


        ArrayList<HashMap<String, String>> bancos = contaBancoController.getBancosDisponiveis();

        if (bancos.isEmpty()) {
//            carousel.getChildren().add(criarLaberSemBancos());
        } else {
            bancos.forEach(banco -> {
                carousel.getChildren().add(criarCardBanco(banco));
                carousel.setStyle("-fx-background-color: transparent;" +
                        "-fx-border-radius: 15px; " +
                        "-fx-background-radius: 15px;");
            });
        }

        ScrollPane scroll = new ScrollPane(carousel);
        scroll.setStyle("-fx-background-color: transparent; -fx-border-radius: 15px; -fx-background-radius: 15px;");
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Oculta a scrollbar horizontal
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setFitToHeight(true);
        scroll.setPannable(true);
        scroll.setPrefViewportWidth(WINDOW_WIDTH - 40);

        Platform.runLater(() -> {
            Node viewport = scroll.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: transparent;");
            }
        });
        scroll.setOnMouseReleased(event -> {
            double currentScroll = scroll.getHvalue();
            double targetScroll = (currentScroll > 0.5) ? 1.0 : 0.0; // Decide para qual lado ir

            // 🔄 Animação progressiva para suavizar o movimento
            Timeline timeline = new Timeline();
            double duration = 100; // Duração em ms (mais tempo = mais suave)
            int frames = 60; // Quantidade de frames da animação
            double step = (targetScroll - currentScroll) / frames; // Diferença por frame

            for (int i = 0; i <= frames; i++) {
                double progress = currentScroll + (step * i);
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * (duration / frames)), e -> scroll.setHvalue(progress)));
            }

            timeline.play(); // Inicia a animação
        });

        return scroll;
    }

    private VBox criarCardBanco(HashMap<String, String> banco) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 6; -fx-padding: 15;");
        card.setMinWidth(WINDOW_WIDTH - 43);

        Label name = new Label(banco.get("nome_instituicao"));
        name.setStyle("-fx-text-fill: black; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 16px;");

        Label balance = new Label("R$ " + banco.get("saldo_usuario"));
        balance.setStyle("-fx-text-fill: black; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 18px;");

        card.getChildren().addAll(name, balance);
        return card;
    }

    private HBox criarMenuBotao() {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(
                criarMenuBotao("bank-icon.png", "Meus Bancos", () -> {
                    // Alteração aqui - direciona para pagamento da fatura 8
                    main.mostrarTelaPagarFatura(idUsuario, "8");
                }),
                criarMenuBotao("switch-icon.png", "Trocar Conta", () -> main.mostrarTelaEntrada()),
                criarMenuBotao("info-icon.png", "Infos allPay", () -> {}),
                criarMenuBotao("exit-icon.png", "Sair", () -> UsuarioController.sair())
        );
        return container;
    }

    private VBox criarMenuBotao(String iconPath, String text, Runnable action) {
        VBox container = new VBox(5);
        container.setAlignment(Pos.CENTER);

        Button btn = new Button();
        btn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 10;");
        btn.setMinSize(50, 50);
        btn.setGraphic(carregarIcone(iconPath));
        btn.setOnAction(e -> action.run());

        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-font-size: 10px;");
        label.setMaxWidth(70);

        container.getChildren().addAll(btn, label);
        return container;
    }

    private ImageView carregarIcone(String path) {
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

    private Button criarBotaoFatura() {
        Button btn = new Button("Buscar Faturas por Código");
        btn.setStyle("-fx-background-color: red; -fx-text-fill: #000000; -fx-font-weight: bold;");
        btn.setMinSize(250, 40);
        return btn;
    }


    private ScrollPane criarListaFatura() {
        VBox listaFaturas = new VBox(10);
        listaFaturas.setAlignment(Pos.TOP_LEFT);
        listaFaturas.setStyle("-fx-background-color: transparent;");
        ArrayList<HashMap<String, String>> faturas = new FaturaDAO().buscarFaturas(idUsuario);

        if (faturas.isEmpty()) {
//            carousel.getChildren().add(criarLaberSemBancos());
        } else {
            faturas.forEach(fatura -> {
                listaFaturas.getChildren().add(criarBotaoFatura(fatura));
                listaFaturas.setStyle("-fx-background-color: transparent;" +
                        "-fx-border-radius: 15px; " +
                        "-fx-background-radius: 15px;");
            });
        }

        // 🔵 Configura o ScrollPane para rolagem vertical
        ScrollPane scroll = new ScrollPane(listaFaturas);
        scroll.setStyle("-fx-background-color: transparent; -fx-border-radius: 15px; -fx-background-radius: 15px;");
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Oculta a barra de rolagem vertical
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setFitToHeight(true);
        scroll.setPannable(true);
        scroll.setPrefViewportHeight(150);

        // 🔵 Remove fundo branco da Viewport
        Platform.runLater(() -> {
            Node viewport = scroll.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: transparent;");
            }
        });
        final double[] lastY = {0}; // Guarda a última posição do mouse
        scroll.setOnMousePressed(event -> lastY[0] = event.getSceneY()); // Captura posição inicial

        scroll.setOnMouseDragged(event -> {
            double deltaY = lastY[0] - event.getSceneY(); // Calcula a diferença do movimento
            scroll.setVvalue(scroll.getVvalue() + deltaY / scroll.getHeight()); // Move o scroll
            lastY[0] = event.getSceneY(); // Atualiza posição
        });
        return scroll;
    }

    // 🔹 Cria botões para cada fatura
    private Button criarBotaoFatura(HashMap<String, String> fatura) {
        Button item = new Button();
        item.setStyle("-fx-background-color: transparent; -fx-border-radius: 15px; -fx-background-radius: 15px;");
        item.setGraphic(createInvoiceContent(fatura)); // Define o conteúdo do botão
        item.setOnAction(e -> abrirTelaX(fatura)); // Ao clicar, abre a tela com os dados da fatura // Chama a tela ao clicar
        item.setOnMousePressed(event -> item.getParent().fireEvent(event)); // Repassa evento para o VBox
        item.setOnMouseDragged(event -> item.getParent().fireEvent(event));
        return item;
    }
    private HBox createInvoiceContent(HashMap<String, String> fatura) {
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(30); // Ajusta o espaçamento entre os elementos
        content.setStyle("-fx-background-color: transparent; -fx-border-width: 0 0 2px 0; -fx-border-color: transparent transparent white transparent;");
        content.setPrefWidth(250); // Define a largura do bloco
        content.setPrefHeight(50); // Define a altura para manter alinhado

        // 🔹 Criação dos textos (não comprimimos eles agora)
        Label name = createInvoiceLabel(fatura.get("nome_recebedor"), "14", FontWeight.BOLD, Color.WHITE);
        Label dueDate = createInvoiceLabel(fatura.get("data_vencimento"), "12", FontWeight.NORMAL, Color.LIGHTGRAY);
        Label amount = createInvoiceLabel("R$ " + fatura.get("valor_fatura"), "12", FontWeight.NORMAL, Color.WHITE);
        Label status = createInvoiceLabel(
                fatura.get("status_fatura"), "12", FontWeight.NORMAL,
                "PAGA".equals(fatura.get("status_fatura")) ? Color.LIGHTGREEN : Color.LIGHTCORAL
        );

        // 🔹 Adiciona os elementos na linha
        content.getChildren().addAll(name, dueDate, amount, status);

        return content;
    }


    // 🔹 Abre a Tela X (simulação)
    private void abrirTelaX(HashMap<String, String> fatura) {
        main.mostrarTelaPagarFatura("00000000004", "0010");
    }


    private Label createInvoiceLabel(String text, String fontSize, FontWeight weight, Color color) {
        Label label = new Label(text);
        label.setStyle("-fx-border-color: transparent;");
        label.setFont(Font.font("Montserrat", weight, Double.parseDouble(fontSize)));
        label.setTextFill(color);
        return label;
    }

    private Label criarLaberSemBancos() {
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