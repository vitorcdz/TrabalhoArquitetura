
package com.estacionamento.estacionamento.adapter.out.persistence.repository;

import com.estacionamento.estacionamento.adapter.out.persistence.entity.VeiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VeiculoJpaRepository
        extends JpaRepository<VeiculoEntity, Long> {

    Optional<VeiculoEntity> findByPlaca(String placa);

    boolean existsByPlaca(String placa);
}
