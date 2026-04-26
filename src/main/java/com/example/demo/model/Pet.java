package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private String raca;

    @Column(nullable = false)
    private String sexo;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(nullable = false)
    private String cor;

    @Column(nullable = false)
    private String porte;

    @Column(length = 500)
    private String observacoes;

    @Column(nullable = false)
    private Long clienteId;

    protected Pet() {
    }

    private Pet(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.especie = builder.especie;
        this.raca = builder.raca;
        this.sexo = builder.sexo;
        this.dataNascimento = builder.dataNascimento;
        this.peso = builder.peso;
        this.cor = builder.cor;
        this.porte = builder.porte;
        this.observacoes = builder.observacoes;
        this.clienteId = builder.clienteId;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public String getSexo() {
        return sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public String getCor() {
        return cor;
    }

    public String getPorte() {
        return porte;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public static class Builder {
        private Long id;
        private String nome;
        private String especie;
        private String raca;
        private String sexo;
        private LocalDate dataNascimento;
        private BigDecimal peso;
        private String cor;
        private String porte;
        private String observacoes;
        private Long clienteId;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder especie(String especie) {
            this.especie = especie;
            return this;
        }

        public Builder raca(String raca) {
            this.raca = raca;
            return this;
        }

        public Builder sexo(String sexo) {
            this.sexo = sexo;
            return this;
        }

        public Builder dataNascimento(LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
            return this;
        }

        public Builder peso(BigDecimal peso) {
            this.peso = peso;
            return this;
        }

        public Builder cor(String cor) {
            this.cor = cor;
            return this;
        }

        public Builder porte(String porte) {
            this.porte = porte;
            return this;
        }

        public Builder observacoes(String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        public Builder clienteId(Long clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        public Pet build() {
            if (nome == null || nome.isBlank()) {
                throw new IllegalArgumentException("Nome do pet e obrigatorio");
            }

            if (especie == null || especie.isBlank()) {
                throw new IllegalArgumentException("Especie e obrigatoria");
            }

            if (raca == null || raca.isBlank()) {
                throw new IllegalArgumentException("Raca e obrigatoria");
            }

            if (sexo == null || sexo.isBlank()) {
                throw new IllegalArgumentException("Sexo e obrigatorio");
            }

            if (dataNascimento == null) {
                throw new IllegalArgumentException("Data de nascimento e obrigatoria");
            }

            if (peso == null || peso.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Peso deve ser maior que zero");
            }

            if (cor == null || cor.isBlank()) {
                throw new IllegalArgumentException("Cor e obrigatoria");
            }

            if (porte == null || porte.isBlank()) {
                throw new IllegalArgumentException("Porte e obrigatorio");
            }

            if (clienteId == null || clienteId <= 0) {
                throw new IllegalArgumentException("ID do cliente e obrigatorio");
            }

            return new Pet(this);
        }
    }
}
