package com.estacionamento.estacionamento.application.port.in;

import com.estacionamento.estacionamento.domain.model.Vaga;

public interface CadastrarVagaUseCase {

    Vaga executar(Vaga vaga);
}