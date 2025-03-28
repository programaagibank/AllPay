import static org.junit.jupiter.api.Assertions.*;

import com.allpay.projeto.controller.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {

  // Método para configurar os dados do usuário antes de cada teste
  @BeforeEach
  public void setUp() {
    User.setUserData("12345678901", "João Silva", "joao.silva@email.com", "senha123");
  }

  // Testando o método getNome()
  @Test
  public void testGetNome() {
    assertEquals("João Silva", User.getNome(), "O nome do usuário deve ser 'João Silva'");
  }

  // Testando o método getId_usuario()
  @Test
  public void testGetId_usuario() {
    assertEquals("12345678901", User.getId_usuario(), "O ID do usuário (CPF) deve ser '12345678901'");
  }

  // Testando o método getEmail()
  @Test
  public void testGetEmail() {
    assertEquals("joao.silva@email.com", User.getEmail(), "O email do usuário deve ser 'joao.silva@email.com'");
  }

  // Testando o método getSenha()
  @Test
  public void testGetSenha() {
    assertEquals("senha123", User.getSenha(), "A senha do usuário deve ser 'senha123'");
  }

  // Testando o método mostrarInfos()
  @Test
  public void testMostrarInfos() {
    // Redirecionar a saída padrão para capturar a saída do método mostrarInfos
    String expectedOutput = "Nome: João Silva\nCPF: 12345678901\nSenha: senha123\nEmail: joao.silva@email.com\n";

    // Podemos capturar a saída do System.out para comparação com a saída esperada.
    assertDoesNotThrow(() -> User.mostrarInfos(), "O método mostrarInfos não deve lançar exceções");
  }

  // Testando o método logout()
  @Test
  public void testLogout() {
    // Chamando logout e verificando se os dados foram limpos
    User.logout();

    // Verificando se os valores de todos os campos estão nulos após o logout
    assertNull(User.getNome(), "O nome do usuário deve ser nulo após logout");
    assertNull(User.getId_usuario(), "O ID do usuário (CPF) deve ser nulo após logout");
    assertNull(User.getEmail(), "O email do usuário deve ser nulo após logout");
    assertNull(User.getSenha(), "A senha do usuário deve ser nula após logout");
  }
  @Test
  public void testDivideByZero() {
    Runnable res = (int n1,int n2) -> n1/n2;
    // Verificando se a exceção ArithmeticException é lançada
    assertThrows(ArithmeticException.class, () -> {
      10/0; // Aqui estamos tentando dividir 10 por 0
    }, "Deve lançar uma ArithmeticException ao dividir por zero");
}
