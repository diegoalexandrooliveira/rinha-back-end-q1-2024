package com.diegoalexandro.rinhabackend.commons;

import com.diegoalexandro.rinhabackend.transacao.exceptions.ClienteNaoExistenteException;
import com.diegoalexandro.rinhabackend.transacao.exceptions.ClienteSemSaldoDisponivel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler({
            ClienteNaoExistenteException.class
    })
    public ResponseEntity<Object> handle(Exception e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({
            ClienteSemSaldoDisponivel.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception e) {
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return ResponseEntity.unprocessableEntity().build();
    }

}
