package com.estacionamento.estacionamento.application.port.out;

import com.estacionamento.estacionamento.domain.model.Ticket;
import java.util.Optional;

public interface TicketRepositoryPort{
    Ticket salvar(Ticket ticket);
    Optional<Ticket> buscarPorId(Long id);
    boolean existeTicketAtivo(String placa);
}