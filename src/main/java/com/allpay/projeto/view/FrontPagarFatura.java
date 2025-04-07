package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import com.allpay.projeto.DAO.ContaBancoDAO;
import com.allpay.projeto.DAO.FaturaDAO;
import com.allpay.projeto.controller.ContaBancoController;
import com.allpay.projeto.controller.FaturaController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.HashMap;

public class FrontPagarFatura {
    private final VBox view;
    private final Main main;
    private final String idUsuario;
    private final String idPagamento;
    private int id_instituicao;
    private HashMap<String, String> faturaData;
    private String bancoSelecionado;
    private String metodoPagamento;
    private float saldoBancoSelecionado;
    private float limiteBancoSelecionado;
    private FaturaController faturaController;

    public FrontPagarFatura(Main main, String idUsuario, String idPagamento) {
        this.main = main;
        this.idUsuario = idUsuario;
        this.idPagamento = idPagamento;
        this.view = new VBox(15);
        faturaController = new FaturaController();
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

        // Espaço expansível para empurrar os botões para baixo
        Region espacador = new Region();
        VBox.setVgrow(espacador, Priority.ALWAYS);

        // Botões
        Button btnPagar = criarBotao("Pagar", "primary", e -> mostrarTelaBancos());
        btnPagar.setMaxWidth(Double.MAX_VALUE);

        Button btnVoltar = criarBotao("Voltar", "secondary", e -> main.mostrarTelaPrincipal(idUsuario, "Usuário"));
        btnVoltar.setMaxWidth(Double.MAX_VALUE);

        // Contêiner para os botões (mantém eles juntos na parte inferior)
        VBox boxBotoes = new VBox(10, btnPagar, btnVoltar);
        boxBotoes.setAlignment(Pos.CENTER);

        // Adicionando os elementos à tela
        view.getChildren().addAll(lblTitulo, lblValor, lblVencimento, lblDescricao, espacador, boxBotoes);
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
        ArrayList<HashMap<String, String>> bancos = bankDAO.encontrarContaBancoUsuario(idUsuario);
        float valorFatura = Float.parseFloat(faturaData.get("valor_fatura"));

        for (HashMap<String, String> banco : bancos) {
            float saldo = Float.parseFloat(banco.get("saldo_usuario"));
            float limite = Float.parseFloat(banco.get("limite"));
            Button btnBanco = criarBotaoBanco(banco.get("nome_instituicao"), saldo >= valorFatura);
            if (saldo >= valorFatura) {
                btnBanco.setOnAction(e -> {
                    this.bancoSelecionado = banco.get("nome_instituicao");
                    this.saldoBancoSelecionado = saldo;
                    this.limiteBancoSelecionado = limite;
                    id_instituicao = Integer.parseInt(banco.get("id_instituicao"));
                    mostrarTelaMetodosPagamento();
                });
            }
            bancosContainer.getChildren().add(btnBanco);
        }
        bancosContainer.setAlignment(Pos.CENTER);
        scrollBancos.setContent(bancosContainer);
        scrollBancos.setFitToWidth(true);
        scrollBancos.setStyle("-fx-background-color: transparent; fx-border-color:red");

        VBox.setVgrow(scrollBancos, Priority.ALWAYS);

        Platform.runLater(() -> {
            Node viewport = scrollBancos.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: transparent;");
            }
        });

        Button btnVoltar = criarBotao("Voltar", "secondary", e -> mostrarTelaFatura());
        btnVoltar.setMaxWidth(Double.MAX_VALUE);
        view.getChildren().addAll(lblTitulo, lblValor, scrollBancos, btnVoltar);
    }

    // Tela 3: Seleção de Método de Pagamento
    private void mostrarTelaMetodosPagamento() {
        view.getChildren().clear();

        Label lblTitulo = new Label("Escolher Método de Pagamento");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        lblTitulo.setWrapText(true);
        lblTitulo.setTextAlignment(TextAlignment.CENTER);
        Label lblValorFatura = new Label("R$ " + faturaData.get("valor_fatura"));
        lblValorFatura.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblSaldo = new Label("Saldo disponível: R$ " + saldoBancoSelecionado);
        lblSaldo.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        // Container para os botões de métodos de pagamento
        VBox metodosContainer = new VBox(10);
        metodosContainer.setAlignment(Pos.CENTER); // Alinha os botões no centro

        String[] metodos = {"PIX", "CREDITO", "DEBITO", "TED", "BOLETO"};

        for (String metodo : metodos) {
            Button btnMetodo = criarBotao(metodo, "primary", e -> {
                this.metodoPagamento = metodo;
                FaturaDAO.metodoPagamento = metodoPagamento;
                mostrarTelaConfirmacao();
            });

            btnMetodo.setWrapText(true); // Faz o texto quebrar linha
            btnMetodo.setMaxWidth(250); // Define uma largura máxima
            metodosContainer.getChildren().add(btnMetodo);
        }

        // Espaço expansível para empurrar o botão "Voltar" para baixo
        Region espacador = new Region();
        VBox.setVgrow(espacador, Priority.ALWAYS);

        // Botão "Voltar"
        Button btnVoltar = criarBotao("Voltar", "secondary", e -> mostrarTelaBancos());
        btnVoltar.setMaxWidth(Double.MAX_VALUE);

        // Container dos botões (mantém o "Voltar" fixo no final)
        VBox boxBotoes = new VBox(10, btnVoltar);
        boxBotoes.setAlignment(Pos.CENTER);

        // Adiciona os elementos à tela
        view.getChildren().addAll(lblTitulo, lblValorFatura, lblSaldo, metodosContainer, espacador, boxBotoes);
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
                efetuarPagamento(pfSenha.getText());
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

        return bankDAO.validarSenha(senha, idUsuario, id_instituicao); // Substituir 1 pelo id correto
    }

    private void efetuarPagamento(String senha) {
        ContaBancoController contaBancoController = new ContaBancoController();
        float valor = Float.parseFloat(faturaData.get("valor_fatura"));
        int idFatura = Integer.parseInt(idPagamento);

        if (metodoPagamento.equals("CREDITO")) {// Substituir 1 pelo id correto
            Float rest = this.faturaController.efetuarPagamentoCartao(idUsuario, idFatura, valor, limiteBancoSelecionado, senha, id_instituicao);
            contaBancoController.atualizarLimite(rest, id_instituicao);
        } else {
            Float rest = this.faturaController.efetuarPagamento(idUsuario, idFatura, valor, saldoBancoSelecionado, senha, id_instituicao);
            contaBancoController.atualizarSaldo(rest, id_instituicao);
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
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-min-width: 250px; -fx-text-fill: black;" +
                        "-fx-background-color: " + (habilitado ? "white; " : "rgba(255,255,255,0.7);"));
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