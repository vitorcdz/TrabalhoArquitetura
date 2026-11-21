package com.estacionamento.estacionamento.adapter.in.web.dto;

import com.estacionamento.estacionamento.domain.model.FormaPagamento;
import com.estacionamento.estacionamento.domain.model.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoResponse(
        Long id,
        Long ticketId,
        BigDecimal valor,
        LocalDateTime dataPagamento,
        FormaPagamento formaPagamento
) {

    public static PagamentoResponse from(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(),
                pagamento.getTicketId(),
                pagamento.getValor(),
                pagamento.getDataPagamento(),
                pagamento.getFormaPagamento()
        );
    }
}
