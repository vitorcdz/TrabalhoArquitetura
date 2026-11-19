package com.estacionamento.estacionamento.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pagamento {

    private Long id;
    private Long ticketId;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;
    private String formaPagamento;

    public Pagamento() {
    }

    public Pagamento(Long ticketId, BigDecimal valor, String formaPagamento) {
        this.ticketId = ticketId;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.dataPagamento = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
