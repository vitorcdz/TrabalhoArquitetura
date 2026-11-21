package com.estacionamento.estacionamento.support;

import com.estacionamento.estacionamento.application.port.out.PagamentoRepositoryPort;
import com.estacionamento.estacionamento.domain.model.Pagamento;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryPagamentoRepository implements PagamentoRepositoryPort {

    private final Map<Long, Pagamento> banco = new LinkedHashMap<>();
    private long sequencia = 1;

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        if (pagamento.getId() == null) {
            pagamento.setId(sequencia++);
        }
        banco.put(pagamento.getId(), pagamento);
        return pagamento;
    }

    @Override
    public Optional<Pagamento> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public Optional<Pagamento> buscarPorTicketId(Long ticketId) {
        return banco.values().stream()
                .filter(p -> p.getTicketId().equals(ticketId))
                .findFirst();
    }

    @Override
    public List<Pagamento> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public List<Pagamento> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return banco.values().stream()
                .filter(p -> !p.getDataPagamento().isBefore(inicio)
                        && !p.getDataPagamento().isAfter(fim))
                .toList();
    }

    @Override
    public BigDecimal calcularFaturamento(LocalDateTime inicio, LocalDateTime fim) {
        return buscarPorPeriodo(inicio, fim).stream()
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
