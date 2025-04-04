package com.allpay.projeto.view;

import com.allpay.projeto.Main;
import com.allpay.projeto.DAO.FaturaDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Alert;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class FrontGerarComprovante {
    private final VBox view;
    private final Main main;
    private final String idUsuario;
    private final String idFatura;
    private HashMap<String, String> faturaData;

    public FrontGerarComprovante(Main main, String idUsuario, String idFatura) {
        this.main = main;
        this.idUsuario = idUsuario;
        this.idFatura = idFatura;
        this.view = new VBox(20);
        setupView();
    }

    private void setupView() {
        view.setAlignment(Pos.TOP_CENTER);
        view.setPadding(new Insets(40, 20, 20, 20));
        setBackground();

        Label lblTitulo = createLabel("Comprovante de Pagamento", 32, FontWeight.BOLD, Color.WHITE);
        lblTitulo.setWrapText(true);
        lblTitulo.setMaxWidth(350);

        FaturaDAO faturaDAO = new FaturaDAO();
        ArrayList<HashMap<String, String>> faturas = faturaDAO.buscarFaturasByUserId(idUsuario);

        for (HashMap<String, String> fatura : faturas) {
            if (fatura.get("id_fatura").equals(idFatura)) {
                this.faturaData = fatura;
                break;
            }
        }

        VBox contentBox = new VBox(15);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(400);

        if (faturaData != null) {
            Label lblValor = createWrappedLabel("Valor: R$ " + faturaData.get("valor_fatura"), 24, FontWeight.BOLD, Color.WHITE);
            Label lblRecebedor = createWrappedLabel("Recebedor: " + faturaData.get("nome_recebedor"), 16, FontWeight.NORMAL, Color.WHITE);
            Label lblDescricao = createWrappedLabel("Descrição: " + faturaData.get("descricao"), 16, FontWeight.NORMAL, Color.WHITE);
            Label lblStatus = createWrappedLabel("Status: " + faturaData.get("status_fatura"), 16, FontWeight.NORMAL, Color.WHITE);
            Label lblData = createWrappedLabel("Data do Pagamento: " +
                            (faturaData.getOrDefault("data_pagamento", "N/A")),
                    16, FontWeight.NORMAL, Color.WHITE);

            contentBox.getChildren().addAll(lblValor, lblRecebedor, lblDescricao, lblStatus, lblData);
        } else {
            Label lblErro = createWrappedLabel("Erro ao carregar dados do comprovante", 16, FontWeight.NORMAL, Color.LIGHTCORAL);
            contentBox.getChildren().add(lblErro);
        }

        Button btnPDF = criarBotao("Gerar PDF", "primary", e -> gerarPDF());
        Button btnVoltar = criarBotao("Voltar", "secondary", e -> main.mostrarTelaPrincipal(idUsuario, "Usuário"));

        VBox botoesBox = new VBox(10, btnPDF, btnVoltar);
        botoesBox.setAlignment(Pos.CENTER);
        btnPDF.setMaxWidth(Double.MAX_VALUE);
        btnVoltar.setMaxWidth(Double.MAX_VALUE);

        view.getChildren().addAll(lblTitulo, contentBox, botoesBox);
    }

    private void gerarPDF() {
        if (faturaData == null) {
            mostrarAlerta("Erro", "Nenhum dado disponível para gerar PDF", Alert.AlertType.ERROR);
            return;
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                PDImageXObject logo = PDImageXObject.createFromByteArray(
                        document,
                        Objects.requireNonNull(getClass().getResourceAsStream("/images/logoAllpayPreto.png")).readAllBytes(),
                        "logoAllpay"
                );
                contentStream.drawImage(logo, 100, 700, 280, (float) (logo.getHeight() * 280) / logo.getWidth());

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(100, 670);
                contentStream.showText("Sua revolução em Open Finance");
                contentStream.endText();

                contentStream.setLineWidth(1f);
                contentStream.moveTo(100, 660);
                contentStream.lineTo(500, 660);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.newLineAtOffset(100, 630);
                contentStream.showText("COMPROVANTE DE PAGAMENTO");
                contentStream.endText();

                float yPosition = 600;
                String[] lines = {
                        "ID Fatura: " + faturaData.get("id_fatura"),
                        "Recebedor: " + faturaData.get("nome_recebedor"),
                        "Valor: R$ " + faturaData.get("valor_fatura"),
                        "Descrição: " + faturaData.get("descricao"),
                        "Status: " + faturaData.get("status_fatura"),
                        "Data Pagamento: " + faturaData.getOrDefault("data_pagamento", "N/A")
                };

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                for (String line : lines) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yPosition);
                    contentStream.showText(line);
                    contentStream.endText();
                    yPosition -= 20;
                }

                PDImageXObject thumbsUp = PDImageXObject.createFromByteArray(
                        document,
                        Objects.requireNonNull(getClass().getResourceAsStream("/images/icon-thumbs-up.png")).readAllBytes(),
                        "thumbsUp"
                );
                contentStream.drawImage(thumbsUp, 200, yPosition - 100, 100, 100);

                contentStream.setLineWidth(1f);
                contentStream.moveTo(100, yPosition - 120);
                contentStream.lineTo(500, yPosition - 120);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
                contentStream.newLineAtOffset(100, yPosition - 140);
                contentStream.showText("Pagamento realizado digitalmente com AllPay.");
                contentStream.endText();

                String authCode = generateRandomAuthCode();
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(100, yPosition - 160);
                contentStream.showText("Código de Autenticação: " + authCode);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                contentStream.newLineAtOffset(100, yPosition - 180);
                contentStream.showText("Data Emissão: " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                contentStream.endText();
            }

            String fileName = "comprovante_" + faturaData.get("id_fatura") + ".pdf";
            document.save(fileName);
            mostrarAlerta("Sucesso", "PDF gerado com sucesso: " + fileName, Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao gerar PDF: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private String generateRandomAuthCode() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(tipo);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensagem);
            alert.showAndWait();
        });
    }

    private Label createLabel(String text, int fontSize, FontWeight fontWeight, Color textColor) {
        Label label = new Label(text);
        label.setFont(Font.font("Montserrat", fontWeight, fontSize));
        label.setTextFill(textColor);
        return label;
    }

    private Label createWrappedLabel(String text, int fontSize, FontWeight fontWeight, Color textColor) {
        Label label = createLabel(text, fontSize, fontWeight, textColor);
        label.setWrapText(true);
        label.setMaxWidth(350);
        return label;
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

    private void setBackground() {
        try {
            BackgroundImage bgImage = new BackgroundImage(
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backgroundImage.png"))),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, true, true)
            );
            view.setBackground(new Background(bgImage));
        } catch (Exception e) {
            view.setStyle("-fx-background-color: #121212;");
        }
    }

    public Parent getView() {
        return view;
    }
}