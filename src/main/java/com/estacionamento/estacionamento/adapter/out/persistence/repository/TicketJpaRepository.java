package com.estacionamento.estacionamento.adapter.out.persistence.repository;

import com.estacionamento.estacionamento.adapter.out.persistence.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketJpaRepository
        extends JpaRepository<TicketEntity, Long> {

    boolean existsByPlacaAndStatus(
            String placa, String status);

    List<TicketEntity> findByStatus(String status);
}