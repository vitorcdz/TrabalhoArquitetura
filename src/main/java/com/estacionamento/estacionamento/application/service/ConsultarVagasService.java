package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.ConsultarVagasUseCase;
import com.estacionamento.estacionamento.application.port.out.VagaRepositoryPort;
import com.estacionamento.estacionamento.domain.exception.VagaNaoEncontradaException;
import com.estacionamento.estacionamento.domain.model.StatusVaga;
import com.estacionamento.estacionamento.domain.model.Vaga;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConsultarVagasService implements ConsultarVagasUseCase {

    private final VagaRepositoryPort repository;

    public ConsultarVagasService(VagaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Vaga> listar(StatusVaga status) {

        if (status == null) {
            return repository.listarTodas();
        }

        return repository.listarPorStatus(status);
    }

    @Override
    public Vaga buscarPorId(Long id) {

        return repository.buscarPorId(id)
                .orElseThrow(() -> new VagaNaoEncontradaException(id));
    }
}