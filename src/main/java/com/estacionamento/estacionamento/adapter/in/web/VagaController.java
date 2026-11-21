package com.estacionamento.estacionamento.adapter.in.web;

import com.estacionamento.estacionamento.adapter.in.web.dto.VagaRequest;
import com.estacionamento.estacionamento.adapter.in.web.dto.VagaResponse;
import com.estacionamento.estacionamento.application.port.in.CadastrarVagaUseCase;
import com.estacionamento.estacionamento.application.port.in.ConsultarVagasUseCase;
import com.estacionamento.estacionamento.domain.model.StatusVaga;
import com.estacionamento.estacionamento.domain.model.Vaga;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    private final CadastrarVagaUseCase cadastrarUseCase;
    private final ConsultarVagasUseCase consultarUseCase;

    public VagaController(
            CadastrarVagaUseCase cadastrarUseCase,
            ConsultarVagasUseCase consultarUseCase) {
        this.cadastrarUseCase = cadastrarUseCase;
        this.consultarUseCase = consultarUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VagaResponse cadastrar(
            @Valid @RequestBody VagaRequest request) {

        Vaga vaga = Vaga.nova(request.numero(), request.tipo());

        return VagaResponse.from(
                cadastrarUseCase.executar(vaga)
        );
    }

    @GetMapping
    public List<VagaResponse> listar(
            @RequestParam(required = false) StatusVaga status) {

        return consultarUseCase.listar(status)
                .stream()
                .map(VagaResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public VagaResponse buscarPorId(
            @PathVariable Long id) {

        return VagaResponse.from(
                consultarUseCase.buscarPorId(id)
        );
    }
}