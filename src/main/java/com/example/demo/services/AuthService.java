package com.example.demo.services;

import com.example.demo.Auth.Login;
import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Cliente verificarLogin(Login login) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(login.getEmail());

        if (clienteOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Cliente cliente = clienteOpt.get();

        if (!passwordEncoder.matches(login.getSenha(), cliente.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        return cliente;
    }
}