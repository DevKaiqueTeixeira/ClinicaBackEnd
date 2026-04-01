package com.example.demo.controller;

import com.example.demo.model.Cliente;
import com.example.demo.services.ClienteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
    public ResponseEntity<?> user(OAuth2AuthenticationToken auth) {

        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autenticado");
        }

        String email = auth.getPrincipal().getAttribute("email");
        String nome = auth.getPrincipal().getAttribute("name");

        Cliente cliente = service.loginComGoogle(email, nome);

        return ResponseEntity.ok(cliente);
    }

}