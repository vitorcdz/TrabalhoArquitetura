package com.estacionamento.estacionamento.adapter.out.persistence;

import com.estacionamento.estacionamento.adapter.out.persistence.entity.PagamentoEntity;
import com.estacionamento.estacionamento.adapter.out.persistence.repository.PagamentoJpaRepository;
import com.estacionamento.estacionamento.application.port.out.PagamentoRepositoryPort;
import com.estacionamento.estacionamento.domain.model.Pagamento;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class PagamentoRepositoryAdapter
        implements PagamentoRepositoryPort {

    private final PagamentoJpaRepository repository;

    public PagamentoRepositoryAdapter(
            PagamentoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {

        PagamentoEntity entity = converter(pagamento);

        PagamentoEntity salvo = repository.save(entity);

        return converter(salvo);
    }

    @Override
    public Optional<Pagamento> buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::converter);
    }

    @Override
    public List<Pagamento> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    @Override
    public Optional<Pagamento> buscarPorTicketId(Long ticketId) {
        return repository.findByTicketId(ticketId)
                .map(this::converter);
    }

    @Override
    public List<Pagamento> buscarPorPeriodo(
            LocalDateTime inicio,
            LocalDateTime fim) {

        return repository.findByDataPagamentoBetween(inicio, fim)
                .stream()
                .map(this::converter)
                .toList();
    }

    @Override
    public BigDecimal calcularFaturamento(
            LocalDateTime inicio,
            LocalDateTime fim) {

        return repository.calcularFaturamento(inicio, fim);
    }

    private PagamentoEntity converter(Pagamento pagamento) {

        PagamentoEntity entity = new PagamentoEntity();

        entity.setId(pagamento.getId());
        entity.setTicketId(pagamento.getTicketId());
        entity.setValor(pagamento.getValor());
        entity.setDataPagamento(pagamento.getDataPagamento());
        entity.setFormaPagamento(pagamento.getFormaPagamento());

        return entity;
    }

    private Pagamento converter(PagamentoEntity entity) {

        Pagamento pagamento = new Pagamento();

        pagamento.setId(entity.getId());
        pagamento.setTicketId(entity.getTicketId());
        pagamento.setValor(entity.getValor());
        pagamento.setDataPagamento(entity.getDataPagamento());
        pagamento.setFormaPagamento(entity.getFormaPagamento());

        return pagamento;
    }
}
