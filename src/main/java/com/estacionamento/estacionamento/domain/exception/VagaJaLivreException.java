package com.estacionamento.estacionamento.domain.exception;

public class VagaJaLivreException extends RuntimeException {

    public VagaJaLivreException(int numero) {
        super("A vaga " + numero + " já está livre");
    }
}