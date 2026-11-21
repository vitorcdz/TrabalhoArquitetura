package com.estacionamento.estacionamento.application.port.in;

import com.estacionamento.estacionamento.domain.model.Ticket;
import java.util.List;

public interface ConsultarTicketUseCase {

    // status: "ATIVO" ou "FINALIZADO"; null lista todos.
    List<Ticket> listar(String status);

    Ticket buscarPorId(Long id);
}
