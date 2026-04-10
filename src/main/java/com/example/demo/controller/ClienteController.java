package com.example.demo.controller;

import com.example.demo.model.Cliente;
import com.example.demo.services.ClienteService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Cliente cliente) {
        try {
            Cliente novo = service.salvar(cliente);
            return ResponseEntity.ok(novo);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> user(HttpSession session) {

        Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Não autenticado");
        }

        return ResponseEntity.ok(cliente);
    }
}