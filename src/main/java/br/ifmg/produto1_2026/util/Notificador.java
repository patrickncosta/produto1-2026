package br.ifmg.produto1_2026.util;

import br.ifmg.produto1_2026.entities.Usuario;

public interface Notificador {
    public void notificar(Usuario usuario, String mensagem);
}


