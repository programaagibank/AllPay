package com.allpay.projeto.trasicoes;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {

  private StackPane root;

  @Override
  public void start(Stage primaryStage) {
    root = new StackPane();

    // Instâncias das páginas
    Pagina1 pagina1 = new Pagina1(this);  // Passando a referência de com.allpay.projeto.trasicoes.MainApp
    Pagina2 pagina2 = new Pagina2(this);

    // Adicionando as páginas ao root, mas apenas uma delas será visível
    root.getChildren().addAll(pagina1, pagina2);

    // Exibindo a com.allpay.projeto.trasicoes.Pagina1 inicialmente
    pagina1.setVisible(true);
    pagina2.setVisible(false);

    // Configurando a cena
    Scene scene = new Scene(root, 300, 250);
    primaryStage.setScene(scene);
    primaryStage.setTitle("App com Transições");
    primaryStage.show();
  }

  // Método para trocar para a com.allpay.projeto.trasicoes.Pagina2 com animação
  public void trocarParaPagina2() {
    Pagina1 pagina1 = (Pagina1) root.getChildren().get(0);
    Pagina2 pagina2 = (Pagina2) root.getChildren().get(1);

    FadeTransition fadeOut = new FadeTransition(Duration.millis(300), pagina1);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);
    fadeOut.setOnFinished(event -> {
      pagina1.setVisible(false);
      pagina2.setVisible(true);
      FadeTransition fadeIn = new FadeTransition(Duration.millis(300), pagina2);
      fadeIn.setFromValue(0.0);
      fadeIn.setToValue(1.0);
      fadeIn.play();
    });
    fadeOut.play();
  }

  // Método para trocar para a com.allpay.projeto.trasicoes.Pagina1 com animação
  public void trocarParaPagina1() {
    Pagina1 pagina1 = (Pagina1) root.getChildren().get(0);
    Pagina2 pagina2 = (Pagina2) root.getChildren().get(1);

    FadeTransition fadeOut = new FadeTransition(Duration.millis(300), pagina2);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.0);
    fadeOut.setOnFinished(event -> {
      pagina2.setVisible(false);
      pagina1.setVisible(true);
      FadeTransition fadeIn = new FadeTransition(Duration.millis(300), pagina1);
      fadeIn.setFromValue(0.0);
      fadeIn.setToValue(1.0);
      fadeIn.play();
    });
    fadeOut.play();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
