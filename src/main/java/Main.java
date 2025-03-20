import com.allpay.projeto.controller.BankAccountController;
import com.allpay.projeto.controller.UserController;
import com.allpay.projeto.dbConnection.MySQLDataBaseConnection;
import com.allpay.projeto.interfaces.DataBaseConnection;
//import com.allpay.projeto.model.BankAccountModelDAO;
import com.allpay.projeto.model.ModelFaturaDAO;
import com.allpay.projeto.model.PaymentModelDAO;
import com.allpay.projeto.view.FrontEntrada;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        com.allpay.projeto.view.GerarComprovantePagamento(1);
//        PaymentModelDAO.com.allpay.projeto.view.GerarComprovantePagamento();
        FrontEntrada.launch(FrontEntrada.class, args);
//        new UserController().select();
//        new ModelFaturaDAO().buscarFaturas();

    }
}

