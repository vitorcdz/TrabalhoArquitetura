package com.estacionamento.estacionamento.adapter.in.web;

import com.estacionamento.estacionamento.application.port.in.ConsultarFaturamentoUseCase;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final ConsultarFaturamentoUseCase consultarFaturamento;

    public RelatorioController(
            ConsultarFaturamentoUseCase consultarFaturamento) {
        this.consultarFaturamento = consultarFaturamento;
    }

    @GetMapping("/faturamento")
    public BigDecimal consultarFaturamento(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime inicio,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fim) {

        return consultarFaturamento.executar(inicio, fim);
    }
}
