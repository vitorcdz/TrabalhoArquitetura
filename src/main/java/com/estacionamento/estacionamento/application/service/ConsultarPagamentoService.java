package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.ConsultarPagamentoUseCase;
import com.estacionamento.estacionamento.application.port.out.PagamentoRepositoryPort;
import com.estacionamento.estacionamento.domain.exception.PagamentoNaoEncontradoException;
import com.estacionamento.estacionamento.domain.model.Pagamento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarPagamentoService implements ConsultarPagamentoUseCase {

    private final PagamentoRepositoryPort repository;

    public ConsultarPagamentoService(PagamentoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Pagamento> listar() {
        return repository.listarTodos();
    }

    @Override
    public Pagamento buscarPorId(Long id) {

        return repository.buscarPorId(id)
                .orElseThrow(() -> PagamentoNaoEncontradoException.porId(id));
    }

    @Override
    public Pagamento buscarPorTicketId(Long ticketId) {

        return repository.buscarPorTicketId(ticketId)
                .orElseThrow(() -> PagamentoNaoEncontradoException.porTicket(ticketId));
    }
}
