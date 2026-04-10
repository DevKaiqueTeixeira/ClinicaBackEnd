package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Ticket;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

        @GetMapping
        public List<Ticket> getTickets() {
                return List.of(
                                new Ticket(12345, "Sistema lento - Relatório de Desempenho", "Técnico", "Em Andamento",
                                                "schedule", "status-progress", "Alta", "priority-high", "Maria Santos",
                                                "4h 32m",
                                                "10/11/2024 14:30", "2024-11-10T14:30:00"),

                                new Ticket(12344, "Erro na integração de pagamento", "Técnico", "Em Análise",
                                                "pending_actions", "status-review", "Alta", "priority-high",
                                                "João Silva", "2h 15m",
                                                "10/11/2024 16:45", "2024-11-10T16:45:00"),

                                new Ticket(12343, "", "", "", "",
                                                "", "", "", "", "", "09/11/2024 11:00",
                                                "2024-11-09T11:00:00"),

                                new Ticket(12342, "Correção concluída - Sistema atualizado", "Técnico", "Concluído",
                                                "check_circle", "status-completed", "Alta", "priority-high",
                                                "Kaique Teixeira",
                                                "Concluído", "10/11/2024 09:30", "2024-11-10T09:30:00"),

                                new Ticket(12341, "Backup automático falhou", "Infraestrutura", "Em Andamento",
                                                "schedule", "status-progress", "Crítica", "priority-critical",
                                                "Pedro Costa",
                                                "1h 45m", "10/11/2024 17:20", "2024-11-10T17:20:00"),

                                // IDs únicos 👇
                                new Ticket(12340, "Dúvida sobre funcionalidade de relatório", "Suporte", "Aberto",
                                                "info", "status-open", "Baixa", "priority-low", "Unassigned", "18h 10m",
                                                "06/11/2024 14:00", "2024-11-06T14:00:00"),

                                new Ticket(12339, "Dúvida sobre funcionalidade de relatório", "Suporte", "Aberto",
                                                "info", "status-open", "Baixa", "priority-low", "Unassigned", "18h 10m",
                                                "06/11/2024 14:00", "2024-11-06T14:00:00"),

                                new Ticket(12338, "Dúvida sobre funcionalidade de relatório", "Suporte", "Aberto",
                                                "info", "status-open", "Baixa", "priority-low", "Unassigned", "18h 10m",
                                                "06/11/2024 14:00", "2024-11-06T14:00:00"),

                                new Ticket(12337, "Dúvida sobre funcionalidade de relatório", "Suporte", "Aberto",
                                                "info", "status-open", "Baixa", "priority-low", "Unassigned", "18h 10m",
                                                "06/11/2024 14:00", "2024-11-06T14:00:00"),

                                new Ticket(
                                                99999,
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                "",
                                                ""));
        }
}