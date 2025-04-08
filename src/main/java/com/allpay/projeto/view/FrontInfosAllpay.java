package com.allpay.projeto.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import com.allpay.projeto.Main;
import com.allpay.projeto.DAO.RequestOFInfos;
import com.allpay.projeto.DAO.RequestOFInfos.Instituicao;

import java.util.List;
import java.util.Objects;

public class FrontInfosAllpay {
    private final VBox content;
    private static final int WINDOW_WIDTH = 320;
    private final String idUsuario;
    private Main main;
    private VBox cardsContainer;
    private List<Instituicao> todasInstituicoes;
    private int cardsMostrados = 0;
    private final int CARDS_POR_VEZ = 3;

    public FrontInfosAllpay(Main main, String idUsuario) {
        this.main = main;
        this.content = new VBox(15);
        this.idUsuario = idUsuario;
        this.todasInstituicoes = RequestOFInfos.getInstituicoes();
        setupView();
    }

    public Parent getView() {
        StackPane root = new StackPane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_WIDTH - 40);

        ScrollPane scrollPane = getScrollPane();

        Platform.runLater(() -> {
            Node viewport = scrollPane.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: transparent;");
            }
        });

        setBackground(root);
        root.getChildren().add(scrollPane);

        return root;
    }

    private ScrollPane getScrollPane() {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setPannable(true);
        scrollPane.setPrefViewportHeight(WINDOW_WIDTH - 60);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: black;");
        return scrollPane;
    }

    private void setupView() {
        configurarEstiloView();
        content.getChildren().addAll(
                criarTitulo(),
                criarContainerCards(),
                criarBtnVerMais(),
                criarBtnVoltar()
        );

        carregarMaisCards();

    }

    private VBox criarContainerCards() {
        cardsContainer = new VBox(10);
        cardsContainer.setAlignment(Pos.TOP_CENTER);
        cardsContainer.setPadding(new Insets(10));

        return cardsContainer;
    }

    private void carregarMaisCards() {
        int fim = Math.min(cardsMostrados + CARDS_POR_VEZ, todasInstituicoes.size());

        for (int i = cardsMostrados; i < fim; i++) {
            cardsContainer.getChildren().add(criarCardInstituicao(todasInstituicoes.get(i)));
        }

        cardsMostrados = fim;
    }

    private Button criarBtnVerMais() {
        Button btnVerMais = new Button("Ver Mais");
        btnVerMais.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-font-weight: bold;");
        btnVerMais.setMinWidth(75);
        btnVerMais.setOnAction(e -> {
            if (cardsMostrados < todasInstituicoes.size()) {
                carregarMaisCards();
            } else {
                btnVerMais.setDisable(true);
                btnVerMais.setText("Todas instituições carregadas");
            }
        });

        if (todasInstituicoes.size() <= CARDS_POR_VEZ) {
            btnVerMais.setVisible(false);
        }

        return btnVerMais;
    }

    private HBox criarCardInstituicao(Instituicao instituicao) {
        HBox card = new HBox(10);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 10;");
        card.setMaxWidth(WINDOW_WIDTH - 40);

        StackPane imageContainer = new StackPane();
        imageContainer.setMinSize(40, 40);
        imageContainer.setMaxSize(40, 40);
        imageContainer.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 20;");

        ImageView logoView = new ImageView();
        logoView.setFitWidth(36);
        logoView.setFitHeight(36);
        logoView.setPreserveRatio(true);

        carregarImagemPadrao(logoView);

        if (instituicao.getLogoUrl() != null && !instituicao.getLogoUrl().isEmpty()) {
            Platform.runLater(() -> {
                try {
                    Image logo = new Image(instituicao.getLogoUrl(), true);
                    logoView.setImage(logo);
                } catch (Exception e) {
                    System.err.println("Erro ao carregar logo: " + e.getMessage());
                }
            });
        }

        imageContainer.getChildren().add(logoView);

        VBox infoBox = new VBox(5);
        Label nomeLabel = new Label(instituicao.getNome());
        nomeLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
        nomeLabel.setTextFill(Color.WHITE);
        nomeLabel.setWrapText(true);
        nomeLabel.setMaxWidth(WINDOW_WIDTH - 70);

        Label statusLabel = new Label("Status: " + instituicao.getStatus());
        statusLabel.setFont(Font.font("Montserrat", 12));
        statusLabel.setTextFill(Color.LIGHTGRAY);

        infoBox.getChildren().addAll(nomeLabel, statusLabel);
        card.getChildren().addAll(imageContainer, infoBox);

        return card;
    }

    private void carregarImagemPadrao(ImageView logoView) {
        try {
            Image defaultImage = new Image(Objects.requireNonNull(
                    getClass().getResource("/images/loading.gif")).toExternalForm());
            logoView.setImage(defaultImage);
        } catch (Exception e) {
            logoView.setImage(null);
        }
    }

    private void configurarEstiloView() {
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(80, 15, 20, 15));
    }

    private Button criarBtnVoltar() {
        Button btnVoltar = FrontLogin.criarBotao("Voltar", "secondary");
        btnVoltar.setOnAction(e -> main.mostrarTelaPrincipal(idUsuario, "Usuário"));
        return btnVoltar;
    }

    private Label criarTitulo() {
        Label lblTitulo = new Label("Instituições\nParceiras");
        lblTitulo.setFont(Font.font("Montserrat", FontWeight.BOLD, 30));
        lblTitulo.setTextFill(Color.WHITE);
        lblTitulo.setTextAlignment(TextAlignment.CENTER);
        return lblTitulo;
    }

    private void setBackground(StackPane root) {
        try {
            Image bgImage = new Image(Objects.requireNonNull(getClass().getResource("/images/backgroundImage.png")).toExternalForm());
            root.setBackground(new Background(new BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true)
            )));
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #121212;");
        }
    }
}