package com.allpay.projeto.controller;

import java.util.Arrays;

public class FaturaController {

    public static String escolherMetodoPag (int choice) {

        String metodo_pag;

        String[] opcoes = {"PIX", "CRÉDITO", "DÉBITO", "TED", "BOLETO"};

        System.out.println(Arrays.toString(opcoes));

        metodo_pag = opcoes[choice];

        return metodo_pag;
    }
}
