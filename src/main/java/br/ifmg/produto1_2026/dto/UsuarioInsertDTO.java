package br.ifmg.produto1_2026.dto;

import br.ifmg.produto1_2026.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioInsertDTO extends UsuarioDTO {
    private String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "senha='" + senha + '\'' +
                '}';
    }
}

