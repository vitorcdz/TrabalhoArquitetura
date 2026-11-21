package com.estacionamento.estacionamento.adapter.in.web;

import com.estacionamento.estacionamento.domain.exception.PagamentoNaoEncontradoException;
import com.estacionamento.estacionamento.domain.exception.TicketNaoEncontradoException;
import com.estacionamento.estacionamento.domain.exception.VagaIncompativelException;
import com.estacionamento.estacionamento.domain.exception.VagaNaoEncontradaException;
import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void naoEncontradoViraNotFound() {
        assertEquals(HttpStatus.NOT_FOUND,
                handler.naoEncontrado(new VagaNaoEncontradaException(1L)).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND,
                handler.naoEncontrado(new TicketNaoEncontradoException(1L)).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND,
                handler.naoEncontrado(PagamentoNaoEncontradoException.porId(1L)).getStatusCode());
    }

    @Test
    void incompatibilidadeViraConflict() {
        var erro = new VagaIncompativelException(1, TipoVeiculo.CARRO, TipoVeiculo.MOTO);

        assertEquals(HttpStatus.CONFLICT,
                handler.conflitoDeEstado(erro).getStatusCode());
    }

    @Test
    void argumentoInvalidoContinuaBadRequest() {
        assertEquals(HttpStatus.BAD_REQUEST,
                handler.dadosInvalidos(new IllegalArgumentException("x")).getStatusCode());
    }
}
