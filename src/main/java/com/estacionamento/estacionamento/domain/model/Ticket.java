package com.estacionamento.estacionamento.domain.model;

import java.time.LocalDateTime;

public class Ticket {

    private Long id;
    private String placa;
    private Long vagaId;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private String status;

    public Ticket() {
    }

    public Ticket(String placa, Long vagaId) {
        this.placa = placa;
        this.vagaId = vagaId;
        this.entrada = LocalDateTime.now();
        this.status = "ATIVO";
    }

    public void finalizar() {
        if (!"ATIVO".equals(status)) {
            throw new IllegalStateException(
                    "Ticket ja finalizado"
            );
        }

        this.saida = LocalDateTime.now();
        this.status = "FINALIZADO";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getVagaId() {
        return vagaId;
    }

    public void setVagaId(Long vagaId) {
        this.vagaId = vagaId;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}