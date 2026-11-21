package com.estacionamento.estacionamento.adapter.out.persistence.repository;

import com.estacionamento.estacionamento.adapter.out.persistence.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PagamentoJpaRepository
        extends JpaRepository<PagamentoEntity, Long> {

    Optional<PagamentoEntity> findByTicketId(Long ticketId);

    List<PagamentoEntity> findByDataPagamentoBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    @Query("""
            SELECT COALESCE(SUM(p.valor), 0)
            FROM PagamentoEntity p
            WHERE p.dataPagamento BETWEEN :inicio AND :fim
            """)
    BigDecimal calcularFaturamento(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}
