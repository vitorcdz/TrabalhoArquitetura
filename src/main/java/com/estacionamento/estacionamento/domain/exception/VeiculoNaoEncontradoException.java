package com.estacionamento.estacionamento.domain.exception;

public class VeiculoNaoEncontradoException extends RuntimeException {

    public VeiculoNaoEncontradoException(String placa) {
        super("Veículo não encontrado com a placa: " + placa);
    }
}
