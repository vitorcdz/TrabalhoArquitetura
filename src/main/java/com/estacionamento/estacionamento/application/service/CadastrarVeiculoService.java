package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.CadastrarVeiculoUseCase;
import com.estacionamento.estacionamento.application.port.out.VeiculoRepositoryPort;
import com.estacionamento.estacionamento.domain.exception.VeiculoJaCadastradoException;
import com.estacionamento.estacionamento.domain.model.Veiculo;
import org.springframework.stereotype.Service;

@Service
public class CadastrarVeiculoService implements CadastrarVeiculoUseCase {

    private final VeiculoRepositoryPort repository;

    public CadastrarVeiculoService(VeiculoRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Veiculo executar(Veiculo veiculo) {

        if (repository.existePorPlaca(veiculo.getPlaca())) {
            throw new VeiculoJaCadastradoException(veiculo.getPlaca());
        }

        return repository.salvar(veiculo);
    }
}
