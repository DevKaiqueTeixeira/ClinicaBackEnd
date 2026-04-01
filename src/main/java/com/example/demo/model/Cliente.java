package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false, unique = true)
	private String cpf;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = true)
	private String telefone;

	protected Cliente() {
	}

	private Cliente(Builder builder) {
		this.nome = builder.nome;
		this.cpf = builder.cpf;
		this.email = builder.email;
		this.senha = builder.senha;
		this.telefone = builder.telefone;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public static class Builder {
		private String nome;
		private String cpf;
		private String email;
		private String senha;
		private String telefone;

		public Builder nome(String nome) {
			this.nome = nome;
			return this;
		}

		public Builder cpf(String cpf) {
			this.cpf = cpf;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder senha(String senha) {
			this.senha = senha;
			return this;
		}

		public Builder telefone(String telefone) {
			this.telefone = telefone;
			return this;
		}

		public Cliente build() {

			if (nome == null || nome.isBlank()) {
				throw new IllegalArgumentException("Nome é obrigatório");
			}

			if (cpf == null || cpf.isBlank()) {
				throw new IllegalArgumentException("CPF é obrigatório");
			}

			if (senha == null || senha.length() < 6) {
				throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");
			}

			return new Cliente(this);
		}
	}
}