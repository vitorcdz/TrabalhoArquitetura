package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.CadastrarVagaUseCase;
import com.estacionamento.estacionamento.application.port.out.VagaRepositoryPort;
import com.estacionamento.estacionamento.domain.exception.VagaJaCadastradaException;
import com.estacionamento.estacionamento.domain.model.Vaga;
import org.springframework.stereotype.Service;

@Service
public class CadastrarVagaService implements CadastrarVagaUseCase {

    private final VagaRepositoryPort repository;

    public CadastrarVagaService(VagaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Vaga executar(Vaga vaga) {

        if (repository.existePorNumero(vaga.getNumero())) {
            throw new VagaJaCadastradaException(vaga.getNumero());
        }

        return repository.salvar(vaga);
    }
}