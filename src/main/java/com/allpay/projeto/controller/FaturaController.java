package com.allpay.projeto.controller;

import com.allpay.projeto.DAO.FaturaDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FaturaController {

    public static String escolherMetodoPag (int choice) {

        String metodo_pag;

        String[] opcoes = {"PIX", "CRÉDITO", "DÉBITO", "TED", "BOLETO"};

        System.out.println(Arrays.toString(opcoes));

        metodo_pag = opcoes[choice];

        return metodo_pag;
    }

    public void buscarFaturasByUserId(String id_usuario){
        ArrayList<HashMap<String,String>> result = new FaturaDAO().buscarFaturasByUserId(id_usuario);
        System.out.println("pritou faturas");
        System.out.println(result.get(0).get("id_fatura"));
    }
}
