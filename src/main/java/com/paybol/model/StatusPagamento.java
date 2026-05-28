package com.paybol.model;

/**
 * Enum que representa o status de pagamento de um jogador.
 * PAGO - Jogador já efetuou o pagamento
 * PENDENTE - Jogador ainda não pagou
 */
public enum StatusPagamento {

    PAGO("Pago"),
    PENDENTE("Pendente");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
