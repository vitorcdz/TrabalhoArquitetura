package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.RegistrarEntradaUseCase;
import com.estacionamento.estacionamento.application.port.out.TicketRepositoryPort;
import com.estacionamento.estacionamento.application.port.out.VagaRepositoryPort;
import com.estacionamento.estacionamento.domain.model.Ticket;
import com.estacionamento.estacionamento.domain.model.Vaga;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarEntradaService
        implements RegistrarEntradaUseCase {

    private final TicketRepositoryPort ticketRepository;
    private final VagaRepositoryPort vagaRepository;

    public RegistrarEntradaService(
            TicketRepositoryPort ticketRepository,
            VagaRepositoryPort vagaRepository) {

        this.ticketRepository = ticketRepository;
        this.vagaRepository = vagaRepository;
    }

    @Override
    @Transactional
    public Ticket executar(String placa, Long vagaId) {

        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException(
                    "Placa obrigatoria"
            );
        }

        if (ticketRepository.existeTicketAtivo(placa)) {
            throw new IllegalStateException(
                    "Veiculo ja esta estacionado"
            );
        }

        Vaga vaga = vagaRepository.buscarPorId(vagaId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Vaga nao encontrada"
                        )
                );

        if (vaga.isOcupada()) {
            throw new IllegalStateException(
                    "Vaga ja esta ocupada"
            );
        }

        vaga.setOcupada(true);
        vagaRepository.salvar(vaga);

        Ticket ticket = new Ticket(placa, vagaId);

        return ticketRepository.salvar(ticket);
    }
}