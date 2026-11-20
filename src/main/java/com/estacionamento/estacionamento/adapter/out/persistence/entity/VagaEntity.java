package com.estacionamento.estacionamento.adapter.out.persistence.entity;

import com.estacionamento.estacionamento.domain.model.StatusVaga;
import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import jakarta.persistence.*;

@Entity
@Table(name = "vagas")
public class VagaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private int numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVeiculo tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVaga status;

    protected VagaEntity() {
    }

    public VagaEntity(Long id, int numero, TipoVeiculo tipo, StatusVaga status) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.status = status;
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