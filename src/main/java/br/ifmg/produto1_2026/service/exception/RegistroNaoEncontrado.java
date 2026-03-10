package br.ifmg.produto1_2026.service.exception;

public class RegistroNaoEncontrado extends RuntimeException{
    public RegistroNaoEncontrado() {
    }

    public RegistroNaoEncontrado(String message) {
        super(message);
    }
}
