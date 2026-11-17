package com.estacionamento.estacionamento.adapter.in.web;

import com.estacionamento.estacionamento.adapter.in.web.dto.VeiculoRequest;
import com.estacionamento.estacionamento.adapter.in.web.dto.VeiculoResponse;
import com.estacionamento.estacionamento.application.port.in.CadastrarVeiculoUseCase;
import com.estacionamento.estacionamento.application.port.in.ConsultarVeiculoUseCase;
import com.estacionamento.estacionamento.domain.model.Veiculo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final CadastrarVeiculoUseCase cadastrarUseCase;
    private final ConsultarVeiculoUseCase consultarUseCase;

    public VeiculoController(
            CadastrarVeiculoUseCase cadastrarUseCase,
            ConsultarVeiculoUseCase consultarUseCase) {
        this.cadastrarUseCase = cadastrarUseCase;
        this.consultarUseCase = consultarUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoResponse cadastrar(
            @Valid @RequestBody VeiculoRequest request) {

        Veiculo veiculo = new Veiculo(
                null,
                request.placa(),
                request.modelo(),
                request.cor(),
                request.tipo()
        );

        return VeiculoResponse.from(
                cadastrarUseCase.executar(veiculo)
        );
    }

    @GetMapping("/{placa}")
    public VeiculoResponse buscarPorPlaca(
            @PathVariable String placa) {

        return VeiculoResponse.from(
                consultarUseCase.buscarPorPlaca(placa)
        );
    }

    @GetMapping
    public List<VeiculoResponse> listarTodos() {

        return consultarUseCase.listarTodos()
                .stream()
                .map(VeiculoResponse::from)
                .toList();
    }
}
