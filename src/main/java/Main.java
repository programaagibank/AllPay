import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    static final String URL = System.getenv("DB_URL");
    static final String USER = System.getenv("DB_USER");
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static void main(String[] args) {

        try {
            DataBaseConnection dbConnect = new MySQLDataBaseConnection(URL, USER, PASSWORD);

            dbConnect.connect();
            System.out.println("Conexao feita");
            //sql teste
            String sql = "SELECT id_usuario, nome_usuario FROM usuario";
            //forca o uso do banco allpay
            dbConnect.getConnection().createStatement().execute("USE allpay");
            //prepare e executa o sql
            PreparedStatement statement = dbConnect.getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

//            --------
//            teste
//            Map<String, Object> row = getRow(resultSet);
//            System.out.println("ID: " + row.get("id") + ", Name: " + row.get("name"));
//            ---------

            //itera e printa cada linha da tabela
            //result.next vem como um ponteiro para cada linha da coluna
            while (resultSet.next()) {
                //pega as colunas
                String id = resultSet.getString("id_usuario");
                String nome = resultSet.getString("nome_usuario");

                // Imprimindo os dados
                System.out.println(id + " - " + nome);
            }

            resultSet.close();
            statement.close();
            dbConnect.closeConnection();
            System.out.println("Conexao finalizada");
        } catch (Exception e) {
            //mostra no console onde que deu o erro com base na execuçao
            e.printStackTrace();
        }
    }
}
