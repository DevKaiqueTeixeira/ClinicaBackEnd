package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String pais;
    private String pontoReferencia;
    private String tipoEndereco;
    private Long clienteId;

    public Endereco() {
    }

    private Endereco(Builder builder) {
        this.id = builder.id;
        this.cep = builder.cep;
        this.logradouro = builder.logradouro;
        this.numero = builder.numero;
        this.complemento = builder.complemento;
        this.bairro = builder.bairro;
        this.cidade = builder.cidade;
        this.uf = builder.uf;
        this.pais = builder.pais;
        this.pontoReferencia = builder.pontoReferencia;
        this.tipoEndereco = builder.tipoEndereco;
        this.clienteId = builder.clienteId;
    }

    public Endereco(
            Long id,
            String cep,
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String uf,
            String pais,
            String pontoReferencia,
            String tipoEndereco,
            Long clienteId) {
        this.id = id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.pais = pais;
        this.pontoReferencia = pontoReferencia;
        this.tipoEndereco = tipoEndereco;
        this.clienteId = clienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public static class Builder {
        private Long id;
        private String cep;
        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
        private String cidade;
        private String uf;
        private String pais;
        private String pontoReferencia;
        private String tipoEndereco;
        private Long clienteId;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder cep(String cep) {
            this.cep = cep;
            return this;
        }

        public Builder logradouro(String logradouro) {
            this.logradouro = logradouro;
            return this;
        }

        public Builder numero(String numero) {
            this.numero = numero;
            return this;
        }

        public Builder complemento(String complemento) {
            this.complemento = complemento;
            return this;
        }

        public Builder bairro(String bairro) {
            this.bairro = bairro;
            return this;
        }

        public Builder cidade(String cidade) {
            this.cidade = cidade;
            return this;
        }

        public Builder uf(String uf) {
            this.uf = uf;
            return this;
        }

        public Builder pais(String pais) {
            this.pais = pais;
            return this;
        }

        public Builder pontoReferencia(String pontoReferencia) {
            this.pontoReferencia = pontoReferencia;
            return this;
        }

        public Builder tipoEndereco(String tipoEndereco) {
            this.tipoEndereco = tipoEndereco;
            return this;
        }

        public Builder clienteId(Long clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        public Endereco build() {
            return new Endereco(this);
        }
    }
}
