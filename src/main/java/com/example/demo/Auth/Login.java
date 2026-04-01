package com.example.demo.Auth;

public class Login {

    private String email;
    private String senha;

    public Login() {
    }

    private Login(Builder builder) {
        this.email = builder.email;
        this.senha = builder.senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public static class Builder {
        private String email;
        private String senha;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public Login build() {
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Email não pode ser vazio");
            }
            if (senha == null || senha.isEmpty()) {
                throw new IllegalArgumentException("Senha não pode ser vazia");
            }
            return new Login(this);
        }
    }
}