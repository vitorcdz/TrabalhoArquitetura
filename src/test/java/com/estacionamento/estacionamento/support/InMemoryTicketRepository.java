package com.estacionamento.estacionamento.support;

import com.estacionamento.estacionamento.application.port.out.TicketRepositoryPort;
import com.estacionamento.estacionamento.domain.model.Ticket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTicketRepository implements TicketRepositoryPort {

    private final Map<Long, Ticket> banco = new LinkedHashMap<>();
    private long sequencia = 1;

    @Override
    public Ticket salvar(Ticket ticket) {
        if (ticket.getId() == null) {
            ticket.setId(sequencia++);
        }
        banco.put(ticket.getId(), ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public boolean existeTicketAtivo(String placa) {
        return banco.values().stream()
                .anyMatch(t -> t.getPlaca().equals(placa)
                        && "ATIVO".equals(t.getStatus()));
    }

    @Override
    public List<Ticket> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public List<Ticket> listarPorStatus(String status) {
        return banco.values().stream()
                .filter(t -> status.equals(t.getStatus()))
                .toList();
    }
}
