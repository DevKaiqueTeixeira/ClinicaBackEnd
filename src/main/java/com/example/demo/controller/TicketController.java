package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Ticket;
import com.example.demo.repository.TicketRepository;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

        private final TicketRepository repository;

        public TicketController(TicketRepository repository) {
                this.repository = repository;
        }

        @GetMapping
        public List<Ticket> getTickets() {
                return repository.findAll();
        }
}