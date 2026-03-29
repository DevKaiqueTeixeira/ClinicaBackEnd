package com.example.demo.services;

import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    private int contarLetras(String senha) {
        int count = 0;

        for (char c : senha.toCharArray()) {
            if (Character.isLetter(c)) {
                count++;
            }
        }

        return count;
    }

    public Cliente salvar(Cliente cliente) {

        if (repository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado");
        }

        if (cliente.getSenha() == null || contarLetras(cliente.getSenha()) < 3) {
            throw new RuntimeException("Senha deve conter pelo menos 3 letras");
        }

        Cliente novoCliente = new Cliente.Builder()
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .senha(passwordEncoder.encode(cliente.getSenha()))
                .telefone(cliente.getTelefone())
                .build();

        return repository.save(novoCliente);
    }

    public Iterable<Cliente> listar() {
        return repository.findAll();
    }
}