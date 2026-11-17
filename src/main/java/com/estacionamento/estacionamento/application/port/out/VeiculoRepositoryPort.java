package com.estacionamento.estacionamento.application.port.out;

import com.estacionamento.estacionamento.domain.model.Veiculo;
import java.util.List;
import java.util.Optional;

public interface VeiculoRepositoryPort {

    Veiculo salvar(Veiculo veiculo);

    Optional<Veiculo> buscarPorPlaca(String placa);

    List<Veiculo> listarTodos();

    boolean existePorPlaca(String placa);
}