package com.estacionamento.estacionamento.domain.exception;

import com.estacionamento.estacionamento.domain.model.TipoVeiculo;

public class VagaIncompativelException extends RuntimeException {

    public VagaIncompativelException(int numeroVaga,
                                     TipoVeiculo tipoVaga,
                                     TipoVeiculo tipoVeiculo) {
        super("A vaga " + numeroVaga + " é para " + tipoVaga
                + " e não aceita veículo do tipo " + tipoVeiculo);
    }
}
