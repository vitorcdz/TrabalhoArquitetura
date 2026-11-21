package com.estacionamento.estacionamento.adapter.in.web.dto;

import com.estacionamento.estacionamento.domain.model.Ticket;

import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        String placa,
        Long vagaId,
        LocalDateTime entrada,
        LocalDateTime saida,
        String status
) {

    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getPlaca(),
                ticket.getVagaId(),
                ticket.getEntrada(),
                ticket.getSaida(),
                ticket.getStatus()
        );
    }
}
