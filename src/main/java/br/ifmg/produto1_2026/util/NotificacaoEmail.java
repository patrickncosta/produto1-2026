package br.ifmg.produto1_2026.util;

import br.ifmg.produto1_2026.entities.Usuario;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
public class NotificacaoEmail implements Notificador {

    private boolean caixaAlta;
    private String servidorSmtp;

    public NotificacaoEmail(String servidorSmtp){
        System.out.println("NotificacaoEmail criado com sucesso!");
        this.servidorSmtp = servidorSmtp;
    }

    public void notificar(Usuario usuario, String mensagem){
        if (caixaAlta){
            mensagem = mensagem.toUpperCase();
        }
        System.out.printf("Notificando %s através do email %s no servidor %s: %s\n", usuario.getNome(), usuario.getEmail(), servidorSmtp, mensagem);
    }

    public boolean isCaixaAlta() {
        return caixaAlta;
    }

    public void setCaixaAlta(boolean caixaAlta) {
        this.caixaAlta = caixaAlta;
    }

    public String getServidorSmtp() {
        return servidorSmtp;
    }

    public void setServidorSmtp(String servidorSmtp) {
        this.servidorSmtp = servidorSmtp;
    }
}
