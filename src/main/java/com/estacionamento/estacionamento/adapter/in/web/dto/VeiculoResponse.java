package com.estacionamento.estacionamento.adapter.in.web.dto;

import com.estacionamento.estacionamento.domain.model.Veiculo;
import com.estacionamento.estacionamento.domain.model.TipoVeiculo;

public record VeiculoResponse(
        Long id,
        String placa,
        String modelo,
        String cor,
        TipoVeiculo tipo
) {

    public static VeiculoResponse from(Veiculo veiculo) {
        return new VeiculoResponse(
                veiculo.getId(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getTipo()
        );
    }
}
