package br.ifmg.produto1_2026.service;

import br.ifmg.produto1_2026.entities.Usuario;
import br.ifmg.produto1_2026.util.NotificacaoEmail;
import br.ifmg.produto1_2026.util.Notificador;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //mesma coisa que @Component
public class AtivacaoClienteService {
    //@Autowired <--- essa é uma forma de injetar o bean (forma 1)
    private List<Notificador> notificadores;

    //forma 2 --> no construtor inserir o bean como parametro
    @Autowired //<--- forma 3 - quando existe overload de construtores
    public AtivacaoClienteService(List<Notificador> notificadores) {
        System.out.println("Iniciando AtivacaoClienteService");
        this.notificadores = notificadores;
    }

    public AtivacaoClienteService() {
        System.out.println("Iniciando AtivacaoClienteService com o construtor sem parametro");
    }

    public void ativar(Usuario usuario, String mensagem){
        //usuario.ativo();
        //if(notificador != null)
        //    notificador.notificar(usuario, mensagem);

        for(Notificador notificador : notificadores){
            notificador.notificar(usuario, mensagem);
        }
    }

    @PostConstruct
    public void init(){
        System.out.println("Metodo executado depois do construtor");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("Metodo executado ao destruir o objeto");
    }
}
