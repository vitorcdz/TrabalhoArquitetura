package com.estacionamento.estacionamento.adapter.in.web.dto;

import com.estacionamento.estacionamento.domain.model.FormaPagamento;
import jakarta.validation.constraints.NotNull;

public record PagamentoRequest(

        @NotNull(message = "O ticket é obrigatório")
        Long ticketId,

        @NotNull(message = "A forma de pagamento é obrigatória")
        FormaPagamento formaPagamento

) {}
