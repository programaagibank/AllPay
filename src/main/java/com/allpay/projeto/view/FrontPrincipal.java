package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import com.allpay.projeto.DAO.FaturaDAO;
import com.allpay.projeto.controller.ContaBancoController;
import com.allpay.projeto.controller.UsuarioController;
import com.allpay.projeto.model.UsuarioModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
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

    private String formatarSaldoBanco(String saldoStr) {
        try {
            double saldo = Double.parseDouble(saldoStr);
            return String.format("%.2f", saldo);
        } catch (NumberFormatException e) {
            return "0.00"; // fallback se der erro na conversão
        }
    }
    private String formatarValorFatura(String valorStr) {
        try {
            double valor = Double.parseDouble(valorStr);
            return String.format("%.2f", valor);
        } catch (NumberFormatException e) {
            return "0.00"; // fallback se der erro na conversão
        }
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
        view.setPadding(new Insets(40, 20, 0, 20));
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
            carousel.getChildren().add(criarLaberSemBancos());
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
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setFitToHeight(true);
        scroll.setPannable(true);
        scroll.setPrefViewportWidth(WINDOW_WIDTH - 40);

        removeBackgroundScroll(scroll);
        scroll.setOnMouseReleased(event -> {
            double currentScroll = scroll.getHvalue();
            int cardCount = carousel.getChildren().size();

            // Se não há cards ou só um, não faz nada
            if (cardCount <= 1) return;

            // Calcula a posição ideal baseada no scroll atual
            double cardWidth = 1.0 / (cardCount - 1);
            int nearestCardIndex = (int) Math.round(currentScroll / cardWidth);
            double targetScroll = nearestCardIndex * cardWidth;

            // Animação suave
            Timeline timeline = new Timeline();
            double duration = 150; // Duração em ms
            int frames = 30; // Quantidade de frames

            for (int i = 0; i <= frames; i++) {
                double progress = currentScroll + ((targetScroll - currentScroll) * (i / (double)frames));
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(i * (duration / frames)),
                                e -> scroll.setHvalue(progress))
                );
            }

            timeline.play();
        });

        return scroll;
    }

    private VBox criarCardBanco(HashMap<String, String> banco) {
        VBox card = new VBox(5); // Espaçamento entre os elementos
        card.setFillWidth(false); // ❗️Não preencher a altura toda
        card.setAlignment(Pos.TOP_LEFT); // ❗️Alinha os itens no topo à esquerda
        card.setPadding(new Insets(10, 15, 10, 15)); // Padding ajustado para mais topo, menos base
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 4, 0, 0, 2);"
        );

        card.setMinWidth(WINDOW_WIDTH - 43);
        card.setMaxWidth(WINDOW_WIDTH - 43);
        card.setPrefHeight(140); // usa só prefHeight
        card.setMaxHeight(140);

        // 🔹 Saldo
        Label balance = new Label("R$ " + formatarSaldoBanco(banco.get("saldo_usuario")));
        balance.setStyle("-fx-text-fill: black; -fx-font-family: 'Arial'; -fx-font-size: 34px; -fx-font-weight: bold;");

        Label limite = new Label("Limite: R$ " + formatarSaldoBanco(banco.get("limite")));
        limite.setStyle("-fx-text-fill: black; -fx-font-family: 'Arial';  -fx-font-size: 14px; -fx-font-weight: bold;");

        // 🔹 Nome do banco
        Label name = new Label(banco.get("nome_instituicao"));
        name.setStyle("-fx-text-fill: #6e6e6e; -fx-font-family: 'Montserrat'; -fx-font-size: 12px;");

        card.getChildren().addAll(balance,limite, name);
        return card;
    }




    private HBox criarMenuBotao() {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(
                criarMenuBotao("switch-icon.png", "Trocar Conta", main::mostrarTelaEntrada),
                criarMenuBotao("info-icon.png", "Infos allPay", () -> main.mostrarTelaInformacao(idUsuario)),
                criarMenuBotao("exit-icon.png", "Sair", UsuarioController::sair)
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
            ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/" + path))));
            icon.setFitWidth(30);
            icon.setFitHeight(30);
            return icon;
        } catch (Exception e) {
            System.out.println("Ícone não encontrado: " + path);
            return new ImageView();
        }
    }

    private HBox criarBotaoFatura() {

        // Criando o botão
        Button btn = new Button();
        btn.setStyle("-fx-background-color: #ffffff00; -fx-text-fill: #ffffff00; -fx-font-weight: bold; -fx-font-family: 'Montserrat';");
        btn.setMinSize(60, 40);
        btn.setGraphic(carregarIcone("search-icon.png"));

        // Criando o campo de texto
        TextField faturaIdField = new TextField();
        faturaIdField.setPromptText("Buscar faturas por código");
        faturaIdField.setMinWidth(200);
        faturaIdField.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-font-family: 'Montserrat';");

        // Criando a label para a mensagem "Fatura não Encontrada"
        Label msgNaoEncontrado = createInvoiceLabel("Fatura não Encontrada!", "12", FontWeight.BOLD, Color.RED);
        msgNaoEncontrado.setMinWidth(200);  // Definindo a largura da mensagem de erro
        msgNaoEncontrado.setMaxWidth(200);  // Limitando a largura máxima para manter o alinhamento
        msgNaoEncontrado.setWrapText(true); // Permitindo que o texto seja quebrado em várias linhas se necessário
        msgNaoEncontrado.setStyle("-fx-font-size: 12px; -fx-text-fill: red;");
        msgNaoEncontrado.setVisible(false);  // A mensagem inicialmente invisível

        // Criando o HBox para o campo de texto e o botão (alinhados horizontalmente)
        HBox hboxCampos = new HBox(10);  // Espaço de 10 entre os elementos
        hboxCampos.setAlignment(Pos.CENTER); // Alinhando os elementos centralizados horizontalmente

        hboxCampos.getChildren().addAll(faturaIdField, btn);  // Adiciona o campo de texto e o botão ao HBox

        // Criando um VBox para colocar o HBox (campo de texto + botão) e a mensagem de erro
        VBox vbox = new VBox(10);  // Espaço de 10 entre os elementos
        vbox.setAlignment(Pos.CENTER);  // Alinha tudo no centro
        vbox.getChildren().addAll(hboxCampos, msgNaoEncontrado);  // Adiciona o HBox com os componentes e a mensagem de erro

        // Evento do botão
        btn.setOnAction(el -> {
            String text = faturaIdField.getText(); // Pega o texto do campo

            // Verifica se o campo está vazio ou contém um valor inválido
            if (text.trim().isEmpty()) {
                // Se estiver vazio, exibe uma mensagem de erro
                msgNaoEncontrado.setText("Código de fatura inválido!.");
                msgNaoEncontrado.setVisible(true);
            } else {
                try {
                    // Tenta converter o texto para número
                    int faturaId = Integer.parseInt(text);

                    // Busca a fatura com o ID fornecido
                    HashMap<String, String> fatura = new FaturaDAO().buscarFaturasNoUser(faturaId);

                    // Verifica se a fatura foi encontrada
                    if (!fatura.isEmpty()) {
                        // Adiciona a fatura encontrada e remove a mensagem de erro, se necessário
                        abrirTelaMostrarFatura(fatura, true);
                        msgNaoEncontrado.setVisible(false);  // Esconde a mensagem se a fatura foi encontrada
                    } else {
                        // Exibe a mensagem "Fatura não encontrada"
                        msgNaoEncontrado.setText("Fatura não Encontrada!");
                        msgNaoEncontrado.setVisible(true);
                    }
                } catch (NumberFormatException ex) {
                    // Se não for possível converter para inteiro, exibe uma mensagem de erro
                    msgNaoEncontrado.setText("Código de fatura inválido!");
                    msgNaoEncontrado.setWrapText(true);
                    msgNaoEncontrado.setVisible(true);
                    msgNaoEncontrado.setMaxWidth(Double.MAX_VALUE);
                }
            }
        });
        // Retorna o VBox com o layout ajustado
        HBox buscarFaturasContainer = new HBox();
        buscarFaturasContainer.getChildren().add(vbox);  // Coloca o VBox dentro do HBox, que será retornado
        buscarFaturasContainer.setAlignment(Pos.CENTER);  // Alinha o VBox no centro do HBox

        return buscarFaturasContainer;
    }

    private ScrollPane criarListaFatura() {
        VBox listaFaturas = new VBox(10);
        listaFaturas.setAlignment(Pos.TOP_CENTER);
        listaFaturas.setStyle("-fx-background-color: transparent; ");
        ArrayList<HashMap<String, String>> faturas = new FaturaDAO().buscarFaturas(idUsuario);

        if (faturas.isEmpty()) {
            listaFaturas.getChildren().add(criarLaberSemBancos());
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
        scroll.setPrefViewportHeight(250);
        scroll.setMaxWidth(Double.MAX_VALUE-40);


        // 🔵 Remove fundo branco da Viewport
        removeBackgroundScroll(scroll);
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
        item.setPrefWidth(277);
        item.setAlignment(Pos.TOP_CENTER);
        item.setStyle("-fx-background-color: transparent; -fx-border-radius: 15px; -fx-background-radius: 15px;");
        item.setGraphic(createInvoiceContent(fatura)); // Define o conteúdo do botão
        item.setOnAction(e -> abrirTelaMostrarFatura(fatura, false)); // Ao clicar, abre a tela com os dados da fatura // Chama a tela ao clicar
        item.setOnMousePressed(event -> item.getParent().fireEvent(event)); // Repassa evento para o VBox
        item.setOnMouseDragged(event -> item.getParent().fireEvent(event));
        return item;
    }
    private HBox createInvoiceContent(HashMap<String, String> fatura) {
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.setPadding(new Insets(5, 10, 5, 10));
        content.setStyle("-fx-background-color: transparent; -fx-border-width: 0 0 1px 0; -fx-border-color: transparent transparent grey transparent;");
        content.setPrefWidth(250);
        content.setPrefHeight(60); // Altura um pouco maior pra acomodar a quebra

        // 🔹 Nome (com quebra de linha)
        Label name = createInvoiceLabel(fatura.get("nome_recebedor"), "14", FontWeight.BOLD, Color.WHITE);
        name.setWrapText(true);
        name.setMaxWidth(130); // Largura fixa pro nome pra forçar quebra se necessário
        name.setTooltip(new Tooltip(fatura.get("nome_recebedor")));

        VBox nameBox = new VBox(name);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(nameBox, Priority.ALWAYS);

        // 🔹 Valor da fatura
        Label amount = createInvoiceLabel("R$ " + formatarValorFatura(fatura.get("valor_fatura")), "12", FontWeight.NORMAL, Color.WHITE);
        amount.setAlignment(Pos.CENTER_RIGHT);
        amount.setMaxWidth(80);

        // 🔹 Status da fatura
        Label status = createInvoiceLabel(
                fatura.get("status_fatura"), "12", FontWeight.NORMAL,
                "EM ABERTO".equals(fatura.get("status_fatura")) ? Color.YELLOW : Color.LIGHTCORAL
        );
        status.setAlignment(Pos.CENTER_RIGHT);
        status.setMaxWidth(80);

        VBox rightBox = new VBox(amount, status);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setSpacing(5);

        content.getChildren().addAll(nameBox, rightBox);
        return content;
    }




    // 🔹 Abre a Tela X (simulação)
    private void abrirTelaMostrarFatura(HashMap<String, String> fatura, boolean noId) {
        main.mostrarTelaPagarFatura(UsuarioModel.getId_usuario(), fatura.get("id_fatura"), noId);
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

    private void removeBackgroundScroll(ScrollPane scroll){
        Platform.runLater(() -> {
            Node viewport = scroll.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: transparent;");
            }
        });
    }
}