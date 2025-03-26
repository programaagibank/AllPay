
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Pagina2 extends StackPane {

  private MainApp mainApp;

  public Pagina2(MainApp mainApp) {
    this.mainApp = mainApp;

    Button btnIrParaPagina1 = new Button("Voltar para Pagina 1");

    btnIrParaPagina1.setOnAction(e -> {
      mainApp.trocarParaPagina1();
    });

    this.getChildren().add(btnIrParaPagina1);
  }
}
