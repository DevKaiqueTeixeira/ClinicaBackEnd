package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.demo.Auth.Login;
import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {

        Optional<Cliente> clienteOpt = clienteRepository.findByCpf(login.getCpf());

        if (clienteOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }

        Cliente cliente = clienteOpt.get();

        if (!passwordEncoder.matches(login.getSenha(), cliente.getSenha())) {
            return ResponseEntity.status(401).body("Senha inválida");
        }

        return ResponseEntity.ok(cliente);
    }
}