package com.estacionamento.estacionamento.application.port.in;

import com.estacionamento.estacionamento.domain.model.Veiculo;

public interface CadastrarVeiculoUseCase {

    Veiculo executar(Veiculo veiculo);
}