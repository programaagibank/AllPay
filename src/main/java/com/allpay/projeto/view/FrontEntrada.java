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
import javafx.scene.text.*;
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
        logo.setFitWidth(280);
        logo.setPreserveRatio(true);

        Text text = new Text("Sua revolução em Open Finance!");
        text.setFont(Font.font("Montserrat", FontWeight.BOLD, 16));
        text.setFill(Color.WHITE);

        Region spacer = new Region();
        spacer.setPrefHeight(120);

        Button btnLogin = new Button("LOGIN");
        Button btnCadastro = new Button("CADASTRO");
        Button btnSair = new Button("SAIR");

        String buttonStyle = "-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: #000000; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; " +
                "-fx-min-width: 250px; " +
                "-fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; " +
                "-fx-font-weight: bold;";

        String sairButtonStyle = "-fx-background-color: #000000; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px; " +
                "-fx-min-width: 250px; " +
                "-fx-min-height: 40px; " +
                "-fx-font-family: 'Montserrat'; " +
                "-fx-font-weight: bold;";

        btnLogin.setStyle(buttonStyle);
        btnCadastro.setStyle(buttonStyle);
        btnSair.setStyle(sairButtonStyle);

        btnLogin.setOnAction(e -> {
            primaryStage.close();
            FrontLogin frontLogin = new FrontLogin();
            frontLogin.start(new Stage());
        });

        btnCadastro.setOnAction(e -> {
            primaryStage.close();
            FrontSignUp frontSignUp = new FrontSignUp();
            frontSignUp.start(new Stage());
        });
        btnSair.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(logo, text, spacer, btnLogin, btnCadastro, btnSair);

        Scene mainScene = new Scene(layout, 320, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Allpay");
        primaryStage.setResizable(false);
        primaryStage.show();
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