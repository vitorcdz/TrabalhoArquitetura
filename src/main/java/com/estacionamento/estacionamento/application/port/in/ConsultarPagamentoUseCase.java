package com.estacionamento.estacionamento.application.port.in;

import com.estacionamento.estacionamento.domain.model.Pagamento;
import java.util.List;

public interface ConsultarPagamentoUseCase {

    List<Pagamento> listar();

    Pagamento buscarPorId(Long id);

    Pagamento buscarPorTicketId(Long ticketId);
}
