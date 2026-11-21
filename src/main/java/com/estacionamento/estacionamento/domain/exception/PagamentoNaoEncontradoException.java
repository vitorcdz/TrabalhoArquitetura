package com.estacionamento.estacionamento.domain.exception;

public class PagamentoNaoEncontradoException extends RuntimeException {

    private PagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public static PagamentoNaoEncontradoException porId(Long id) {
        return new PagamentoNaoEncontradoException(
                "Pagamento não encontrado com o id: " + id);
    }

    public static PagamentoNaoEncontradoException porTicket(Long ticketId) {
        return new PagamentoNaoEncontradoException(
                "Nenhum pagamento registrado para o ticket: " + ticketId);
    }
}
