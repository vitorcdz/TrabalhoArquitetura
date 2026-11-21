package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.RegistrarPagamentoUseCase;
import com.estacionamento.estacionamento.application.port.out.PagamentoRepositoryPort;
import com.estacionamento.estacionamento.application.port.out.TicketRepositoryPort;
import com.estacionamento.estacionamento.domain.model.Pagamento;
import com.estacionamento.estacionamento.domain.model.Ticket;
import com.estacionamento.estacionamento.domain.service.CalculadoraTarifa;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarPagamentoService implements RegistrarPagamentoUseCase {

    private final PagamentoRepositoryPort pagamentoRepository;
    private final TicketRepositoryPort ticketRepository;
    private final CalculadoraTarifa calculadoraTarifa;

    public RegistrarPagamentoService(
            PagamentoRepositoryPort pagamentoRepository,
            TicketRepositoryPort ticketRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.ticketRepository = ticketRepository;
        this.calculadoraTarifa = new CalculadoraTarifa();
    }

    @Override
    @Transactional
    public Pagamento executar(Long ticketId, String formaPagamento) {

        if (ticketId == null) {
            throw new IllegalArgumentException("ID do ticket nao informado");
        }

        if (formaPagamento == null || formaPagamento.isBlank()) {
            throw new IllegalArgumentException("Forma de pagamento nao informada");
        }

        Ticket ticket = ticketRepository.buscarPorId(ticketId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Ticket nao encontrado"));

        if (!"FINALIZADO".equals(ticket.getStatus())) {
            throw new IllegalStateException(
                    "O ticket precisa estar finalizado para receber o pagamento");
        }

        if (pagamentoRepository.buscarPorTicketId(ticketId).isPresent()) {
            throw new IllegalStateException(
                    "Esse ticket ja possui um pagamento registrado");
        }

        var valor = calculadoraTarifa.calcular(ticket);

        Pagamento pagamento = new Pagamento(
                ticketId,
                valor,
                formaPagamento
        );

        return pagamentoRepository.salvar(pagamento);
    }
}
