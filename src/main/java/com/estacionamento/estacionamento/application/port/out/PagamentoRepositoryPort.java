package com.estacionamento.estacionamento.application.port.out;

import com.estacionamento.estacionamento.domain.model.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PagamentoRepositoryPort {

    Pagamento salvar(Pagamento pagamento);

    Optional<Pagamento> buscarPorTicketId(Long ticketId);

    List<Pagamento> buscarPorPeriodo(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    BigDecimal calcularFaturamento(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
