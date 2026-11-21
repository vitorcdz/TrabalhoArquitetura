package com.estacionamento.estacionamento.support;

import com.estacionamento.estacionamento.application.port.out.VeiculoRepositoryPort;
import com.estacionamento.estacionamento.domain.model.Veiculo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryVeiculoRepository implements VeiculoRepositoryPort {

    private final Map<String, Veiculo> banco = new LinkedHashMap<>();
    private long sequencia = 1;

    @Override
    public Veiculo salvar(Veiculo veiculo) {
        Long id = veiculo.getId() != null ? veiculo.getId() : sequencia++;
        Veiculo salvo = new Veiculo(id, veiculo.getPlaca(), veiculo.getModelo(),
                veiculo.getCor(), veiculo.getTipo());
        banco.put(salvo.getPlaca(), salvo);
        return salvo;
    }

    @Override
    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return Optional.ofNullable(banco.get(placa));
    }

    @Override
    public List<Veiculo> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public boolean existePorPlaca(String placa) {
        return banco.containsKey(placa);
    }
}
