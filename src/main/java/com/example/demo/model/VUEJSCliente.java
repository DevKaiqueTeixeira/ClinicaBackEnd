package com.example.demo.model;

public class VUEJSCliente {

    private String nome;
    private String email;
    private String documento;
    private String telefone;
    private String senha;

    // Construtor vazio (necessário para o @RequestBody)
    public VUEJSCliente() {
    }

    // Construtor completo (opcional)
    public VUEJSCliente(String nome, String email, String documento, String telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.telefone = telefone;
        this.senha = senha;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // toString() para debug
    @Override
    public String toString() {
        return "VUEJSCliente{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", documento='" + documento + '\'' +
                ", telefone='" + telefone + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}