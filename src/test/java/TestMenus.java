//import com.allpay.projeto.view.FrontEntrada;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.util.InputMismatchException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TestMenus {
//    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;
//    private final java.io.InputStream originalIn = System.in;
//
//    @BeforeEach
//    void setUpStreams() {
//        System.setOut(new PrintStream(outputStream)); // Redireciona a saída padrão para o stream de captura
//    }
//
//    @AfterEach
//    void restoreStreams() {
//        System.setOut(originalOut); // Restaura a saída padrão original
//    }
//
//    @Test
//    void testeMenuOp1() throws InterruptedException {
//        //teste: selecionar opcao 1 do menu
//        ByteArrayInputStream inputStream = new ByteArrayInputStream("1\n".getBytes());
//        System.setIn(inputStream);
//        FrontEntrada.main(new String[]{});
//        System.out.println("Saída capturada: " + outputStream.toString());
//
//
//        String expectOutPut =  "Você escolheu Login. Vamos te redirecionar.";// O que queremos testar
//        String expectOutPut2 =  "Login";// O que queremos testar
//
//        assertTrue(outputStream.toString().contains(expectOutPut));
//        assertTrue(outputStream.toString().contains(expectOutPut2));
//
//    }
//    @Test
//    void testeMenuOp2() throws InterruptedException {
//        //teste: selecionar opcao 1 do menu
//        ByteArrayInputStream inputStream = new ByteArrayInputStream("2\n".getBytes());
//        System.setIn(inputStream);
//        FrontEntrada.main(new String[]{});
//        System.out.println("Saída capturada: " + outputStream.toString());
//
//        String expectOutPut =  "Você escolheu Cadastro. Vamos te redirecionar.";// O que queremos testar
//        String expectOutPut2 =  "Cadastro";// O que queremos testar
//
//        assertTrue(outputStream.toString().contains(expectOutPut));
//        assertTrue(outputStream.toString().contains(expectOutPut2));
//
//    }
//    @Test
//    void testeMenuOp3() throws InterruptedException {
//        //teste: selecionar opcao 1 do menu
//        ByteArrayInputStream inputStream = new ByteArrayInputStream("3\n".getBytes());
//        System.setIn(inputStream);
//        FrontEntrada.main(new String[]{});
//        System.out.println("Saída capturada: " + outputStream.toString());
//
//        String expectOutPut = "Você escolheu Sair. Obrigado por usar o allPay.";// O que queremos testar
//
//        assertTrue(outputStream.toString().contains(expectOutPut));
//
//    }
//
//    @Test
//    void testeMenuOutOp() throws InterruptedException {
//        //teste: selecionar opcao 1 do menu
//        ByteArrayInputStream inputStream = new ByteArrayInputStream("4\n".getBytes());
//        System.setIn(inputStream);
//        FrontEntrada.main(new String[]{});
//        System.out.println("Saída capturada: " + outputStream.toString());
//
//        String expectOutPut = "Opção inválida! Tente novamente.";// O que queremos testar
//
//        assertTrue(outputStream.toString().contains(expectOutPut));
//
//    }
//
//    @Test
//    void testMensagemErroEntradaInvalida() throws InterruptedException {
//        // Simula entrada inválida
//        ByteArrayInputStream inputStream = new ByteArrayInputStream("#\n".getBytes());
//        System.setIn(inputStream);
//
//        // Captura a saída do console
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//
//        // Executa o código
//        FrontEntrada.main(new String[]{});
//
//        // Verifica se a mensagem de erro foi exibida
//        String saidaPrograma = outputStream.toString();
//        assertTrue(saidaPrograma.contains("Erro: Entrada inválida! Digite um número inteiro."));
//    }
//
//    @Test
//    public void testSoma() {
//
//        int resultado = 3 + 2;
//        assertEquals(5, resultado, "A soma de 2 e 3 deve ser 5");
//    }
//
//
//}
