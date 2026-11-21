package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.RegistrarEntradaUseCase;
import com.estacionamento.estacionamento.application.port.out.TicketRepositoryPort;
import com.estacionamento.estacionamento.application.port.out.VagaRepositoryPort;
import com.estacionamento.estacionamento.application.port.out.VeiculoRepositoryPort;
import com.estacionamento.estacionamento.domain.exception.VagaIncompativelException;
import com.estacionamento.estacionamento.domain.exception.VagaNaoEncontradaException;
import com.estacionamento.estacionamento.domain.exception.VeiculoNaoEncontradoException;
import com.estacionamento.estacionamento.domain.model.Ticket;
import com.estacionamento.estacionamento.domain.model.Vaga;
import com.estacionamento.estacionamento.domain.model.Veiculo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarEntradaService
        implements RegistrarEntradaUseCase {

    private final TicketRepositoryPort ticketRepository;
    private final VagaRepositoryPort vagaRepository;
    private final VeiculoRepositoryPort veiculoRepository;

    public RegistrarEntradaService(
            TicketRepositoryPort ticketRepository,
            VagaRepositoryPort vagaRepository,
            VeiculoRepositoryPort veiculoRepository) {

        this.ticketRepository = ticketRepository;
        this.vagaRepository = vagaRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    @Transactional
    public Ticket executar(String placa, Long vagaId) {

        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException(
                    "Placa obrigatoria"
            );
        }

        // Mesma normalizacao usada no cadastro do veiculo.
        String placaNormalizada = placa.toUpperCase().trim();

        // O tipo do veiculo vem do cadastro, nao do cliente da API.
        Veiculo veiculo = veiculoRepository.buscarPorPlaca(placaNormalizada)
                .orElseThrow(() ->
                        new VeiculoNaoEncontradoException(placaNormalizada)
                );

        if (ticketRepository.existeTicketAtivo(placaNormalizada)) {
            throw new IllegalStateException(
                    "Veiculo ja esta estacionado"
            );
        }

        Vaga vaga = vagaRepository.buscarPorId(vagaId)
                .orElseThrow(() ->
                        new VagaNaoEncontradaException(vagaId)
                );

        if (!vaga.aceita(veiculo.getTipo())) {
            throw new VagaIncompativelException(
                    vaga.getNumero(),
                    vaga.getTipo(),
                    veiculo.getTipo()
            );
        }

        // Lanca VagaOcupadaException se a vaga ja estiver ocupada.
        vaga.ocupar();
        vagaRepository.salvar(vaga);

        Ticket ticket = new Ticket(placaNormalizada, vagaId);

        return ticketRepository.salvar(ticket);
    }
}
