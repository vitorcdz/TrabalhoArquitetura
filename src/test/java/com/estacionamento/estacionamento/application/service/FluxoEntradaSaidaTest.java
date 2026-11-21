package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.domain.model.Ticket;
import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import com.estacionamento.estacionamento.domain.model.Vaga;
import com.estacionamento.estacionamento.support.InMemoryTicketRepository;
import com.estacionamento.estacionamento.support.InMemoryVagaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Prova da arquitetura hexagonal: os casos de uso de entrada e saída
// funcionam só com adaptadores em memória, sem Spring e sem banco.
class FluxoEntradaSaidaTest {

    private InMemoryVagaRepository vagas;
    private InMemoryTicketRepository tickets;
    private RegistrarEntradaService entrada;
    private RegistrarSaidaService saida;
    private Long vagaId;

    @BeforeEach
    void preparar() {
        vagas = new InMemoryVagaRepository();
        tickets = new InMemoryTicketRepository();
        entrada = new RegistrarEntradaService(tickets, vagas);
        saida = new RegistrarSaidaService(tickets, vagas);

        vagaId = vagas.salvar(Vaga.nova(1, TipoVeiculo.CARRO)).getId();
    }

    @Test
    void entradaOcupaAVaga() {
        Ticket ticket = entrada.executar("ABC1D23", vagaId);

        assertEquals("ATIVO", ticket.getStatus());
        assertTrue(vagas.buscarPorId(vagaId).get().isOcupada());
    }

    @Test
    void naoPermiteDoisVeiculosNaMesmaVaga() {
        entrada.executar("ABC1D23", vagaId);

        assertThrows(IllegalStateException.class,
                () -> entrada.executar("XYZ9K88", vagaId));
    }

    @Test
    void saidaLiberaAVagaEFinalizaOTicket() {
        Ticket ticket = entrada.executar("ABC1D23", vagaId);

        Ticket finalizado = saida.executar(ticket.getId());

        assertEquals("FINALIZADO", finalizado.getStatus());
        assertTrue(vagas.buscarPorId(vagaId).get().estaLivre());
    }

    @Test
    void naoPermiteSaidaDuasVezes() {
        Ticket ticket = entrada.executar("ABC1D23", vagaId);
        saida.executar(ticket.getId());

        assertThrows(IllegalStateException.class,
                () -> saida.executar(ticket.getId()));
    }

    @Test
    void vagaLiberadaPodeSerUsadaDeNovo() {
        Ticket primeiro = entrada.executar("ABC1D23", vagaId);
        saida.executar(primeiro.getId());

        Ticket segundo = entrada.executar("XYZ9K88", vagaId);

        assertEquals("ATIVO", segundo.getStatus());
    }
}