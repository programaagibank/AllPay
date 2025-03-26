package com.allpay.projeto.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;

public class FrontEntrada extends Application {

    private static boolean primeiraExecucao = true;

    @Override
    public void start(Stage primaryStage) {
        if (primeiraExecucao) {
            mostrarSplashScreen(primaryStage);
            primeiraExecucao = false;
        } else {
            iniciarTelaPrincipal(primaryStage);
        }
    }

    private void mostrarSplashScreen(Stage primaryStage) {
        try {
            String videoPath = getClass().getResource("/videos/splash.mp4").toString();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(640);
            mediaView.setFitHeight(400);
            mediaView.setX(0);
            mediaView.setY(0);

            StackPane root = new StackPane(mediaView);
            root.setStyle("-fx-background-color: black;");

            Scene splashScene = new Scene(root, 320, 600);
            primaryStage.setScene(splashScene);
            primaryStage.show();

            mediaPlayer.setStopTime(Duration.seconds(2));
            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(() -> {
                Platform.runLater(() -> iniciarTelaPrincipal(primaryStage));
            });

        } catch (Exception e) {
            Platform.runLater(() -> iniciarTelaPrincipal(primaryStage));
        }
    }

    private void iniciarTelaPrincipal(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(150, 0, 0, 0));
        setBackground(layout, "/images/backgroundImage.png");

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/logoAllpay.png").toExternalForm()));
        logo.setFitWidth(280);
        logo.setPreserveRatio(true);

        Text text = new Text("Sua revolução em Open Finance");
        text.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));
        text.setFill(Color.WHITE);

        Region spacer = new Region();
        spacer.setPrefHeight(120);

        Button btnLogin = criarBotao("LOGIN", "-fx-background-color: #FFFFFF; -fx-text-fill: #000000;");
        Button btnCadastro = criarBotao("CADASTRO", "-fx-background-color: #FFFFFF; -fx-text-fill: #000000;");
        Button btnSair = criarBotao("SAIR", "-fx-background-color: #000000; -fx-text-fill: #FFFFFF;");

        btnLogin.setOnAction(e -> {
            try {
                navegarParaTela(new FrontLogin(), primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        btnCadastro.setOnAction(e -> {
            try {
                navegarParaTela(new FrontSignUp(), primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        btnSair.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(logo, text, spacer, btnLogin, btnCadastro, btnSair);
        Scene mainScene = new Scene(layout, 320, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Allpay");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Button criarBotao(String texto, String estilo) {
        Button btn = new Button(texto);
        btn.setStyle(estilo + " -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; -fx-min-width: 250px; -fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; -fx-font-weight: bold;");
        return btn;
    }

    private void navegarParaTela(Application tela, Stage primaryStage) throws Exception {
        primaryStage.close();
        tela.start(new Stage());
    }

    private void setBackground(Region layout, String imagePath) {
        Image backgroundImage = new Image(getClass().getResource(imagePath).toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        layout.setBackground(new Background(bgImage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}