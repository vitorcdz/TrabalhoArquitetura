package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.ConsultarTicketUseCase;
import com.estacionamento.estacionamento.application.port.out.TicketRepositoryPort;
import com.estacionamento.estacionamento.domain.exception.TicketNaoEncontradoException;
import com.estacionamento.estacionamento.domain.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarTicketService implements ConsultarTicketUseCase {

    private final TicketRepositoryPort repository;

    public ConsultarTicketService(TicketRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Ticket> listar(String status) {

        if (status == null || status.isBlank()) {
            return repository.listarTodos();
        }

        String normalizado = status.toUpperCase().trim();

        if (!"ATIVO".equals(normalizado) && !"FINALIZADO".equals(normalizado)) {
            throw new IllegalArgumentException(
                    "Status invalido. Use ATIVO ou FINALIZADO"
            );
        }

        return repository.listarPorStatus(normalizado);
    }

    @Override
    public Ticket buscarPorId(Long id) {

        return repository.buscarPorId(id)
                .orElseThrow(() -> new TicketNaoEncontradoException(id));
    }
}
