package com.allpay.projeto.controller;

import com.allpay.projeto.model.BankAccountModelDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BankAccountController {

  //public void atualizarSaldo ()

  public void findUserBankAccount(String id){
    ArrayList<HashMap<String,String>> bancosDisponiveis = new BankAccountModelDAO().findUserBankAccount(User.getId_usuario());
    if(!bancosDisponiveis.isEmpty()){

      bancosDisponiveis.forEach(elemento ->
        System.out.println(elemento.get("nome_instituicao") + " | " + "Código do banco: "+elemento.get("id_instituicao") + " | " + "Limite: R$" + elemento.get("limite") + " | " + "Saldo: R$"+elemento.get("saldo_usuario")));
    } else {
      System.out.println("erro");
    }
  }
}
