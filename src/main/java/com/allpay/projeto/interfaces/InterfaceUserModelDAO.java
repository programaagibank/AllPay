package com.allpay.projeto.interfaces;

import java.sql.ResultSet;
import java.util.HashMap;

public interface InterfaceUserModelDAO {

    public void insert(String id_usuario, String nome_usuario, String senha_acesso, String email);
    public HashMap<String, String> selectById(String id, String senha);

}
