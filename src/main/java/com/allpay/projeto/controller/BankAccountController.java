package com.allpay.projeto.controller;

import com.allpay.projeto.DAO.BankAccountDAO;

import java.util.ArrayList;
import java.util.HashMap;

public class BankAccountController {

  //public void atualizarSaldo ()

  public ArrayList<HashMap<String,String>> findUserBankAccount(String id){
    ArrayList<HashMap<String,String>> bancosDisponiveis = new BankAccountDAO().findUserBankAccount(id);
    if(!bancosDisponiveis.isEmpty()){

//      bancosDisponiveis.forEach(elemento ->
//        System.out.println(elemento.get("nome_instituicao") + " | " + elemento.get("conta") + " | " + elemento.get("limite") + " | " + elemento.get("saldo_usuario")));
     return bancosDisponiveis;
    } else {
      return bancosDisponiveis;
    }
  }
}
