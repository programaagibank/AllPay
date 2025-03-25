import com.allpay.projeto.viewOld.FrontEntradaOld;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestFrontEntradaOld {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testMenuOp1() throws InterruptedException {
        // Adicionando uma entrada extra para evitar erro de Scanner
        String input = "1\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Executa o código
        FrontEntradaOld.main(new String[]{});

        // Captura a saída do console
        String saida = outputStream.toString();

        // Verifica se a saída contém a mensagem esperada
        assertTrue(saida.contains("Você escolheu Login. Vamos te redirecionar."));
    }

    @Test
    void testMenuOp2() throws InterruptedException {
        String input = "2\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        FrontEntradaOld.main(new String[]{});
        String saida = outputStream.toString();
        assertTrue(saida.contains("Você escolheu Cadastro. Vamos te redirecionar."));
    }

    @Test
    void testMenuOp3() throws InterruptedException {
        String input = "3\n"; // Garante que há uma entrada válida para evitar erro
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        FrontEntradaOld.main(new String[]{});
        String saida = outputStream.toString();
        assertTrue(saida.contains("Você escolheu Sair. Obrigado por usar o allPay."));
    }
}
