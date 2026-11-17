package com.estacionamento.estacionamento.application.port.in;

import com.estacionamento.estacionamento.domain.model.Veiculo;
import java.util.List;

public interface ConsultarVeiculoUseCase {

    Veiculo buscarPorPlaca(String placa);

    List<Veiculo> listarTodos();
}