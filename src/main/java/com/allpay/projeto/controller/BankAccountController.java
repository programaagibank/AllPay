package com.allpay.projeto.controller;

import com.allpay.projeto.DAO.BankAccountDAO;
import com.allpay.projeto.model.UserModel;

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

  public void findUserBankAccount(String id_usuaria){
    bancosDisponiveis = new BankAccountDAO().findUserBankAccount(UserModel.getId_usuario());
    System.out.println("mudar para o do develop");
    if(!bancosDisponiveis.isEmpty()){

      bancosDisponiveis.forEach(elemento ->
        System.out.println(elemento.get("nome_instituicao") + " | " + "Código do banco: "+elemento.get("id_instituicao") + " | " + "Limite: R$" + elemento.get("limite") + " | " + "Saldo: R$"+elemento.get("saldo_usuario")));
    } else {
      System.out.println("erro");
    }
  }

  public float escolherBanco (int selecaoBancoPagar) {

    BankAccountDAO bankAccountModelDAO = new BankAccountDAO();

    float saldo = new BankAccountDAO().escolherBanco(UserModel.getId_usuario(), Integer.parseInt(bankAccountModelDAO.findUserBankAccount(UserModel.getId_usuario()).get(selecaoBancoPagar).get("id_instituicao")));

    return saldo;
  }

  public float escolherBancoCartao (int selecaoBancoPagar) {

    float saldo = new BankAccountDAO().escolherBancoCartao(UserModel.getId_usuario(), Integer.parseInt(bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));

    return saldo;
  }

  public boolean validarSenha (String senha, int selecaoBancoPagar) {

    boolean validacao = new BankAccountDAO().validarSenha(senha, UserModel.getId_usuario(), Integer.parseInt(getBancosDisponiveis().get(selecaoBancoPagar).get("id_instituicao")));

    return validacao;
  }

  public void saldoUpdate (float saldo_restante, int selecaoBancoPagar) {

    new BankAccountDAO().saldoUpdate(saldo_restante, UserModel.getId_usuario(), Integer.parseInt(bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));

  }

  public void limiteUpdate (float saldo_restante, int selecaoBancoPagar) {

    new BankAccountDAO().limiteUpdate(saldo_restante, UserModel.getId_usuario(), Integer.parseInt(bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));

  }
}
