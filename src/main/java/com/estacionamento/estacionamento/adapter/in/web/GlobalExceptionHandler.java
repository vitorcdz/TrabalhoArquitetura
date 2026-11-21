package com.estacionamento.estacionamento.adapter.in.web;

import com.estacionamento.estacionamento.domain.exception.VagaJaCadastradaException;
import com.estacionamento.estacionamento.domain.exception.VagaJaLivreException;
import com.estacionamento.estacionamento.domain.exception.VagaNaoEncontradaException;
import com.estacionamento.estacionamento.domain.exception.VagaOcupadaException;
import com.estacionamento.estacionamento.domain.exception.VeiculoJaCadastradoException;
import com.estacionamento.estacionamento.domain.exception.VeiculoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VeiculoJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> duplicado(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(VeiculoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> naoEncontrado(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler({
            VagaOcupadaException.class,
            VagaJaLivreException.class,
            VagaJaCadastradaException.class,
            IllegalStateException.class
    })
    public ResponseEntity<Map<String, String>> conflitoDeEstado(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(VagaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> vagaNaoEncontrada(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> dadosInvalidos(Exception ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validacao(
            MethodArgumentNotValidException ex) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(Map.of("erro", mensagem));
    }
}