package com.allpay.projeto.interfaces;

import java.util.HashMap;

public interface InterfaceUsuarioDAO {

    public void insert(String id_usuario, String nome_usuario, String senha_acesso, String email);
    public HashMap<String, String> selectById(String id, String senha);

}
