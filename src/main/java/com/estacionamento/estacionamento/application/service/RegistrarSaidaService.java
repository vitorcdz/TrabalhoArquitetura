package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.RegistrarSaidaUseCase;
import com.estacionamento.estacionamento.application.port.out.TicketRepositoryPort;
import com.estacionamento.estacionamento.application.port.out.VagaRepositoryPort;
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
                        new IllegalArgumentException(
                                "Ticket nao encontrado"
                        )
                );

        ticket.finalizar();

        Vaga vaga = vagaRepository
                .buscarPorId(ticket.getVagaId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Vaga nao encontrada"
                        )
                );

        vaga.setOcupada(false);
        vagaRepository.salvar(vaga);

        return ticketRepository.salvar(ticket);
    }
}