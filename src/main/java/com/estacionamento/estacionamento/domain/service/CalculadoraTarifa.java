
package com.estacionamento.estacionamento.domain.service;

import com.estacionamento.estacionamento.domain.model.Ticket;

import java.math.BigDecimal;
import java.time.Duration;

public class CalculadoraTarifa {

    private static final BigDecimal VALOR_PRIMEIRA_HORA =
            new BigDecimal("10.00");

    private static final BigDecimal VALOR_HORA_ADICIONAL =
            new BigDecimal("5.00");

    public BigDecimal calcular(Ticket ticket) {

        if (ticket == null) {
            throw new IllegalArgumentException("Ticket nao informado");
        }

        if (ticket.getEntrada() == null || ticket.getSaida() == null) {
            throw new IllegalArgumentException(
                    "Ticket precisa ter entrada e saida registradas"
            );
        }

        Duration duracao = Duration.between(
                ticket.getEntrada(),
                ticket.getSaida()
        );

        if (duracao.isNegative()) {
            throw new IllegalArgumentException(
                    "Horario de saida invalido"
            );
        }

        long horasCobradas = duracao.toHours();

        if (!duracao.minusHours(horasCobradas).isZero()) {
            horasCobradas++;
        }

        horasCobradas = Math.max(1, horasCobradas);

        return VALOR_PRIMEIRA_HORA.add(
                VALOR_HORA_ADICIONAL.multiply(
                        BigDecimal.valueOf(horasCobradas - 1)
                )
        );
    }
}
