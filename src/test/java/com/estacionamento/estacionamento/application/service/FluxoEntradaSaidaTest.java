package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.domain.exception.TicketNaoEncontradoException;
import com.estacionamento.estacionamento.domain.exception.VagaIncompativelException;
import com.estacionamento.estacionamento.domain.exception.VagaNaoEncontradaException;
import com.estacionamento.estacionamento.domain.exception.VagaOcupadaException;
import com.estacionamento.estacionamento.domain.exception.VeiculoNaoEncontradoException;
import com.estacionamento.estacionamento.domain.model.Ticket;
import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import com.estacionamento.estacionamento.domain.model.Vaga;
import com.estacionamento.estacionamento.domain.model.Veiculo;
import com.estacionamento.estacionamento.support.InMemoryTicketRepository;
import com.estacionamento.estacionamento.support.InMemoryVagaRepository;
import com.estacionamento.estacionamento.support.InMemoryVeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Prova da arquitetura hexagonal: os casos de uso de entrada e saída
// funcionam só com adaptadores em memória, sem Spring e sem banco.
class FluxoEntradaSaidaTest {

    private InMemoryVagaRepository vagas;
    private InMemoryTicketRepository tickets;
    private InMemoryVeiculoRepository veiculos;
    private RegistrarEntradaService entrada;
    private RegistrarSaidaService saida;
    private Long vagaId;

    @BeforeEach
    void preparar() {
        vagas = new InMemoryVagaRepository();
        tickets = new InMemoryTicketRepository();
        veiculos = new InMemoryVeiculoRepository();
        entrada = new RegistrarEntradaService(tickets, vagas, veiculos);
        saida = new RegistrarSaidaService(tickets, vagas);

        vagaId = vagas.salvar(Vaga.nova(1, TipoVeiculo.CARRO)).getId();

        veiculos.salvar(new Veiculo(null, "ABC1D23", "Gol", "Preto", TipoVeiculo.CARRO));
        veiculos.salvar(new Veiculo(null, "XYZ9K88", "Onix", "Prata", TipoVeiculo.CARRO));
        veiculos.salvar(new Veiculo(null, "MOT0R11", "CG 160", "Vermelha", TipoVeiculo.MOTO));
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

        assertThrows(VagaOcupadaException.class,
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

    // ---- Placa normalizada ----

    @Test
    void placaEhNormalizadaNaEntrada() {
        Ticket ticket = entrada.executar("  abc1d23 ", vagaId);

        assertEquals("ABC1D23", ticket.getPlaca());
    }

    @Test
    void placaEmMinusculoNaoBurlaChecagemDeJaEstacionado() {
        long outraVaga = vagas.salvar(Vaga.nova(2, TipoVeiculo.CARRO)).getId();

        entrada.executar("abc1d23", vagaId);

        IllegalStateException erro = assertThrows(IllegalStateException.class,
                () -> entrada.executar("ABC1D23", outraVaga));
        assertEquals("Veiculo ja esta estacionado", erro.getMessage());
    }

    // ---- Compatibilidade veiculo x vaga ----

    @Test
    void motoNaoEntraEmVagaDeCarro() {
        assertThrows(VagaIncompativelException.class,
                () -> entrada.executar("MOT0R11", vagaId));

        assertTrue(vagas.buscarPorId(vagaId).get().estaLivre());
    }

    @Test
    void carroNaoEntraEmVagaDeMoto() {
        long vagaMoto = vagas.salvar(Vaga.nova(2, TipoVeiculo.MOTO)).getId();

        assertThrows(VagaIncompativelException.class,
                () -> entrada.executar("ABC1D23", vagaMoto));
    }

    @Test
    void motoEntraEmVagaDeMoto() {
        long vagaMoto = vagas.salvar(Vaga.nova(2, TipoVeiculo.MOTO)).getId();

        Ticket ticket = entrada.executar("MOT0R11", vagaMoto);

        assertEquals("ATIVO", ticket.getStatus());
    }

    @Test
    void veiculoNaoCadastradoNaoEntra() {
        assertThrows(VeiculoNaoEncontradoException.class,
                () -> entrada.executar("ZZZ0Z00", vagaId));
    }

    // ---- Nao encontrado (vira 404 no handler) ----

    @Test
    void entradaEmVagaInexistenteLancaVagaNaoEncontrada() {
        assertThrows(VagaNaoEncontradaException.class,
                () -> entrada.executar("ABC1D23", 99L));
    }

    @Test
    void saidaDeTicketInexistenteLancaTicketNaoEncontrado() {
        assertThrows(TicketNaoEncontradoException.class,
                () -> saida.executar(99L));
    }

    @Test
    void saidaComVagaInexistenteLancaVagaNaoEncontrada() {
        Ticket orfao = new Ticket("ABC1D23", 99L);
        tickets.salvar(orfao);

        assertThrows(VagaNaoEncontradaException.class,
                () -> saida.executar(orfao.getId()));
    }
}
