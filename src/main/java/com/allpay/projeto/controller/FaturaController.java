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
    public ArrayList<HashMap<String,String>> buscarFaturasByUserId(String id_usuario){
        ArrayList<HashMap<String,String>> result = this.modelFaturaDAO.buscarFaturasByUserId(id_usuario);
        return result;
    }
    public float efetuarPagamento(String id_usuarioOut, int id_fatura, float valor_fatura,
                                  float saldo_usuario, String senha_transacao, int id_instituicao){

        float saldo_restante = this.modelFaturaDAO.efetuarPagamento(id_usuarioOut, id_fatura, valor_fatura, saldo_usuario, senha_transacao, id_instituicao);

        return saldo_restante;
    }
    public float efetuarPagamentoCartao(String id_usuarioOut, int id_fatura, float valor_fatura,
                                        float saldo_usuario, String senha_transacao, int id_instituicao){

        float saldo_restante = this.modelFaturaDAO.efetuarPagamento(id_usuarioOut, id_fatura, valor_fatura, saldo_usuario, senha_transacao, id_instituicao);

        return saldo_restante;
    }
    public void atulizaStatusFatura(int id_fatura){

        int id = Integer.parseInt(this.modelFaturaDAO.getData().get(id_fatura).get("id_fatura"));

        this.modelFaturaDAO.atualizarStatus_fatura(id);
    }
}
