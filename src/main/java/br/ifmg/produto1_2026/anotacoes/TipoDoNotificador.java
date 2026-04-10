package br.ifmg.produto1_2026.anotacoes;

import br.ifmg.produto1_2026.constants.TipoDeNotificacao;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface TipoDoNotificador {
    TipoDeNotificacao value();
}
