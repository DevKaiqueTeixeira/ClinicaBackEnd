package com.example.demo.services;

import com.example.demo.DAO.ClienteDAO;
import com.example.demo.model.Cliente;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteDAO clienteDAO;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteDAO clienteDAO, PasswordEncoder passwordEncoder) {
        this.clienteDAO = clienteDAO;
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

        if (clienteDAO.buscarPorCpf(cliente.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado");
        }

        if (clienteDAO.buscarPorEmail(cliente.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
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

        return clienteDAO.salvar(novoCliente);
    }

    public Iterable<Cliente> listar() {
        return clienteDAO.listarTodos();
    }

    public Cliente loginComGoogle(String email, String nome) {

        Optional<Cliente> clienteExistente = clienteDAO.buscarPorEmail(email);

        if (clienteExistente.isPresent()) {
            return clienteExistente.get();
        }

        Cliente novo = new Cliente.Builder()
                .nome(nome)
                .email(email)
                .cpf("GOOGLE_" + email)
                .senha("GOOGLE_LOGIN")
                .telefone(null)
                .build();

        return clienteDAO.salvar(novo);
    }
}