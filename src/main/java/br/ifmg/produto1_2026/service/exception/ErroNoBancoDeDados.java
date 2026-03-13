package br.ifmg.produto1_2026.service.exception;

public class ErroNoBancoDeDados extends RuntimeException{
    public ErroNoBancoDeDados() {
    }

    public ErroNoBancoDeDados(String message) {
        super(message);
    }
}
