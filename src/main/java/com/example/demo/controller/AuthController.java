package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.demo.Auth.Login;
import com.example.demo.model.Cliente;
import com.example.demo.services.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginData) {

        Login login = new Login.Builder()
                .email(loginData.getEmail())
                .senha(loginData.getSenha())
                .build();

        try {
            Cliente cliente = authService.verificarLogin(login);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg.contains("não encontrado")) {
                return ResponseEntity.status(404).body(msg);
            }
            return ResponseEntity.status(401).body(msg);
        }
    }
}