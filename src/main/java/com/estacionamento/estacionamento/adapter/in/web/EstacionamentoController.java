package com.estacionamento.estacionamento.adapter.in.web;

import com.estacionamento.estacionamento.adapter.in.web.dto.TicketResponse;
import com.estacionamento.estacionamento.application.port.in.RegistrarEntradaUseCase;
import com.estacionamento.estacionamento.application.port.in.RegistrarSaidaUseCase;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estacionamentos")
public class EstacionamentoController {

    private final RegistrarEntradaUseCase entrada;
    private final RegistrarSaidaUseCase saida;

    public EstacionamentoController(
            RegistrarEntradaUseCase entrada,
            RegistrarSaidaUseCase saida) {

        this.entrada = entrada;
        this.saida = saida;
    }

    @PostMapping("/entradas")
    public TicketResponse registrarEntrada(
            @RequestParam String placa,
            @RequestParam Long vagaId) {

        return TicketResponse.from(entrada.executar(placa, vagaId));
    }

    @PostMapping("/{ticketId}/saida")
    public TicketResponse registrarSaida(
            @PathVariable Long ticketId) {

        return TicketResponse.from(saida.executar(ticketId));
    }
}
