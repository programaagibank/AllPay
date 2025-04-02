package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FrontEntrada {
    private final VBox view;
    private final Main main;

    public FrontEntrada(Main main) {
        this.main = main;
        this.view = new VBox(20);
        setupView();
    }

    public Parent getView() {
        return view;
    }

    private void setupView() {

        view.setAlignment(Pos.TOP_CENTER);
        view.setPadding(new Insets(150, 20, 20, 20));
        view.setPrefWidth(320);
        view.setPrefHeight(600);
        setBackground();

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/logoAllpay.png").toExternalForm()));
        logo.setFitWidth(280);
        logo.setPreserveRatio(true);

        Text slogan = new Text("Sua revolução em Open Finance");
        slogan.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));
        slogan.setFill(Color.WHITE);

        Region spacer = new Region();
        spacer.setPrefHeight(120);

        Button btnLogin = criarBotao("LOGIN", "primary");
        Button btnCadastro = criarBotao("CADASTRO", "primary");
        Button btnSair = criarBotao("SAIR", "secondary");

        btnLogin.setOnAction(e -> main.mostrarTelaLogin());
        btnCadastro.setOnAction(e -> main.mostrarTelaCadastro());
        btnSair.setOnAction(e -> System.exit(0));

        view.getChildren().addAll(logo, slogan, spacer, btnLogin, btnCadastro, btnSair);
    }

    private Button criarBotao(String text, String type) {
        Button btn = new Button(text);
        String baseStyle = "-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; -fx-min-width: 250px; -fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; -fx-font-weight: bold; ";

        if (type.equals("primary")) {
            btn.setStyle(baseStyle + "-fx-background-color: #FFFFFF; -fx-text-fill: #000000;");
        } else {
            btn.setStyle(baseStyle + "-fx-background-color: #000000; -fx-text-fill: #FFFFFF;");
        }
        return btn;
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

    public static void mostrarTelaSplash(Main main, Runnable onFinished) {
        try {
            String videoPath = FrontEntrada.class.getResource("/videos/splash.mp4").toString();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(640);
            mediaView.setFitHeight(400);

            StackPane root = new StackPane(mediaView);
            root.setStyle("-fx-background-color: black;");

            main.getPrimaryStage().setScene(new Scene(root, 320, 600));
            main.getPrimaryStage().show();

            mediaPlayer.setStopTime(Duration.seconds(2));
            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(() -> {
                onFinished.run();
                mediaPlayer.dispose();
            });

        } catch (Exception e) {
            onFinished.run();
        }
    }
}