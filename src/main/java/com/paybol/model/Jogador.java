package com.paybol.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidade JPA que representa um jogador na lista da pelada.
 * Mapeada para a tabela "jogadores" no banco de dados MySQL.
 *
 * Utiliza anotações JPA (Jakarta Persistence API) para o mapeamento
 * objeto-relacional, que é implementado pelo Hibernate ORM.
 */
@Entity
@Table(name = "jogadores")
public class Jogador implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do jogador (chave primária).
     * Gerado automaticamente pelo banco de dados (auto-increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do jogador.
     */
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    /**
     * Número de telefone/WhatsApp do jogador.
     */
    @Column(name = "telefone", length = 20)
    private String telefone;

    /**
     * Status do pagamento: PAGO ou PENDENTE.
     * Armazenado como String no banco (EnumType.STRING).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", nullable = false)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    /**
     * Valor que o jogador deve pagar pela pelada.
     * Utiliza BigDecimal para precisão monetária.
     */
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    // ========== Construtores ==========

    public Jogador() {
        // Construtor padrão exigido pelo JPA
    }

    public Jogador(String nome, String telefone, StatusPagamento statusPagamento, BigDecimal valor) {
        this.nome = nome;
        this.telefone = telefone;
        this.statusPagamento = statusPagamento;
        this.valor = valor;
    }

    // ========== Getters e Setters ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    // ========== hashCode, equals e toString ==========

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Jogador other = (Jogador) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", statusPagamento=" + statusPagamento +
                ", valor=" + valor +
                '}';
    }
}
