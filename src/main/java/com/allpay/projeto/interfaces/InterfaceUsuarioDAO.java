package com.allpay.projeto.interfaces;

import java.util.HashMap;

public interface InterfaceUsuarioDAO {

    public void inserir(String id_usuario, String nome_usuario, String senha_acesso, String email);
    public HashMap<String, String> procurarPeloId(String id, String senha);

}
