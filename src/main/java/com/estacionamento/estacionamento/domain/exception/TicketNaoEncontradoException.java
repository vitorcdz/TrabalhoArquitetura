package com.estacionamento.estacionamento.domain.exception;

public class TicketNaoEncontradoException extends RuntimeException {

    public TicketNaoEncontradoException(Long id) {
        super("Ticket não encontrado com o id: " + id);
    }
}
