package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.application.port.in.ConsultarFaturamentoUseCase;
import com.estacionamento.estacionamento.application.port.out.PagamentoRepositoryPort;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ConsultarFaturamentoService
        implements ConsultarFaturamentoUseCase {

    private final PagamentoRepositoryPort pagamentoRepository;

    public ConsultarFaturamentoService(
            PagamentoRepositoryPort pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public BigDecimal executar(
            LocalDateTime inicio,
            LocalDateTime fim) {

        if (inicio == null || fim == null) {
            throw new IllegalArgumentException(
                    "Periodo nao informado"
            );
        }

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException(
                    "Data inicial nao pode ser maior que a data final"
            );
        }

        return pagamentoRepository.calcularFaturamento(
                inicio,
                fim
        );
    }
}
