import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class TestClass {
    @Test
    public void testSoma() {

        int resultado = 3 + 2;
        assertEquals(5, resultado, "A soma de 2 e 3 deve ser 5");
    }
}
