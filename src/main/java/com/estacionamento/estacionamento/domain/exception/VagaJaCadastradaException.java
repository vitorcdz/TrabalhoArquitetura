package com.estacionamento.estacionamento.domain.exception;

public class VagaJaCadastradaException extends RuntimeException {

    public VagaJaCadastradaException(int numero) {
        super("Já existe uma vaga cadastrada com o número: " + numero);
    }
}