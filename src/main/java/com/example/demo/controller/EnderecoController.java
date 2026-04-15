package com.example.demo.controller;

import com.example.demo.model.Endereco;
import com.example.demo.services.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin(origins = "*")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
        try {
            Endereco enderecoSalvo = enderecoService.cadastrar(endereco);
            return ResponseEntity.status(HttpStatus.CREATED).body(enderecoSalvo);
        } catch (RuntimeException e) {
            HttpStatus status = e.getMessage() != null && e.getMessage().contains("não encontrado")
                    ? HttpStatus.NOT_FOUND
                    : HttpStatus.BAD_REQUEST;

            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar endereço: " + e.getMessage());
        }
    }
}
