package com.estacionamento.estacionamento.application.port.in;

import com.estacionamento.estacionamento.domain.model.FormaPagamento;
import com.estacionamento.estacionamento.domain.model.Pagamento;

public interface RegistrarPagamentoUseCase {

    Pagamento executar(Long ticketId, FormaPagamento formaPagamento);

}
