
package com.estacionamento.estacionamento.adapter.out.persistence;

import com.estacionamento.estacionamento.application.port.out.TicketRepositoryPort;
import com.estacionamento.estacionamento.domain.model.Ticket;
import com.estacionamento.estacionamento.adapter.out.persistence.entity.TicketEntity;
import com.estacionamento.estacionamento.adapter.out.persistence.repository.TicketJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepositoryAdapter
        implements TicketRepositoryPort {

    private final TicketJpaRepository repository;

    public TicketRepositoryAdapter(
            TicketJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ticket salvar(Ticket ticket) {

        TicketEntity entity = new TicketEntity();

        entity.setId(ticket.getId());
        entity.setPlaca(ticket.getPlaca());
        entity.setVagaId(ticket.getVagaId());
        entity.setEntrada(ticket.getEntrada());
        entity.setSaida(ticket.getSaida());
        entity.setStatus(ticket.getStatus());

        TicketEntity salvo = repository.save(entity);

        return converter(salvo);
    }

    @Override
    public Optional<Ticket> buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::converter);
    }

    @Override
    public boolean existeTicketAtivo(String placa) {
        return repository.existsByPlacaAndStatus(
                placa, "ATIVO"
        );
    }

    @Override
    public List<Ticket> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    @Override
    public List<Ticket> listarPorStatus(String status) {
        return repository.findByStatus(status)
                .stream()
                .map(this::converter)
                .toList();
    }

    private Ticket converter(TicketEntity entity) {

        Ticket ticket = new Ticket();

        ticket.setId(entity.getId());
        ticket.setPlaca(entity.getPlaca());
        ticket.setVagaId(entity.getVagaId());
        ticket.setEntrada(entity.getEntrada());
        ticket.setSaida(entity.getSaida());
        ticket.setStatus(entity.getStatus());

        return ticket;
    }
}
