package com.estacionamento.estacionamento.application.port.in;

import com.estacionamento.estacionamento.domain.model.Ticket;

public interface RegistrarEntradaUseCase{
    Ticket executar(String placa, Long vagaId);
}