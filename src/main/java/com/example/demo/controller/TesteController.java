package com.example.demo.controller;

import com.example.demo.model.VUEJSCliente;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:9000", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.OPTIONS }, allowCredentials = "true")
@RequestMapping("/vueTJ")
public class TesteController {

    @PostMapping("/cadastro")
    public ResponseEntity<?> receberDados(@RequestBody VUEJSCliente cliente) {
        System.out.println("Dados recebidos: " + cliente);
        return ResponseEntity.ok(Map.of("message", "Cadastro realizado com sucesso, backend message!"));
    }
}