package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "receitas")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long agendamentoId;

    @Column(nullable = false)
    private Long petId;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false, length = 4000)
    private String texto;

    @Column(nullable = false)
    private String emailDestino;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    protected Receita() {
    }

    private Receita(Builder builder) {
        this.id = builder.id;
        this.agendamentoId = builder.agendamentoId;
        this.petId = builder.petId;
        this.clienteId = builder.clienteId;
        this.texto = builder.texto;
        this.emailDestino = builder.emailDestino;
        this.criadaEm = builder.criadaEm;
    }

    public Long getId() {
        return id;
    }

    public Long getAgendamentoId() {
        return agendamentoId;
    }

    public Long getPetId() {
        return petId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getTexto() {
        return texto;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public static class Builder {
        private Long id;
        private Long agendamentoId;
        private Long petId;
        private Long clienteId;
        private String texto;
        private String emailDestino;
        private LocalDateTime criadaEm;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder agendamentoId(Long agendamentoId) {
            this.agendamentoId = agendamentoId;
            return this;
        }

        public Builder petId(Long petId) {
            this.petId = petId;
            return this;
        }

        public Builder clienteId(Long clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        public Builder texto(String texto) {
            this.texto = texto;
            return this;
        }

        public Builder emailDestino(String emailDestino) {
            this.emailDestino = emailDestino;
            return this;
        }

        public Builder criadaEm(LocalDateTime criadaEm) {
            this.criadaEm = criadaEm;
            return this;
        }

        public Receita build() {
            if (agendamentoId == null || agendamentoId <= 0) {
                throw new IllegalArgumentException("ID do agendamento e obrigatorio");
            }

            if (petId == null || petId <= 0) {
                throw new IllegalArgumentException("ID do pet e obrigatorio");
            }

            if (clienteId == null || clienteId <= 0) {
                throw new IllegalArgumentException("ID do cliente e obrigatorio");
            }

            if (texto == null || texto.isBlank()) {
                throw new IllegalArgumentException("Texto da receita e obrigatorio");
            }

            if (emailDestino == null || emailDestino.isBlank()) {
                throw new IllegalArgumentException("Email de destino e obrigatorio");
            }

            if (criadaEm == null) {
                throw new IllegalArgumentException("Data de criacao e obrigatoria");
            }

            return new Receita(this);
        }
    }
}
