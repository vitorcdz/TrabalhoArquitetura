package com.estacionamento.estacionamento.application.port.out;

import com.estacionamento.estacionamento.domain.model.StatusVaga;
import com.estacionamento.estacionamento.domain.model.Vaga;
import java.util.List;
import java.util.Optional;

public interface VagaRepositoryPort {

    Vaga salvar(Vaga vaga);

    Optional<Vaga> buscarPorId(Long id);

    List<Vaga> listarTodas();

    List<Vaga> listarPorStatus(StatusVaga status);

    boolean existePorNumero(int numero);
}