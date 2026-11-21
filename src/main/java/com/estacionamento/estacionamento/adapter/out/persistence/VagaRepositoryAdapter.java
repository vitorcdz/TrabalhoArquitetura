package com.estacionamento.estacionamento.adapter.out.persistence;

import com.estacionamento.estacionamento.adapter.out.persistence.mapper.VagaMapper;
import com.estacionamento.estacionamento.adapter.out.persistence.repository.VagaJpaRepository;
import com.estacionamento.estacionamento.application.port.out.VagaRepositoryPort;
import com.estacionamento.estacionamento.domain.model.StatusVaga;
import com.estacionamento.estacionamento.domain.model.Vaga;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class VagaRepositoryAdapter implements VagaRepositoryPort {

    private final VagaJpaRepository jpaRepository;

    public VagaRepositoryAdapter(VagaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Vaga salvar(Vaga vaga) {

        var entity = VagaMapper.toEntity(vaga);

        var salvo = jpaRepository.save(entity);

        return VagaMapper.toDomain(salvo);
    }

    @Override
    public Optional<Vaga> buscarPorId(Long id) {

        return jpaRepository.findById(id)
                .map(VagaMapper::toDomain);
    }

    @Override
    public List<Vaga> listarTodas() {

        return jpaRepository.findAll()
                .stream()
                .map(VagaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Vaga> listarPorStatus(StatusVaga status) {

        return jpaRepository.findByStatus(status)
                .stream()
                .map(VagaMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existePorNumero(int numero) {

        return jpaRepository.existsByNumero(numero);
    }
}