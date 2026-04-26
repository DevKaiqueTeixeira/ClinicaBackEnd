package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long petId;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(length = 500)
    private String observacoes;

    @Column(nullable = false)
    private String veterinario;

    @Column(nullable = false)
    private String status;

    protected Agendamento() {
    }

    private Agendamento(Builder builder) {
        this.id = builder.id;
        this.petId = builder.petId;
        this.clienteId = builder.clienteId;
        this.tipo = builder.tipo;
        this.data = builder.data;
        this.hora = builder.hora;
        this.observacoes = builder.observacoes;
        this.veterinario = builder.veterinario;
        this.status = builder.status;
    }

    public Long getId() {
        return id;
    }

    public Long getPetId() {
        return petId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public String getStatus() {
        return status;
    }

    public static class Builder {
        private Long id;
        private Long petId;
        private Long clienteId;
        private String tipo;
        private LocalDate data;
        private LocalTime hora;
        private String observacoes;
        private String veterinario;
        private String status;

        public Builder id(Long id) {
            this.id = id;
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

        public Builder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder data(LocalDate data) {
            this.data = data;
            return this;
        }

        public Builder hora(LocalTime hora) {
            this.hora = hora;
            return this;
        }

        public Builder observacoes(String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        public Builder veterinario(String veterinario) {
            this.veterinario = veterinario;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Agendamento build() {
            if (petId == null || petId <= 0) {
                throw new IllegalArgumentException("ID do pet e obrigatorio");
            }

            if (clienteId == null || clienteId <= 0) {
                throw new IllegalArgumentException("ID do cliente e obrigatorio");
            }

            if (tipo == null || tipo.isBlank()) {
                throw new IllegalArgumentException("Tipo do agendamento e obrigatorio");
            }

            if (data == null) {
                throw new IllegalArgumentException("Data do agendamento e obrigatoria");
            }

            if (hora == null) {
                throw new IllegalArgumentException("Hora do agendamento e obrigatoria");
            }

            if (veterinario == null || veterinario.isBlank()) {
                throw new IllegalArgumentException("Veterinario e obrigatorio");
            }

            if (status == null || status.isBlank()) {
                throw new IllegalArgumentException("Status do agendamento e obrigatorio");
            }

            return new Agendamento(this);
        }
    }
}
