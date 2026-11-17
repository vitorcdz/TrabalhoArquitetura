package com.estacionamento.estacionamento.adapter.out.persistence.entity;

import com.estacionamento.estacionamento.domain.model.TipoVeiculo;
import jakarta.persistence.*;

@Entity
@Table(name = "veiculos")
public class VeiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String cor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVeiculo tipo;

    protected VeiculoEntity() {
    }

    public VeiculoEntity(String placa, String modelo,
                         String cor, TipoVeiculo tipo) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }
}
