package com.estacionamento.estacionamento.adapter.in.web;

import com.estacionamento.estacionamento.adapter.in.web.dto.TicketResponse;
import com.estacionamento.estacionamento.application.port.in.ConsultarTicketUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final ConsultarTicketUseCase consultarUseCase;

    public TicketController(ConsultarTicketUseCase consultarUseCase) {
        this.consultarUseCase = consultarUseCase;
    }

    @GetMapping
    public List<TicketResponse> listar(
            @RequestParam(required = false) String status) {

        return consultarUseCase.listar(status)
                .stream()
                .map(TicketResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TicketResponse buscarPorId(@PathVariable Long id) {

        return TicketResponse.from(consultarUseCase.buscarPorId(id));
    }
}
