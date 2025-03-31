package com.allpay.projeto.controller;

import com.allpay.projeto.DAO.ContaBancoDAO;
import com.allpay.projeto.model.UsuarioModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ContaBancoController {
  ArrayList<HashMap<String,String>> bancosDisponiveis;
  //public void atualizarSaldo ()

public ContaBancoController(){
  bancosDisponiveis = new ArrayList<>();
}

  public ArrayList<HashMap<String, String>> getBancosDisponiveis() {
    return bancosDisponiveis;
  }

  public  ArrayList<HashMap<String, String>> findUserBankAccount(){
    bancosDisponiveis = new ContaBancoDAO().findUserBankAccount(UsuarioModel.getId_usuario());
    System.out.println(bancosDisponiveis.size());
    if(!bancosDisponiveis.isEmpty()){
//      bancosDisponiveis.forEach(elemento ->
//        System.out.println(elemento.get("nome_instituicao") + " | " + "Código do banco: "+elemento.get("id_instituicao") + " | " + "Limite: R$" + elemento.get("limite") + " | " + "Saldo: R$"+elemento.get("saldo_usuario")));
    return bancosDisponiveis;
    } else {
      System.out.println("erro");
      return bancosDisponiveis;
    }
  }

  public float escolherBanco (int selecaoBancoPagar) {

    ContaBancoDAO bankAccountModelDAO = new ContaBancoDAO();

    float saldo = new ContaBancoDAO().escolherBanco(UsuarioModel.getId_usuario(), Integer.parseInt(bankAccountModelDAO.findUserBankAccount(UsuarioModel.getId_usuario()).get(selecaoBancoPagar).get("id_instituicao")));

    return saldo;
  }

  public float escolherBancoCartao (int selecaoBancoPagar) {

    float saldo = new ContaBancoDAO().escolherBancoCartao(UsuarioModel.getId_usuario(), Integer.parseInt(bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));

    return saldo;
  }

  public boolean validarSenha (String senha, int selecaoBancoPagar) {

    boolean validacao = new ContaBancoDAO().validarSenha(senha, UsuarioModel.getId_usuario(), Integer.parseInt(getBancosDisponiveis().get(selecaoBancoPagar).get("id_instituicao")));

    return validacao;
  }

  public void saldoUpdate (float saldo_restante, int selecaoBancoPagar) {

    new ContaBancoDAO().saldoUpdate(saldo_restante, UsuarioModel.getId_usuario(), Integer.parseInt(bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));

  }

  public void limiteUpdate (float saldo_restante, int selecaoBancoPagar) {

    new ContaBancoDAO().limiteUpdate(saldo_restante, UsuarioModel.getId_usuario(), Integer.parseInt(bancosDisponiveis.get(selecaoBancoPagar).get("id_instituicao")));

  }
}
