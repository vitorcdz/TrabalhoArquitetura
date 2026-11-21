package com.estacionamento.estacionamento.domain.exception;

public class VagaOcupadaException extends RuntimeException {

    public VagaOcupadaException(int numero) {
        super("A vaga " + numero + " já está ocupada");
    }
}