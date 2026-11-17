package com.estacionamento.estacionamento.domain.exception;

public class VeiculoJaCadastradoException extends RuntimeException {

    public VeiculoJaCadastradoException(String placa) {
        super("Já existe um veículo cadastrado com a placa: " + placa);
    }
}