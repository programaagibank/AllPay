
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Pagina1 extends StackPane {

  private MainApp mainApp;

  public Pagina1(MainApp mainApp) {
    this.mainApp = mainApp;

    Button btnIrParaPagina2 = new Button("Ir para Pagina 2");

    btnIrParaPagina2.setOnAction(e -> {
      mainApp.trocarParaPagina2();
    });

    this.getChildren().add(btnIrParaPagina2);
  }
}
