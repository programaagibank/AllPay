package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.Objects;

public class FrontInfosAllpay {
    private final VBox content;
    private final Main main;
    private static final int WINDOW_WIDTH = 320;

    public FrontInfosAllpay(Main main) {
        this.main = main;
        this.content = new VBox(15);
        setupView();
    }

    public Parent getView() {
        // 🔹 Criamos um StackPane para manter o fundo fixo
        StackPane root = new StackPane();
        root.setPrefSize(WINDOW_WIDTH, WINDOW_WIDTH - 40);

        // 🔹 Criamos o ScrollPane para o conteúdo
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // 🔹 Oculta a barra de rolagem vertical
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // 🔹 Oculta a barra de rolagem horizontal
        scrollPane.setFitToWidth(true); // 🔹 Ajusta a largura ao conteúdo
        scrollPane.setFitToHeight(false); // 🔹 Permite que o conteúdo role verticalmente sem afetar o fundo
        scrollPane.setPannable(true); // 🔹 Permite arrastar o conteúdo
        scrollPane.setPrefViewportHeight(WINDOW_WIDTH - 60);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: black;");

        // 🔹 Remove o fundo extra do ScrollPane (deixa transparente)
        Platform.runLater(() -> {
            Node viewport = scrollPane.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: transparent;");
            }
        });

        // 🔹 Adicionamos o fundo diretamente ao StackPane
        setBackground(root);

        // 🔹 Adicionamos o ScrollPane sobre o fundo fixo
        root.getChildren().add(scrollPane);

        return root;
    }

    private void setupView() {
        configurarEstiloView();
        content.getChildren().addAll(
                criarTitulo(),
                criarTextoInformativo()
        );
    }

    private void configurarEstiloView() {
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(80, 15, 20, 15)); // 🔹 Ajustei padding para não ultrapassar a tela
    }

    private Label criarTitulo() {
        Label lblTitulo = new Label("Sobre o AllPay");
        lblTitulo.setFont(Font.font("Montserrat", FontWeight.BOLD, 30)); // 🔹 Reduzi um pouco a fonte
        lblTitulo.setTextFill(Color.WHITE);
        return lblTitulo;
    }

    private TextFlow criarTextoInformativo() {
        String texto = """
                Tendo em vista os altos números de analfabetismo digital e falta de educação financeira na população brasileira, 
                e considerando também o público-alvo majoritário do AgiBank, o grupo busca atingir essa demografia.
                
                Isto é, pessoas com maior dificuldade no acesso às tecnologias, em geral da terceira idade ou mais velhas. 
                Prezamos, então, por uma interface amigável e intuitiva, com instruções de utilização claras. 
                
                O projeto também tem por objetivo oferecer maior segurança e confiabilidade nos pagamentos, 
                verificando contas a pagar vinculadas ao ID do usuário (CPF ou CNPJ) e oferecendo uma ferramenta de geração de boletos. 
                
                Além disso, utilizamos medidas variadas de segurança, como reconhecimento facial, biometria e autenticação multifator.
                """;

        Text lblTextBox = new Text(texto);
        lblTextBox.setFont(Font.font("Montserrat", FontWeight.NORMAL, 13)); // 🔹 Reduzi um pouco a fonte para melhor encaixe
        lblTextBox.setFill(Color.WHITE);

        TextFlow textFlow = new TextFlow(lblTextBox);
        textFlow.setTextAlignment(TextAlignment.JUSTIFY);
        textFlow.setMaxWidth(WINDOW_WIDTH - 50); // 🔹 Reduzi para evitar ultrapassar a `VBox`
        textFlow.setLineSpacing(2.0);

        return textFlow;
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
