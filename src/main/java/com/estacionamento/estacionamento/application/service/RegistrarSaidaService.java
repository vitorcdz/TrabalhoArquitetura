package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.RegistrarSaidaUseCase;
import com.estacionamento.estacionamento.application.port.out.TicketRepositoryPort;
import com.estacionamento.estacionamento.application.port.out.VagaRepositoryPort;
import com.estacionamento.estacionamento.domain.exception.TicketNaoEncontradoException;
import com.estacionamento.estacionamento.domain.exception.VagaNaoEncontradaException;
import com.estacionamento.estacionamento.domain.model.Ticket;
import com.estacionamento.estacionamento.domain.model.Vaga;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarSaidaService
        implements RegistrarSaidaUseCase {

    private final TicketRepositoryPort ticketRepository;
    private final VagaRepositoryPort vagaRepository;

    public RegistrarSaidaService(
            TicketRepositoryPort ticketRepository,
            VagaRepositoryPort vagaRepository) {

        this.ticketRepository = ticketRepository;
        this.vagaRepository = vagaRepository;
    }

    @Override
    @Transactional
    public Ticket executar(Long ticketId) {

        Ticket ticket = ticketRepository
                .buscarPorId(ticketId)
                .orElseThrow(() ->
                        new TicketNaoEncontradoException(ticketId)
                );

        ticket.finalizar();

        Vaga vaga = vagaRepository
                .buscarPorId(ticket.getVagaId())
                .orElseThrow(() ->
                        new VagaNaoEncontradaException(ticket.getVagaId())
                );

        vaga.liberar();
        vagaRepository.salvar(vaga);

        return ticketRepository.salvar(ticket);
    }
}
