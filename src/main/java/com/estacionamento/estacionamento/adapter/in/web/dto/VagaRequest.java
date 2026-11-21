package com.estacionamento.estacionamento.adapter.in.web.dto;

import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VagaRequest(

        @NotNull(message = "O número da vaga é obrigatório")
        @Positive(message = "O número da vaga deve ser maior que zero")
        Integer numero,

        @NotNull(message = "O tipo é obrigatório")
        TipoVeiculo tipo

) {}