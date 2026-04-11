package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.TicketAttachment;
import com.example.demo.model.TicketTimeline;
import com.example.demo.model.TicketTracking;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*")
public class TicketCTL {

        // 🔥 MOCK EM MEMÓRIA
        private final List<TicketTracking> tickets = new ArrayList<>();

        public TicketCTL() {

                // 🔹 Ticket 12341
                TicketTracking t1 = new TicketTracking();
                t1.setId(12341);
                t1.setTitle("Backup automático falhou");
                t1.setStatus("Em Andamento");
                t1.setCreatedAt("10/11/2024");
                t1.setPriority("Crítica");
                t1.setResponsible("Pedro Costa");
                t1.setCategory("Infraestrutura");
                t1.setSlaTotal(90);
                t1.setSlaUsed(45);
                t1.setSlaRemaining("1h 45m");

                List<TicketTimeline> timeline1 = new ArrayList<>();

                TicketTimeline tl1 = new TicketTimeline();
                tl1.setTitle("Falha detectada");
                tl1.setDescription("Backup automático não executou");
                tl1.setAuthor("Sistema");
                tl1.setDate("10/11/2024");

                TicketTimeline tl2 = new TicketTimeline();
                tl2.setTitle("Investigação iniciada");
                tl2.setDescription("Equipe analisando logs");
                tl2.setAuthor("Pedro Costa");
                tl2.setDate("10/11/2024");

                timeline1.add(tl1);
                timeline1.add(tl2);

                t1.setTimeline(timeline1);

                List<TicketAttachment> attachments1 = new ArrayList<>();
                TicketAttachment a1 = new TicketAttachment();
                a1.setFileName("backup-log.txt");

                attachments1.add(a1);

                t1.setAttachments(attachments1);

                tickets.add(t1);

                // 🔹 Ticket 12342
                TicketTracking t2 = new TicketTracking();
                t2.setId(12342);
                t2.setTitle("Sistema atualizado");
                t2.setStatus("Concluído");
                t2.setCreatedAt("10/11/2024");
                t2.setPriority("Alta");
                t2.setResponsible("Kaique Teixeira");
                t2.setCategory("Técnico");
                t2.setSlaTotal(120);
                t2.setSlaUsed(120);
                t2.setSlaRemaining("Concluído");

                t2.setTimeline(new ArrayList<>());
                t2.setAttachments(new ArrayList<>());

                tickets.add(t2);
        }

        // 🔥 ENDPOINT CORRIGIDO
        @GetMapping("/{id}")
        public ResponseEntity<Object> getTicketById(@PathVariable int id) {

                return tickets.stream()
                                .filter(t -> t.getId() == id)
                                .findFirst()
                                .map(ticket -> ResponseEntity.ok((Object) ticket))
                                .orElseGet(() -> ResponseEntity.status(404).body((Object) "Ticket não encontrado"));
        }
}