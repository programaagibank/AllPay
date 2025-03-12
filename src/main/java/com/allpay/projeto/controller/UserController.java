package com.allpay.projeto.controller;

import com.allpay.projeto.model.UserModelDAO;

import java.sql.ResultSet;

public class UserController {
    private UserModelDAO userModel;
    public void select(){
      this.userModel = new UserModelDAO();
      ResultSet resultSet = this.userModel.select();
        try {
          while (resultSet.next()) {
            //pega as colunas
            String id = resultSet.getString("id_usuario");
            String nome = resultSet.getString("nome_usuario");

            // Imprimindo os dados
            System.out.println(id + " - " + nome);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

}
