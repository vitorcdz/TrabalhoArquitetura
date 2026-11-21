package com.estacionamento.estacionamento.domain.model;

import com.estacionamento.estacionamento.domain.exception.VagaJaLivreException;
import com.estacionamento.estacionamento.domain.exception.VagaOcupadaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VagaTest {

    @Test
    void vagaNovaNasceLivre() {
        Vaga vaga = Vaga.nova(1, TipoVeiculo.CARRO);

        assertTrue(vaga.estaLivre());
        assertFalse(vaga.isOcupada());
    }

    @Test
    void ocuparMudaOStatusParaOcupada() {
        Vaga vaga = Vaga.nova(1, TipoVeiculo.CARRO);

        vaga.ocupar();

        assertEquals(StatusVaga.OCUPADA, vaga.getStatus());
    }

    @Test
    void naoPermiteOcuparVagaJaOcupada() {
        Vaga vaga = Vaga.nova(1, TipoVeiculo.CARRO);
        vaga.ocupar();

        assertThrows(VagaOcupadaException.class, vaga::ocupar);
    }

    @Test
    void liberarVoltaOStatusParaLivre() {
        Vaga vaga = Vaga.nova(1, TipoVeiculo.CARRO);
        vaga.ocupar();

        vaga.liberar();

        assertTrue(vaga.estaLivre());
    }

    @Test
    void naoPermiteLiberarVagaJaLivre() {
        Vaga vaga = Vaga.nova(1, TipoVeiculo.MOTO);

        assertThrows(VagaJaLivreException.class, vaga::liberar);
    }

    @Test
    void aceitaApenasVeiculoDoMesmoTipo() {
        Vaga vaga = Vaga.nova(1, TipoVeiculo.MOTO);

        assertTrue(vaga.aceita(TipoVeiculo.MOTO));
        assertFalse(vaga.aceita(TipoVeiculo.CARRO));
    }

    @Test
    void rejeitaNumeroInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> Vaga.nova(0, TipoVeiculo.CARRO));
    }

    @Test
    void rejeitaTipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> Vaga.nova(1, null));
    }

    @Test
    void setOcupadaRespeitaAsRegrasDoDominio() {
        Vaga vaga = Vaga.nova(1, TipoVeiculo.CARRO);

        vaga.setOcupada(true);
        assertTrue(vaga.isOcupada());

        assertThrows(VagaOcupadaException.class, () -> vaga.setOcupada(true));

        vaga.setOcupada(false);
        assertTrue(vaga.estaLivre());
    }
}