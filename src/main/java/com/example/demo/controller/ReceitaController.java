package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Agendamento;
import com.example.demo.model.Receita;
import com.example.demo.services.ReceitaService;

@RestController
@RequestMapping("/receitas")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReceitaController {

    public static class EnviarReceitaRequest {
        private Long agendamentoId;
        private String texto;

        public Long getAgendamentoId() {
            return agendamentoId;
        }

        public void setAgendamentoId(Long agendamentoId) {
            this.agendamentoId = agendamentoId;
        }

        public String getTexto() {
            return texto;
        }

        public void setTexto(String texto) {
            this.texto = texto;
        }
    }

    public static class EnviarReceitaResponse {
        private Receita receita;
        private Agendamento agendamento;

        public EnviarReceitaResponse(Receita receita, Agendamento agendamento) {
            this.receita = receita;
            this.agendamento = agendamento;
        }

        public Receita getReceita() {
            return receita;
        }

        public Agendamento getAgendamento() {
            return agendamento;
        }
    }

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping("/enviar-email")
    public ResponseEntity<?> enviarPorEmail(@RequestBody EnviarReceitaRequest request) {
        try {
            ReceitaService.EnvioReceitaResultado resultado = receitaService
                    .enviarReceitaPorEmail(request.getAgendamentoId(), request.getTexto());

            EnviarReceitaResponse response = new EnviarReceitaResponse(
                    resultado.getReceita(),
                    resultado.getAgendamento());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(mapStatus(e)).body(e.getMessage());
        }
    }

    private HttpStatus mapStatus(RuntimeException exception) {
        String message = exception.getMessage() == null ? "" : exception.getMessage().toLowerCase();

        if (message.contains("nao encontrado")) {
            return HttpStatus.NOT_FOUND;
        }

        if (message.contains("erro ao enviar email") || message.contains("smtp")) {
            return HttpStatus.BAD_GATEWAY;
        }

        return HttpStatus.BAD_REQUEST;
    }
}
