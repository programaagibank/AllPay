package com.allpay.projeto.controller;

import com.allpay.projeto.model.ModelFaturaDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FaturaController {

    public String escolherMetodoPag (int choice) {

        String metodo_pag;

        String[] opcoes = {"PIX", "CRÉDITO", "DÉBITO", "TED", "BOLETO"};

        //System.out.println(Arrays.toString(opcoes));

        metodo_pag = opcoes[choice];

        return metodo_pag;
    }

    public void buscarFaturasByUserId(String id_usuario){
        ArrayList<HashMap<String,String>> result = new ModelFaturaDAO().buscarFaturasByUserId(id_usuario);

        for (int i = 0; i < result.size(); i++) {
            System.out.println("Código da fatura: " + result.get(i).get("id_fatura"));
            System.out.println("Valor: R$" + result.get(i).get("valor_fatura"));
            System.out.println("Status: " + result.get(i).get("status_fatura"));
            System.out.println("Descrição: " + result.get(i).get("descricao"));
            System.out.println("Remetente: " + result.get(i).get("nome_recebedor"));
            System.out.println(" ");
        }
    }
}
