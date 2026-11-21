package com.estacionamento.estacionamento.application.service;

import com.estacionamento.estacionamento.domain.exception.VagaJaCadastradaException;
import com.estacionamento.estacionamento.domain.exception.VagaNaoEncontradaException;
import com.estacionamento.estacionamento.domain.model.StatusVaga;
import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import com.estacionamento.estacionamento.domain.model.Vaga;
import com.estacionamento.estacionamento.support.InMemoryVagaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VagaServicesTest {

    private InMemoryVagaRepository repository;
    private CadastrarVagaService cadastrar;
    private ConsultarVagasService consultar;

    @BeforeEach
    void preparar() {
        repository = new InMemoryVagaRepository();
        cadastrar = new CadastrarVagaService(repository);
        consultar = new ConsultarVagasService(repository);
    }

    @Test
    void cadastraEListaAsVagas() {
        cadastrar.executar(Vaga.nova(1, TipoVeiculo.CARRO));
        cadastrar.executar(Vaga.nova(2, TipoVeiculo.MOTO));

        assertEquals(2, consultar.listar(null).size());
    }

    @Test
    void naoCadastraNumeroDuplicado() {
        cadastrar.executar(Vaga.nova(1, TipoVeiculo.CARRO));

        assertThrows(VagaJaCadastradaException.class,
                () -> cadastrar.executar(Vaga.nova(1, TipoVeiculo.MOTO)));
    }

    @Test
    void filtraPorStatus() {
        Vaga primeira = cadastrar.executar(Vaga.nova(1, TipoVeiculo.CARRO));
        cadastrar.executar(Vaga.nova(2, TipoVeiculo.CARRO));

        primeira.ocupar();
        repository.salvar(primeira);

        assertEquals(1, consultar.listar(StatusVaga.OCUPADA).size());
        assertEquals(1, consultar.listar(StatusVaga.LIVRE).size());
    }

    @Test
    void buscaPorIdExistente() {
        Vaga salva = cadastrar.executar(Vaga.nova(7, TipoVeiculo.MOTO));

        Vaga encontrada = consultar.buscarPorId(salva.getId());

        assertEquals(7, encontrada.getNumero());
    }

    @Test
    void buscaPorIdInexistenteLancaExcecao() {
        assertThrows(VagaNaoEncontradaException.class,
                () -> consultar.buscarPorId(99L));
    }
}