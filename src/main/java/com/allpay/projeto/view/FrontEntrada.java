package com.allpay.projeto.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FrontEntrada extends Application {

    @Override
    public void start(Stage primaryStage) {
        String title = "allPay - Tela de Entrada";
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #1c1c1c;");

        Button btnLogin = new Button("Login");
        Button btnCadastro = new Button("Cadastro");
        Button btnSair = new Button("Sair");

        String buttonStyle = "-fx-background-color: #333333; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-background-radius: 20px;";

        btnLogin.setStyle(buttonStyle);
        btnCadastro.setStyle(buttonStyle);
        btnSair.setStyle(buttonStyle);

        btnLogin.setOnAction(e -> {
            System.out.println("Você escolheu Login.");
        });

        btnCadastro.setOnAction(e -> {
            System.out.println("Você escolheu Cadastro.");
        });

        btnSair.setOnAction(e -> {
            System.out.println("Você escolheu Sair.");
            primaryStage.close();
        });

        loadingScreen(primaryStage, layout, btnLogin, btnCadastro, btnSair);
    }

    private void loadingScreen(Stage primaryStage, VBox layout, Button btnLogin, Button btnCadastro, Button btnSair) {
        Stage loadingStage = new Stage();
        StackPane loadingLayout = new StackPane();
        loadingLayout.setStyle("-fx-background-color: #1c1c1c;");

        Text titleText = new Text("allPay");
        titleText.setFont(Font.font("Arial", 50));
        titleText.setFill(Color.WHITE);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setStyle("-fx-accent: #2196F3; -fx-background-color: #333333;");
        progressBar.setPrefWidth(300);

        loadingLayout.getChildren().addAll(titleText, progressBar);
        titleText.setTranslateY(-50);

        Scene loadingScene = new Scene(loadingLayout, 400, 300);
        loadingStage.setTitle("Carregando...");
        loadingStage.setScene(loadingScene);
        loadingStage.show();

        new Thread(() -> {
            try {
                long startTime = System.currentTimeMillis();
                long endTime = startTime + 2000; // 5 segundos

                while (System.currentTimeMillis() < endTime) {
                    double progress = (System.currentTimeMillis() - startTime) / 3000.0;
                    javafx.application.Platform.runLater(() -> progressBar.setProgress(progress));
                    Thread.sleep(50); // A cada 50ms atualiza a barra
                }

                javafx.application.Platform.runLater(() -> {
                    loadingStage.close();
                    layout.getChildren().addAll(btnLogin, btnCadastro, btnSair);
                    Scene mainScene = new Scene(layout, 400, 300);
                    primaryStage.setScene(mainScene);
                    primaryStage.setTitle("allPay");
                    primaryStage.show();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
