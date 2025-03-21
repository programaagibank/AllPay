package com.allpay.projeto.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FrontEntrada extends Application {

    @Override
    public void start(Stage primaryStage) {
        String title = "Allpay";

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(150, 0, 0, 0));

        setBackground(layout, "/images/backgroundImage.png");

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/logoAllpay.png").toExternalForm()));
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);

        Region spacer = new Region();
        spacer.setPrefHeight(120);

        Button btnLogin = new Button("Login");
        Button btnCadastro = new Button("Cadastro");
        Button btnSair = new Button("Sair");

        String buttonStyle = "-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: #000000; " +
                "-fx-font-size: 18px; " +
                "-fx-padding: 5px 20px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; " +
                "-fx-min-width: 250px; " +
                "-fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; " +
                "-fx-font-weight: bold;";

        String sairButtonStyle = "-fx-background-color: #000000; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 18px; " +
                "-fx-padding: 5px 20px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; " +
                "-fx-min-width: 250px; " +
                "-fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; " +
                "-fx-font-weight: bold;";

        btnLogin.setStyle(buttonStyle);
        btnCadastro.setStyle(buttonStyle);
        btnSair.setStyle(sairButtonStyle);

        btnLogin.setOnAction(e -> System.out.println("Você escolheu Login."));
        btnCadastro.setOnAction(e -> System.out.println("Você escolheu Cadastro."));
        btnSair.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(logo, spacer, btnLogin, btnCadastro, btnSair);

        showSplashScreen(primaryStage, layout);
    }

    private void showSplashScreen(Stage primaryStage, VBox mainLayout) {
        Stage splashStage = new Stage();
        VBox splashLayout = new VBox(40);
        splashLayout.setAlignment(Pos.CENTER);

        setBackground(splashLayout, "/images/backgroundImage.png");

        ImageView logo = new ImageView(new Image(getClass().getResource("/images/logoAllpay.png").toExternalForm()));
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);

        Text text = new Text("Sua revolução em Open Finance!");
        text.setFont(Font.font("Montserrat", 16));
        text.setFill(Color.WHITE);

        splashLayout.getChildren().addAll(logo, text);

        Scene splashScene = new Scene(splashLayout, 320, 620);
        splashStage.setScene(splashScene);
        splashStage.setResizable(false);
        splashStage.show();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                javafx.application.Platform.runLater(() -> {
                    splashStage.close();
                    Scene mainScene = new Scene(mainLayout, 320, 600);
                    primaryStage.setScene(mainScene);
                    primaryStage.setTitle("Allpay");
                    primaryStage.setResizable(false);
                    primaryStage.show();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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