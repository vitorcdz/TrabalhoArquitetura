package com.estacionamento.estacionamento.adapter.in.web.dto;

import com.estacionamento.estacionamento.domain.model.StatusVaga;
import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import com.estacionamento.estacionamento.domain.model.Vaga;

public record VagaResponse(
        Long id,
        int numero,
        TipoVeiculo tipo,
        StatusVaga status
) {

    public static VagaResponse from(Vaga vaga) {
        return new VagaResponse(
                vaga.getId(),
                vaga.getNumero(),
                vaga.getTipo(),
                vaga.getStatus()
        );
    }
}