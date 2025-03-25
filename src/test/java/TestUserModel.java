import com.allpay.projeto.interfaces.DataBaseConnection;
import com.allpay.projeto.model.UserModelDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TesteUserModal {

    private UserModelDAO userModelDAO;
    private DataBaseConnection mockDbConnect;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        // Criando os mocks
        mockDbConnect = mock(DataBaseConnection.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        Statement mockStatement = mock(Statement.class); // Criando mock de Statement

        // Configurando os mocks
        when(mockDbConnect.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement); // Retornar mock de Statement
        when(mockStatement.execute(anyString())).thenReturn(false); // Correção: não usar doNothing(), mas sim thenReturn(false)

        // Criando instância do DAO com mock de conexão
        userModelDAO = new UserModelDAO(mockDbConnect);
    }

    @Test
    void testInsert() throws SQLException {
        // Simulando execução do insert sem erro
        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Chamando o método que queremos testar
        userModelDAO.insert("123", "João", "senha123", "joao@email.com");

        // Verificando se os métodos foram chamados corretamente
        verify(mockDbConnect).connect();
        verify(mockConnection).prepareStatement(any(String.class));
        verify(mockPreparedStatement, times(4)).setString(anyInt(), anyString());
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).close();
        verify(mockDbConnect).closeConnection();
    }

    @Test
    void testSelectById() throws SQLException {
        // Simulando um usuário existente no banco
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("id_usuario")).thenReturn("123");
        when(mockResultSet.getString("nome_usuario")).thenReturn("João");
        when(mockResultSet.getString("email")).thenReturn("joao@email.com");
        when(mockResultSet.getString("senha_acesso")).thenReturn("senha123");

        // Chamando o método que queremos testar
        HashMap<String, String> result = userModelDAO.selectById("123", "senha123");

        // Verificando se os dados retornados estão corretos
        assertNotNull(result);
        assertEquals("123", result.get("id_usuario"));
        assertEquals("João", result.get("nome_usuario"));
        assertEquals("joao@email.com", result.get("email"));
        assertEquals("senha123", result.get("senha_acesso"));

        // Verificando se os métodos foram chamados corretamente
        verify(mockDbConnect).connect();
        verify(mockConnection).prepareStatement(any(String.class));
        verify(mockPreparedStatement).setString(1, "123");
        verify(mockPreparedStatement).setString(2, "senha123");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).close();
        verify(mockPreparedStatement).close();
        verify(mockDbConnect).closeConnection();
    }
}
