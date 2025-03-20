import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.allpay.projeto.view.FrontEntrada;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestClass {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outputStream)); // Redireciona a saída padrão para o stream de captura
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut); // Restaura a saída padrão original
    }

    @Test
    void testeMenu() throws InterruptedException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(inputStream);
        FrontEntrada.main(new String[]{});
        System.out.println("Saída capturada: " + outputStream.toString());


        String expectOutPut =  "Você escolheu Login. Vamos te redirecionar.";// O que queremos testar
        assertTrue(outputStream.toString().contains(expectOutPut));
//        assertEquals(expectOutPut + System.lineSeparator(), outputStream.toString()); // Verifica a saída
//        assertEquals(expectOutPut.trim(), outputStream.toString().trim());
    }

    @Test
    public void testSoma() {

        int resultado = 3 + 2;
        assertEquals(5, resultado, "A soma de 2 e 3 deve ser 5");
    }
}
