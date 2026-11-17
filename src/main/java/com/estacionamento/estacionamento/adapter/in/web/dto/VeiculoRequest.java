package com.estacionamento.estacionamento.adapter.in.web.dto;

import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeiculoRequest(

        @NotBlank(message = "A placa é obrigatória")
        String placa,

        @NotBlank(message = "O modelo é obrigatório")
        String modelo,

        @NotBlank(message = "A cor é obrigatória")
        String cor,

        @NotNull(message = "O tipo é obrigatório")
        TipoVeiculo tipo

) {}
