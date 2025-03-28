package com.allpay.projeto.controller;

import com.allpay.projeto.model.BankAccountModelDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BankAccountController {
  ArrayList<HashMap<String,String>> bancosDisponiveis;
  //public void atualizarSaldo ()

public BankAccountController(){
  bancosDisponiveis = new ArrayList<>();
}

  public ArrayList<HashMap<String, String>> getBancosDisponiveis() {
    return bancosDisponiveis;
  }

  public void findUserBankAccount(){
    bancosDisponiveis = new BankAccountModelDAO().findUserBankAccount(User.getId_usuario());
    if(!bancosDisponiveis.isEmpty()){

      bancosDisponiveis.forEach(elemento ->
        System.out.println(elemento.get("nome_instituicao") + " | " + "Código do banco: "+elemento.get("id_instituicao") + " | " + "Limite: R$" + elemento.get("limite") + " | " + "Saldo: R$"+elemento.get("saldo_usuario")));
    } else {
      System.out.println("erro");
    }
  }

  public float escolherBanco (int selecaoBancoPagar) {

    BankAccountModelDAO bankAccountModelDAO = new BankAccountModelDAO();

    float saldo = new BankAccountModelDAO().escolherBanco(User.getId_usuario(), Integer.parseInt(bankAccountModelDAO.findUserBankAccount(User.getId_usuario()).get(selecaoBancoPagar).get("id_instituicao")));

    return saldo;
  }

  public float escolherBancoCartao (int selecaoBancoPagar) {

    float saldo = new BankAccountModelDAO().escolherBancoCartao(User.getId_usuario(), Integer.parseInt(bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));

    return saldo;
  }

  public boolean validarSenha (String senha, int selecaoBancoPagar) {

    boolean validacao = new BankAccountModelDAO().validarSenha(senha, User.getId_usuario(), Integer.parseInt(getBancosDisponiveis().get(selecaoBancoPagar).get("id_instituicao")));

    return validacao;
  }

  public void saldoUpdate (float saldo_restante, int selecaoBancoPagar) {

    new BankAccountModelDAO().saldoUpdate(saldo_restante, User.getId_usuario(), Integer.parseInt(bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));

  }

  public void limiteUpdate (float saldo_restante, int selecaoBancoPagar) {

    new BankAccountModelDAO().limiteUpdate(saldo_restante, User.getId_usuario(), Integer.parseInt(bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));

  }
}
