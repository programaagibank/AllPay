package com.allpay.projeto.view;

import com.allpay.projeto.model.UsuarioModel;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
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
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
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
    private final HashMap<String, Image> imageCache = new HashMap<>();

    public FrontInfosAllpay(Main main, String idUsuario) {
        this.main = main;
        this.content = new VBox(15);
        this.idUsuario = idUsuario;
        this.todasInstituicoes = RequestOFInfos.getInstituicoes();
        setupView();
    }

    public Parent getView() {
        // Cria o StackPane principal
        StackPane root = new StackPane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_WIDTH - 40);

        // Configura o background
        setBackground(root);

        // Cria um VBox para organizar o conteúdo e o botão
        VBox mainContainer = new VBox();
        mainContainer.setPrefSize(WINDOW_WIDTH, WINDOW_WIDTH - 40);

        // Cria o ScrollPane com o conteúdo
        ScrollPane scrollPane = getScrollPane();

        // Cria um container para o botão voltar (fixo na parte inferior)
        StackPane bottomPane = new StackPane();
        bottomPane.setPadding(new Insets(0, 0, 20, 0)); // Espaço na parte inferior
        Button btnVoltar = criarBtnVoltar();
        bottomPane.getChildren().add(btnVoltar);

        // Adiciona os componentes ao mainContainer
        mainContainer.getChildren().addAll(scrollPane, bottomPane);

        // Configura para que o ScrollPane ocupe todo o espaço disponível
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Adiciona o mainContainer ao root
        root.getChildren().add(mainContainer);

        return root;
    }

    private ScrollPane getScrollPane() {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);

        // Estilo do ScrollPane
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Aplica estilo transparente ao viewport (área de conteúdo visível)
        scrollPane.setBackground(Background.EMPTY);
        scrollPane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> {
                Node viewport = scrollPane.lookup(".viewport");
                if (viewport != null) {
                    viewport.setStyle("-fx-background-color: transparent;");
                }
            });
        });

        // Estilo do conteúdo interno
        content.setStyle("-fx-background-color: transparent;");

        return scrollPane;
    }

    private void setupView() {
        configurarEstiloView();
        content.setStyle("-fx-background-color: transparent;"); // Adicione esta linha
        content.getChildren().addAll(
                criarTitulo(),
                criarContainerCards(),
                criarBtnVerMais()
        );
        carregarMaisCards();
    }

    private VBox criarContainerCards() {
        cardsContainer = new VBox(10);
        cardsContainer.setAlignment(Pos.TOP_CENTER);
        cardsContainer.setPadding(new Insets(10));
        cardsContainer.setStyle("-fx-background-color: transparent;"); // Adicione esta linha
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
        card.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 10;");
        card.setMaxWidth(WINDOW_WIDTH - 40);

        StackPane imageContainer = new StackPane();
        imageContainer.setMinSize(50, 50);
        imageContainer.setMaxSize(50, 50);

        ImageView logoView = new ImageView();
        logoView.setFitWidth(50);
        logoView.setFitHeight(50);
        logoView.setPreserveRatio(true);

        carregarImagemPadrao(logoView); // Carregar uma imagem padrão enquanto a imagem real não chega.

        if (instituicao.getLogoUrl() != null && !instituicao.getLogoUrl().isEmpty()) {
            Platform.runLater(() -> {
                try {
                    // Verifica se a URL do SVG é válida e começa a carregá-la
                    if (instituicao.getLogoUrl().endsWith(".svg")) {
                        carregarImagemSVGComCache(logoView, instituicao.getLogoUrl());
                    } else {
                        // Caso não seja SVG, tenta carregar como uma imagem comum
                        Image logo = new Image(instituicao.getLogoUrl(), true);
                        logoView.setImage(logo);
                    }
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

    // Método para carregar a imagem SVG de forma assíncrona com cache
    private void carregarImagemSVGComCache(ImageView imageView, String svgUrl) {
        // Verifica se a imagem já está no cache
        if (imageCache.containsKey(svgUrl)) {
            imageView.setImage(imageCache.get(svgUrl));
            return;
        }

        // Criação de um Task para carregar a imagem em um thread de fundo
        Task<Image> carregarImagemTask = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                // Carrega o SVG a partir da URL
                URL url = new URL(svgUrl);
                InputStream svgStream = url.openStream();

                // Cria o TranscoderInput com o stream SVG
                TranscoderInput input = new TranscoderInput(svgStream);

                // Cria um ImageTranscoder para transcodificar o SVG para BufferedImage
                ImageTranscoder transcoder = new ImageTranscoder() {
                    @Override
                    public BufferedImage createImage(int width, int height) {
                        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    }

                    @Override
                    public void writeImage(BufferedImage image, TranscoderOutput output) {
                        // Aqui você pega a BufferedImage e converte para Image JavaFX
                        Image fxImage = convertBufferedImageToImage(image);

                        // Atualiza a UI thread com a imagem transcodificada
                        Platform.runLater(() -> {
                            imageView.setImage(fxImage);
                            imageCache.put(svgUrl, fxImage); // Armazena a imagem no cache para futuras requisições
                        });
                    }
                };

                // Transcodifica a imagem SVG para BufferedImage
                transcoder.transcode(input, new TranscoderOutput());
                return null; // Não retorna nada, pois a atualização da UI é feita na writeImage
            }
        };

        // Quando houver erro, podemos lidar com isso de forma assíncrona
        carregarImagemTask.setOnFailed(event -> {
            Throwable t = carregarImagemTask.getException();
            System.err.println("Erro ao carregar a imagem: " + t.getMessage());
        });

        // Executa a Task em um thread separado
        Thread thread = new Thread(carregarImagemTask);
        thread.setDaemon(true);
        thread.start();
    }

    // Função auxiliar para converter BufferedImage para Image JavaFX
    private Image convertBufferedImageToImage(BufferedImage bufferedImage) {
        WritableImage writableImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int argb = bufferedImage.getRGB(x, y);
                int red = (argb >> 16) & 0xFF;
                int green = (argb >> 8) & 0xFF;
                int blue = argb & 0xFF;
                int alpha = (argb >> 24) & 0xFF;

                // Converte para ARGB (no formato int)
                int rgba = (alpha << 24) | (red << 16) | (green << 8) | blue;

                // Define o valor ARGB no PixelWriter
                pixelWriter.setArgb(x, y, rgba);
            }
        }

        return writableImage;
    }


    //    private HBox criarCardInstituicao(Instituicao instituicao) {
//        HBox card = new HBox(10);
//        card.setAlignment(Pos.CENTER_LEFT);
//        card.setPadding(new Insets(10));
//        card.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 10;");
//        card.setMaxWidth(WINDOW_WIDTH - 40);
//
//        StackPane imageContainer = new StackPane();
//        imageContainer.setMinSize(40, 40);
//        imageContainer.setMaxSize(40, 40);
//        imageContainer.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 20;");
//
//        ImageView logoView = new ImageView();
//        logoView.setFitWidth(36);
//        logoView.setFitHeight(36);
//        logoView.setPreserveRatio(true);
//
//        carregarImagemPadrao(logoView);
//
//        if (instituicao.getLogoUrl() != null && !instituicao.getLogoUrl().isEmpty()) {
//            Platform.runLater(() -> {
//                try {
//                    Image logo = new Image(instituicao.getLogoUrl(), true);
//                    System.out.println(instituicao.getLogoUrl());
//                    logoView.setImage(logo);
//                } catch (Exception e) {
//                    System.err.println("Erro ao carregar logo: " + e.getMessage());
//                }
//            });
//        }
//
//        imageContainer.getChildren().add(logoView);
//
//        VBox infoBox = new VBox(5);
//        Label nomeLabel = new Label(instituicao.getNome());
//        nomeLabel.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));
//        nomeLabel.setTextFill(Color.WHITE);
//        nomeLabel.setWrapText(true);
//        nomeLabel.setMaxWidth(WINDOW_WIDTH - 70);
//
//        Label statusLabel = new Label("Status: " + instituicao.getStatus());
//        statusLabel.setFont(Font.font("Montserrat", 12));
//        statusLabel.setTextFill(Color.LIGHTGRAY);
//
//        infoBox.getChildren().addAll(nomeLabel, statusLabel);
//        card.getChildren().addAll(imageContainer, infoBox);
//
//        return card;
//    }
//
    private void carregarImagemPadrao(ImageView logoView) {
        try {
            Image defaultImage = new Image(Objects.requireNonNull(
                    getClass().getResource("/images/moeda.png")).toExternalForm());
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
        btnVoltar.setOnAction(e -> main.mostrarTelaPrincipal(idUsuario, UsuarioModel.getNome()));
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