package com.estacionamento.estacionamento.application.port.in;

import com.estacionamento.estacionamento.domain.model.StatusVaga;
import com.estacionamento.estacionamento.domain.model.Vaga;
import java.util.List;

public interface ConsultarVagasUseCase {

    List<Vaga> listar(StatusVaga status);

    Vaga buscarPorId(Long id);
}