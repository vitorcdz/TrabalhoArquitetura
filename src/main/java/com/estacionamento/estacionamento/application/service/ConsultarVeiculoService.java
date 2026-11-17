
package com.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.ConsultarVeiculoUseCase;
import com.estacionamento.estacionamento.application.port.out.VeiculoRepositoryPort;
import com.estacionamento.estacionamento.domain.exception.VeiculoNaoEncontradoException;
import com.estacionamento.estacionamento.domain.model.Veiculo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConsultarVeiculoService implements ConsultarVeiculoUseCase {

    private final VeiculoRepositoryPort repository;

    public ConsultarVeiculoService(VeiculoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) {

        return repository.buscarPorPlaca(placa.toUpperCase().trim())
                .orElseThrow(() ->
                        new VeiculoNaoEncontradoException(placa));
    }

    @Override
    public List<Veiculo> listarTodos() {
        return repository.listarTodos();
    }
}
