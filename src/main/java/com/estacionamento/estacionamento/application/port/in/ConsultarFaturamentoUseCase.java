package com.estacionamento.estacionamento.application.port.in;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ConsultarFaturamentoUseCase {

    BigDecimal executar(
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
