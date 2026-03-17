package br.ifmg.produto1_2026.resources.exception;

import br.ifmg.produto1_2026.service.exception.ErroNoBancoDeDados;
import br.ifmg.produto1_2026.service.exception.RegistroNaoEncontrado;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(RegistroNaoEncontrado.class)
    public ResponseEntity<StandartError> entityNotFound(RegistroNaoEncontrado e, HttpServletRequest req){
        StandartError error = new StandartError();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setError("Registro não encontrado :(");
        error.setTimestamp(Instant.now());
        error.setPath(req.getRequestURI());


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ErroNoBancoDeDados.class)
    public ResponseEntity<StandartError> dataBaseIntegrity(ErroNoBancoDeDados e, HttpServletRequest req){
        StandartError error = new StandartError();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setError("Erro de integridade no BD :(");
        error.setTimestamp(Instant.now());
        error.setPath(req.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
