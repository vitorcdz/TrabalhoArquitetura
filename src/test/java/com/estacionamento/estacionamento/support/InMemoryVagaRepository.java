package com.estacionamento.estacionamento.support;

import com.estacionamento.estacionamento.application.port.out.VagaRepositoryPort;
import com.estacionamento.estacionamento.domain.model.StatusVaga;
import com.estacionamento.estacionamento.domain.model.Vaga;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryVagaRepository implements VagaRepositoryPort {

    private final Map<Long, Vaga> banco = new LinkedHashMap<>();
    private long sequencia = 1;

    @Override
    public Vaga salvar(Vaga vaga) {
        Long id = vaga.getId() != null ? vaga.getId() : sequencia++;
        Vaga salva = new Vaga(id, vaga.getNumero(), vaga.getTipo(), vaga.getStatus());
        banco.put(id, salva);
        return salva;
    }

    @Override
    public Optional<Vaga> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<Vaga> listarTodas() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public List<Vaga> listarPorStatus(StatusVaga status) {
        return banco.values().stream()
                .filter(v -> v.getStatus() == status)
                .toList();
    }

    @Override
    public boolean existePorNumero(int numero) {
        return banco.values().stream()
                .anyMatch(v -> v.getNumero() == numero);
    }
}