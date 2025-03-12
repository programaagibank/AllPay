package com.allpay.projeto.controller;

import com.allpay.projeto.model.UserModelDAO;

import java.sql.ResultSet;

public class UserController {
    private UserModelDAO userModel;

    public void select(){
      this.userModel = new UserModelDAO();
      this.userModel.select();
      }

}
