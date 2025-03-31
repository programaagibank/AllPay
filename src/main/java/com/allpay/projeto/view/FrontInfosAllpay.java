package com.allpay.projeto.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import javax.swing.text.Position;

public class FrontInfosAllpay extends Application {

    private ImageView loadingGif = new ImageView();

    @Override
    public void start(Stage primaryStage) {

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(90, 0, 0, 0));

        setBackground(layout, "/images/backgroundImage.png");

        Label lblTitulo = new Label("Sobre o AllPay");
        lblTitulo.setFont(Font.font("Montserrat", FontWeight.BOLD, 32));
        lblTitulo.setTextFill(Color.WHITE);

        String texto = "    Tendo em vista os altos números de analfabetismo digital e falta de educação financeiro na população brasileira, e tendo em vista também o público-alvo majoritário do AgiBank, o grupo busca atingir essa demográfica. \n\n    Isto é, pessoas com maior dificuldade no acesso às tecnologias, em geral da terceira ou maior idade. Prezamos, então, por uma interface amigável e intuitiva, com instruções de utilização claras. O projeto também tem por objetivo oferecer maior segurança e confiabilidade nos pagamentos, fazendo uma verificação das contas a pagar (vinculadas ao ID do usuário, seu CPF ou CNPJ) e oferecendo uma ferramenta de geração de boleto.    Visamos utilizar medidas variadas de segurança, baseadas em tecnologias como reconhecimento facial, biometria, multifactor authentication, entre outras.";

        Text lblTextBox = new Text(texto);
        lblTextBox.setFont(Font.font("Montserrat", FontWeight.NORMAL, 14));
        lblTextBox.setStyle("-fx-text-fill: #FFFFFF;");
        lblTextBox.setFill(Color.WHITE);

        layout.getChildren().add(lblTextBox);

        TextFlow textFlow = new TextFlow(lblTextBox);
        textFlow.setTextAlignment(TextAlignment.JUSTIFY);
        textFlow.setMaxWidth(250);
        textFlow.setLineSpacing(-2.0);

        layout.getChildren().addAll(lblTitulo, textFlow);

        Scene scene = new Scene(layout, 320, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sobre o AllPay");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private static void setBackground(Region layout, String imagePath) {
        Image backgroundImage = new Image(FrontLogin.class.getResource(imagePath).toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        layout.setBackground(new Background(bgImage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

