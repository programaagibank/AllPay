package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import com.allpay.projeto.DAO.ContaBancoDAO;
import com.allpay.projeto.DAO.FaturaDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import java.util.HashMap;

public class FrontPagarFatura {
    private final VBox view;
    private final Main main;
    private final String idUsuario;
    private final String idPagamento;
    private HashMap<String, String> faturaData;
    private String bancoSelecionado;
    private String metodoPagamento;
    private float saldoBancoSelecionado;

    public FrontPagarFatura(Main main, String idUsuario, String idPagamento) {
        this.main = main;
        this.idUsuario = idUsuario;
        this.idPagamento = idPagamento;
        this.view = new VBox(15);
        carregarDadosFatura();
        mostrarTelaFatura();
    }

    public Parent getView() {
        return view;
    }

    private void carregarDadosFatura() {
        FaturaDAO faturaDAO = new FaturaDAO();
        ArrayList<HashMap<String, String>> faturas = faturaDAO.buscarFaturas(idUsuario);

        for (HashMap<String, String> fatura : faturas) {
            if (fatura.get("id_fatura").equals(idPagamento)) {
                this.faturaData = fatura;
                break;
            }
        }
    }

    private void mostrarTelaFatura() {
        view.getChildren().clear();
        view.setAlignment(Pos.TOP_CENTER);
        view.setPadding(new Insets(20));
        setBackground();

        Label lblTitulo = new Label("Confirmar Fatura");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblValor = new Label("R$ " + faturaData.get("valor_fatura"));
        lblValor.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblVencimento = new Label("Vencimento: " + faturaData.get("data_vencimento"));
        lblVencimento.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label lblDescricao = new Label("Descrição: " + faturaData.get("descricao"));
        lblDescricao.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Button btnPagar = criarBotao("Pagar", "primary", e -> mostrarTelaBancos());
        Button btnVoltar = criarBotao("Voltar", "secondary", e -> main.mostrarTelaPrincipal(idUsuario, "Usuário"));

        view.getChildren().addAll(lblTitulo, lblValor, lblVencimento, lblDescricao, btnPagar, btnVoltar);
    }

    private void mostrarTelaBancos() {
        view.getChildren().clear();

        Label lblTitulo = new Label("Escolher Banco");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblValor = new Label("R$ " + faturaData.get("valor_fatura"));
        lblValor.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        ScrollPane scrollBancos = new ScrollPane();
        VBox bancosContainer = new VBox(10);

        ContaBancoDAO bankDAO = new ContaBancoDAO();
        ArrayList<HashMap<String, String>> bancos = bankDAO.findUserBankAccount(idUsuario);

        float valorFatura = Float.parseFloat(faturaData.get("valor_fatura"));

        for (HashMap<String, String> banco : bancos) {
            float saldo = Float.parseFloat(banco.get("saldo_usuario"));
            Button btnBanco = criarBotaoBanco(banco.get("nome_instituicao"), saldo >= valorFatura);

            if (saldo >= valorFatura) {
                btnBanco.setOnAction(e -> {
                    this.bancoSelecionado = banco.get("nome_instituicao");
                    this.saldoBancoSelecionado = saldo;
                    mostrarTelaMetodosPagamento();
                });
            }

            bancosContainer.getChildren().add(btnBanco);
        }

        scrollBancos.setContent(bancosContainer);
        scrollBancos.setFitToWidth(true);

        Button btnVoltar = criarBotao("Voltar", "secondary", e -> mostrarTelaFatura());

        view.getChildren().addAll(lblTitulo, lblValor, scrollBancos, btnVoltar);
    }

    // Tela 3: Seleção de Método de Pagamento
    private void mostrarTelaMetodosPagamento() {
        view.getChildren().clear();

        Label lblTitulo = new Label("Escolher Método de Pagamento");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblValorFatura = new Label("R$ " + faturaData.get("valor_fatura"));
        lblValorFatura.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblSaldo = new Label("Saldo disponível: R$ " + saldoBancoSelecionado);
        lblSaldo.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        VBox metodosContainer = new VBox(10);

        String[] metodos = {"PIX", "Cartão de Crédito", "Cartão de Débito", "TED", "Boleto"};

        for (String metodo : metodos) {
            Button btnMetodo = criarBotao(metodo, "primary", e -> {
                this.metodoPagamento = metodo;
                mostrarTelaConfirmacao();
            });
            metodosContainer.getChildren().add(btnMetodo);
        }

        Button btnVoltar = criarBotao("Voltar", "secondary", e -> mostrarTelaBancos());

        view.getChildren().addAll(lblTitulo, lblValorFatura, lblSaldo, metodosContainer, btnVoltar);
    }

    // Tela 4: Confirmação de Pagamento
    private void mostrarTelaConfirmacao() {
        view.getChildren().clear();

        Label lblTitulo = new Label("Confirmar Pagamento");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblValor = new Label("R$ " + faturaData.get("valor_fatura"));
        lblValor.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblVencimento = new Label("Vencimento: " + faturaData.get("data_vencimento"));
        lblVencimento.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label lblDescricao = new Label("Descrição: " + faturaData.get("descricao"));
        lblDescricao.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label lblRecebedor = new Label("Recebedor: " + faturaData.get("nome_recebedor"));
        lblRecebedor.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label lblBanco = new Label("Pagar com: " + bancoSelecionado);
        lblBanco.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label lblMetodo = new Label("Método: " + metodoPagamento);
        lblMetodo.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label lblErro = new Label();
        lblErro.setStyle("-fx-text-fill: #ff4444;");
        lblErro.setVisible(false);

        PasswordField pfSenha = new PasswordField();
        pfSenha.setPromptText("Senha de transação");
        pfSenha.setMaxWidth(200);

        Button btnConfirmar = criarBotao("Confirmar", "primary", e -> {
            if (validarSenha(pfSenha.getText())) {
                efetuarPagamento();
                main.mostrarComprovantePagamento(idUsuario, idPagamento);
            } else {
                lblErro.setText("Senha incorreta");
                lblErro.setVisible(true);
            }
        });

        Button btnVoltar = criarBotao("Voltar", "secondary", e -> mostrarTelaMetodosPagamento());

        view.getChildren().addAll(lblTitulo, lblValor, lblVencimento, lblDescricao,
                lblRecebedor, lblBanco, lblMetodo, pfSenha,
                lblErro, btnConfirmar, btnVoltar);
    }

    private boolean validarSenha(String senha) {
        ContaBancoDAO bankDAO = new ContaBancoDAO();
        // Implementar lógica para obter id_instituicao do banco selecionado
        return bankDAO.validarSenha(senha, idUsuario, 1); // Substituir 1 pelo id correto
    }

    private void efetuarPagamento() {
        FaturaDAO faturaDAO = new FaturaDAO();
        float valor = Float.parseFloat(faturaData.get("valor_fatura"));
        int idFatura = Integer.parseInt(idPagamento);

        if (metodoPagamento.equals("Cartão de Crédito")) {
            ContaBancoDAO bankDAO = new ContaBancoDAO();
            float limite = bankDAO.escolherBancoCartao(idUsuario, 1); // Substituir 1 pelo id correto
            faturaDAO.efetuarPagamentoCartao(idUsuario, idFatura, valor, limite, "senha", 1);
        } else {
            faturaDAO.efetuarPagamento(idUsuario, idFatura, valor, saldoBancoSelecionado, "senha", 1);
        }
    }

    private Button criarBotao(String texto, String tipo, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-min-width: 200px; " +
                (tipo.equals("primary") ?
                        "-fx-background-color: #FFFFFF; -fx-text-fill: #000000;" :
                        "-fx-background-color: #333333; -fx-text-fill: #FFFFFF;"));
        btn.setOnAction(acao);
        return btn;
    }

    private Button criarBotaoBanco(String nomeBanco, boolean habilitado) {
        Button btn = new Button(nomeBanco);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-min-width: 250px; " +
                (habilitado ?
                        "-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white;" :
                        "-fx-background-color: rgba(255,0,0,0.2); -fx-text-fill: #AAAAAA;"));
        btn.setDisable(!habilitado);
        return btn;
    }

    private void setBackground() {
        try {
            view.setStyle("-fx-background-image: url('/images/backgroundImage.png'); " +
                    "-fx-background-size: cover; -fx-background-position: center;");
        } catch (Exception e) {
            view.setStyle("-fx-background-color: #121212;");
        }
    }
}