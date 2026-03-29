package com.example.demo.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {

    private String rua;
    private String numeroMoradia;
    private String bairro;
    private String cidade;
    private String uf;

    public Endereco() {
    }

    public Endereco(String rua, String numeroMoradia, String bairro, String cidade, String uf) {
        this.rua = rua;
        this.numeroMoradia = numeroMoradia;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumeroMoradia() {
        return numeroMoradia;
    }

    public void setNumeroMoradia(String numeroMoradia) {
        this.numeroMoradia = numeroMoradia;
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
}