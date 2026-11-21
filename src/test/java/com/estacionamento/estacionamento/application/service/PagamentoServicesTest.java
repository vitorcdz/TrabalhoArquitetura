package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.domain.exception.PagamentoNaoEncontradoException;
import com.estacionamento.estacionamento.domain.exception.TicketNaoEncontradoException;
import com.estacionamento.estacionamento.domain.model.FormaPagamento;
import com.estacionamento.estacionamento.domain.model.Pagamento;
import com.estacionamento.estacionamento.domain.model.Ticket;
import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import com.estacionamento.estacionamento.domain.model.Vaga;
import com.estacionamento.estacionamento.domain.model.Veiculo;
import com.estacionamento.estacionamento.support.InMemoryPagamentoRepository;
import com.estacionamento.estacionamento.support.InMemoryTicketRepository;
import com.estacionamento.estacionamento.support.InMemoryVagaRepository;
import com.estacionamento.estacionamento.support.InMemoryVeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoServicesTest {

    private InMemoryTicketRepository tickets;
    private InMemoryPagamentoRepository pagamentos;
    private RegistrarEntradaService entrada;
    private RegistrarSaidaService saida;
    private RegistrarPagamentoService pagar;
    private ConsultarPagamentoService consultarPagamentos;
    private ConsultarTicketService consultarTickets;
    private ConsultarFaturamentoService faturamento;
    private Long vagaId;

    @BeforeEach
    void preparar() {
        var vagas = new InMemoryVagaRepository();
        var veiculos = new InMemoryVeiculoRepository();
        tickets = new InMemoryTicketRepository();
        pagamentos = new InMemoryPagamentoRepository();

        entrada = new RegistrarEntradaService(tickets, vagas, veiculos);
        saida = new RegistrarSaidaService(tickets, vagas);
        pagar = new RegistrarPagamentoService(pagamentos, tickets);
        consultarPagamentos = new ConsultarPagamentoService(pagamentos);
        consultarTickets = new ConsultarTicketService(tickets);
        faturamento = new ConsultarFaturamentoService(pagamentos);

        vagaId = vagas.salvar(Vaga.nova(1, TipoVeiculo.CARRO)).getId();
        veiculos.salvar(new Veiculo(null, "ABC1D23", "Gol", "Preto", TipoVeiculo.CARRO));
    }

    @Test
    void pagamentoDeTicketFinalizadoGeraFaturamento() {
        Ticket ticket = entrada.executar("ABC1D23", vagaId);
        saida.executar(ticket.getId());

        Pagamento pagamento = pagar.executar(ticket.getId(), FormaPagamento.PIX);

        assertNotNull(pagamento.getId());
        assertEquals(new BigDecimal("10.00"), pagamento.getValor());

        BigDecimal total = faturamento.executar(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        assertEquals(new BigDecimal("10.00"), total);
    }

    @Test
    void naoPagaTicketAindaAtivo() {
        Ticket ticket = entrada.executar("ABC1D23", vagaId);

        assertThrows(IllegalStateException.class,
                () -> pagar.executar(ticket.getId(), FormaPagamento.PIX));
    }

    @Test
    void naoPagaDuasVezesOMesmoTicket() {
        Ticket ticket = entrada.executar("ABC1D23", vagaId);
        saida.executar(ticket.getId());
        pagar.executar(ticket.getId(), FormaPagamento.PIX);

        assertThrows(IllegalStateException.class,
                () -> pagar.executar(ticket.getId(), FormaPagamento.DINHEIRO));
    }

    @Test
    void formaDePagamentoObrigatoria() {
        Ticket ticket = entrada.executar("ABC1D23", vagaId);
        saida.executar(ticket.getId());

        assertThrows(IllegalArgumentException.class,
                () -> pagar.executar(ticket.getId(), null));
    }

    @Test
    void pagamentoDeTicketInexistenteLancaTicketNaoEncontrado() {
        assertThrows(TicketNaoEncontradoException.class,
                () -> pagar.executar(99L, FormaPagamento.PIX));
    }

    @Test
    void consultaPagamentosPorIdEPorTicket() {
        Ticket ticket = entrada.executar("ABC1D23", vagaId);
        saida.executar(ticket.getId());
        Pagamento pago = pagar.executar(ticket.getId(), FormaPagamento.CARTAO_CREDITO);

        assertEquals(1, consultarPagamentos.listar().size());
        assertEquals(FormaPagamento.CARTAO_CREDITO, consultarPagamentos.buscarPorId(pago.getId()).getFormaPagamento());
        assertEquals(pago.getId(), consultarPagamentos.buscarPorTicketId(ticket.getId()).getId());
    }

    @Test
    void consultaPagamentoInexistenteLancaNaoEncontrado() {
        assertThrows(PagamentoNaoEncontradoException.class,
                () -> consultarPagamentos.buscarPorId(99L));
        assertThrows(PagamentoNaoEncontradoException.class,
                () -> consultarPagamentos.buscarPorTicketId(99L));
    }

    @Test
    void consultaTicketsListaEFiltraPorStatus() {
        Ticket ticket = entrada.executar("ABC1D23", vagaId);

        assertEquals(1, consultarTickets.listar(null).size());
        assertEquals(1, consultarTickets.listar("ativo").size());
        assertEquals(0, consultarTickets.listar("FINALIZADO").size());

        saida.executar(ticket.getId());

        assertEquals(0, consultarTickets.listar("ATIVO").size());
        assertEquals(1, consultarTickets.listar("FINALIZADO").size());
        assertEquals(ticket.getId(), consultarTickets.buscarPorId(ticket.getId()).getId());
    }

    @Test
    void statusDeTicketInvalidoLancaIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> consultarTickets.listar("QUALQUER"));
    }

    @Test
    void consultaTicketInexistenteLancaNaoEncontrado() {
        assertThrows(TicketNaoEncontradoException.class,
                () -> consultarTickets.buscarPorId(99L));
    }
}
