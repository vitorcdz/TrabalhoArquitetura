package com.estacionamento.estacionamento.adapter.out.persistence.repository;

import com.estacionamento.estacionamento.adapter.out.persistence.entity.VagaEntity;
import com.estacionamento.estacionamento.domain.model.StatusVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VagaJpaRepository
        extends JpaRepository<VagaEntity, Long> {

    List<VagaEntity> findByStatus(StatusVaga status);

    boolean existsByNumero(int numero);
}