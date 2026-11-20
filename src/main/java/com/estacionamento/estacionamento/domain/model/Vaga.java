package com.estacionamento.estacionamento.domain.model;

import com.estacionamento.estacionamento.domain.exception.VagaJaLivreException;
import com.estacionamento.estacionamento.domain.exception.VagaOcupadaException;

public class Vaga {

    private Long id;
    private int numero;
    private TipoVeiculo tipo;
    private StatusVaga status;

    public Vaga(Long id, int numero, TipoVeiculo tipo, StatusVaga status) {

        if (numero <= 0) {
            throw new IllegalArgumentException("O número da vaga deve ser maior que zero");
        }

        if (tipo == null) {
            throw new IllegalArgumentException("O tipo é obrigatório");
        }

        if (status == null) {
            throw new IllegalArgumentException("O status é obrigatório");
        }

        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.status = status;
    }

    // Toda vaga nova nasce livre.
    public static Vaga nova(int numero, TipoVeiculo tipo) {
        return new Vaga(null, numero, tipo, StatusVaga.LIVRE);
    }

    public void ocupar() {
        if (status == StatusVaga.OCUPADA) {
            throw new VagaOcupadaException(numero);
        }
        this.status = StatusVaga.OCUPADA;
    }

    public void liberar() {
        if (status == StatusVaga.LIVRE) {
            throw new VagaJaLivreException(numero);
        }
        this.status = StatusVaga.LIVRE;
    }

    // Regra de compatibilidade: carro só em vaga de carro, moto só em vaga de moto.
    public boolean aceita(TipoVeiculo tipoVeiculo) {
        return this.tipo == tipoVeiculo;
    }

    public boolean estaLivre() {
        return status == StatusVaga.LIVRE;
    }

    public boolean isOcupada() {
        return status == StatusVaga.OCUPADA;
    }

    @Deprecated
    public void setOcupada(boolean ocupada) {
        if (ocupada) {
            ocupar();
        } else {
            liberar();
        }
    }

    public Long getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    public StatusVaga getStatus() {
        return status;
    }
}