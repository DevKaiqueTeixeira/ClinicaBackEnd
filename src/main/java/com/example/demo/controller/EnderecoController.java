package com.example.demo.controller;

import com.example.demo.model.Endereco;
import com.example.demo.services.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.ok(enderecoService.listarPorClienteId(clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Endereco endereco) {
        try {
            Endereco enderecoAtualizado = enderecoService.atualizar(id, endereco);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id, @RequestParam Long clienteId) {
        try {
            enderecoService.excluir(id, clienteId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    private HttpStatus mapStatus(RuntimeException exception) {
        String message = exception.getMessage() == null ? "" : exception.getMessage().toLowerCase();

        if (message.contains("nao encontrado")) {
            return HttpStatus.NOT_FOUND;
        }

        return HttpStatus.BAD_REQUEST;
    }
}
