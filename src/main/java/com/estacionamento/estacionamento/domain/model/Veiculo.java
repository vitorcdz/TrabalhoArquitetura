package com.estacionamento.estacionamento.domain.model;

public class Veiculo {
    private Long id;
    private String placa;
    private String modelo;
    private String cor;
    private TipoVeiculo tipo;


    public Veiculo(Long id, String placa, String modelo, String cor, TipoVeiculo tipo) {

        if(placa == null || placa.isBlank()){
            throw new IllegalArgumentException("A placa é obrigatória");
        }

        if(modelo == null || modelo.isBlank()){
            throw new IllegalArgumentException("O modelo é obrigatório");
        }

        if(cor == null || cor.isBlank()){
            throw new IllegalArgumentException("A cor é obrigatória");
        }

        if(tipo == null){
            throw new IllegalArgumentException("O tipo é obrigatório");
        }

        this.id = id;
        this.placa = placa.toUpperCase().trim();
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
