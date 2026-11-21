package com.estacionamento.estacionamento.domain.exception;

public class VagaNaoEncontradaException extends RuntimeException {

    public VagaNaoEncontradaException(Long id) {
        super("Vaga não encontrada com o id: " + id);
    }
}