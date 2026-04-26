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

import com.example.demo.model.Agendamento;
import com.example.demo.services.AgendamentoService;

@RestController
@RequestMapping("/agendamentos")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AgendamentoController {

    public static class AtualizarStatusRequest {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public ResponseEntity<?> abrir(@RequestBody Agendamento agendamento) {
        try {
            Agendamento novoAgendamento = agendamentoService.abrir(agendamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long clienteId) {
        try {
            return ResponseEntity.ok(agendamentoService.listarPorClienteId(clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(agendamentoService.listarTodos());
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id, @RequestParam Long clienteId) {
        try {
            return ResponseEntity.ok(agendamentoService.buscarPorId(id, clienteId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Agendamento agendamento) {
        try {
            Agendamento agendamentoAtualizado = agendamentoService.atualizar(id, agendamento);
            return ResponseEntity.ok(agendamentoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id, @RequestParam Long clienteId) {
        try {
            agendamentoService.excluir(id, clienteId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest request) {
        try {
            Agendamento agendamentoAtualizado = agendamentoService.atualizarStatus(id, request.getStatus());
            return ResponseEntity.ok(agendamentoAtualizado);
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
