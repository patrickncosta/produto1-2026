package br.ifmg.produto1_2026.resources.exception;

import br.ifmg.produto1_2026.service.exception.ErroNoBancoDeDados;
import br.ifmg.produto1_2026.service.exception.RegistroNaoEncontrado;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandartError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req){
        ValidationError error = new ValidationError();

        error.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        error.setMessage(e.getMessage());
        error.setError("Erro de validação nos dados enviados :(");
        error.setTimestamp(Instant.now());
        error.setPath(req.getRequestURI());

        for(FieldError field :
                e.getBindingResult().getFieldErrors()){

            error.addFieldMessage(
                    new FieldMessage(field.getField(),field.getDefaultMessage())
            );
        }

        /*e.getBindingResult().getAllErrors().forEach(
                (err) -> {
                    error.addFieldMessage(
                            new FieldMessage(err.get)
                    );
                });*/

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }
}
