package com.allpay.projeto.controller;

import com.allpay.projeto.DAO.ContaBancoDAO;
import com.allpay.projeto.DAO.FaturaDAO;
import com.allpay.projeto.model.UsuarioModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FaturaController {
    private final FaturaDAO modelFaturaDAO;

    public FaturaController (){
        this.modelFaturaDAO = new FaturaDAO();
    }
    public String escolherMetodoPag (int choice) {

        String metodo_pag;

        String[] opcoes = {"PIX", "CRÉDITO", "DÉBITO", "TED", "BOLETO"};


        metodo_pag = opcoes[choice];

        return metodo_pag;
    }
    public void buscarFaturasByUserId(String id_usuario){
        ArrayList<HashMap<String,String>> result = this.modelFaturaDAO.buscarFaturasByUserId(id_usuario);

        for (int i = 0; i < result.size(); i++) {
            System.out.println("Código da fatura: " + result.get(i).get("id_fatura"));
            System.out.println("Valor: R$" + result.get(i).get("valor_fatura"));
            System.out.println("Status: " + result.get(i).get("status_fatura"));
            System.out.println("Descrição: " + result.get(i).get("descricao"));
            System.out.println("Remetente: " + result.get(i).get("nome_recebedor"));
            System.out.println(" ");
        }
    }
    public float efetuarPagamento(int id_fatura, String senha_transacao, int selecaoBancoPagar){

        ContaBancoController bankAccountController = new ContaBancoController();
        ContaBancoDAO bankAccountModelDAO = new ContaBancoDAO();

        float valor_fatura = Float.parseFloat(this.modelFaturaDAO.buscarFaturasByUserId(UsuarioModel.getId_usuario()).get(id_fatura).get("valor_fatura"));

        float escolherBanco = bankAccountController.escolherBanco(selecaoBancoPagar);

        int id_instituicao = Integer.parseInt(bankAccountModelDAO.encontrarContaBancoUsuario(UsuarioModel.getId_usuario()).get(selecaoBancoPagar).get("id_instituicao"));

        float saldo_restante = this.modelFaturaDAO.efetuarPagamento(UsuarioModel.getId_usuario(),id_fatura,valor_fatura,escolherBanco, senha_transacao, id_instituicao);

        return saldo_restante;
    }
    public float efetuarPagamentoCartao(int id_fatura, String senha_transacao, int selecaoBancoPagar){

        ContaBancoController bankAccountController = new ContaBancoController();

        float valor_fatura = Float.parseFloat(this.modelFaturaDAO.data.get(id_fatura).get("valor_fatura"));
        float escolherBanco = bankAccountController.escolherBanco(Integer.parseInt(bankAccountController.bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));
        float saldo_restante = this.modelFaturaDAO.efetuarPagamentoCartao(UsuarioModel.getId_usuario(),Integer.parseInt(modelFaturaDAO.data.get(id_fatura).get("id_fatura")),valor_fatura,escolherBanco,senha_transacao,selecaoBancoPagar);

        return saldo_restante;
    }
    public void atulizaStatusFatura(int id_fatura){

        int id = Integer.parseInt(this.modelFaturaDAO.getData().get(id_fatura).get("id_fatura"));

        this.modelFaturaDAO.atualizarStatus_fatura(id);
    }
}
