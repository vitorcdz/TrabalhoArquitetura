package com.estacionamento.estacionamento.adapter.in.web;

import com.estacionamento.estacionamento.adapter.in.web.dto.PagamentoRequest;
import com.estacionamento.estacionamento.adapter.in.web.dto.PagamentoResponse;
import com.estacionamento.estacionamento.application.port.in.ConsultarPagamentoUseCase;
import com.estacionamento.estacionamento.application.port.in.RegistrarPagamentoUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final RegistrarPagamentoUseCase registrarUseCase;
    private final ConsultarPagamentoUseCase consultarUseCase;

    public PagamentoController(
            RegistrarPagamentoUseCase registrarUseCase,
            ConsultarPagamentoUseCase consultarUseCase) {
        this.registrarUseCase = registrarUseCase;
        this.consultarUseCase = consultarUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoResponse registrar(
            @Valid @RequestBody PagamentoRequest request) {

        return PagamentoResponse.from(
                registrarUseCase.executar(
                        request.ticketId(),
                        request.formaPagamento()
                )
        );
    }

    @GetMapping
    public List<PagamentoResponse> listar() {

        return consultarUseCase.listar()
                .stream()
                .map(PagamentoResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public PagamentoResponse buscarPorId(@PathVariable Long id) {

        return PagamentoResponse.from(consultarUseCase.buscarPorId(id));
    }

    @GetMapping("/ticket/{ticketId}")
    public PagamentoResponse buscarPorTicket(@PathVariable Long ticketId) {

        return PagamentoResponse.from(
                consultarUseCase.buscarPorTicketId(ticketId)
        );
    }
}
