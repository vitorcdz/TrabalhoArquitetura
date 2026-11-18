package com.estacionamento.estacionamento.application.port.in;

import com.estacionamento.estacionamento.domain.model.Ticket;

public interface RegistrarSaidaUseCase{
    Ticket executar(Long ticketId);
}