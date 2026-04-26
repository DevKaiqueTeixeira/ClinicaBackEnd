package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pet;
import com.example.demo.services.PetService;

@RestController
@RequestMapping("/pets")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Pet pet) {
        try {
            Pet petSalvo = petService.cadastrar(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(petSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.ok(petService.listarPorClienteId(clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, @RequestParam Long clienteId) {
        try {
            return ResponseEntity.ok(petService.buscarPorId(id, clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Pet pet) {
        try {
            Pet petAtualizado = petService.atualizar(id, pet);
            return ResponseEntity.ok(petAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id, @RequestParam Long clienteId) {
        try {
            petService.excluir(id, clienteId);
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
