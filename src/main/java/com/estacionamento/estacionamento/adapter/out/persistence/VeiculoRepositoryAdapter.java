package com.estacionamento.estacionamento.adapter.out.persistence;

import com.estacionamento.estacionamento.adapter.out.persistence.mapper.VeiculoMapper;
import com.estacionamento.estacionamento.adapter.out.persistence.repository.VeiculoJpaRepository;
import com.estacionamento.estacionamento.application.port.out.VeiculoRepositoryPort;
import com.estacionamento.estacionamento.domain.model.Veiculo;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class VeiculoRepositoryAdapter implements VeiculoRepositoryPort {

    private final VeiculoJpaRepository jpaRepository;

    public VeiculoRepositoryAdapter(VeiculoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Veiculo salvar(Veiculo veiculo) {

        var entity = VeiculoMapper.toEntity(veiculo);

        var salvo = jpaRepository.save(entity);

        return VeiculoMapper.toDomain(salvo);
    }

    @Override
    public Optional<Veiculo> buscarPorPlaca(String placa) {

        return jpaRepository.findByPlaca(placa)
                .map(VeiculoMapper::toDomain);
    }

    @Override
    public List<Veiculo> listarTodos() {

        return jpaRepository.findAll()
                .stream()
                .map(VeiculoMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existePorPlaca(String placa) {

        return jpaRepository.existsByPlaca(placa);
    }
}
