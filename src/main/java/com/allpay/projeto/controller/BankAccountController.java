package com.allpay.projeto.controller;

import com.allpay.projeto.model.BankAccountModelDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BankAccountController {

  //public void atualizarSaldo ()

  public void findUserBankAccount(){
    ArrayList<HashMap<String,String>> bancosDisponiveis = new BankAccountModelDAO().findUserBankAccount("11223344556");
    if(!bancosDisponiveis.isEmpty()){

      bancosDisponiveis.forEach(elemento ->
        System.out.println(elemento.get("nome_instituicao") + " | " + elemento.get("conta") + " | " + elemento.get("limite") + " | " + elemento.get("saldo_usuario")));
    } else {
      System.out.println("erro");
    }
  }
}
