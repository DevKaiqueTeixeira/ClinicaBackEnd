package com.example.demo.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DAO.AgendamentoDAO;
import com.example.demo.DAO.ClienteDAO;
import com.example.demo.DAO.ReceitaDAO;
import com.example.demo.model.Agendamento;
import com.example.demo.model.Cliente;
import com.example.demo.model.Receita;

@Service
public class ReceitaService {

    public static class EnvioReceitaResultado {
        private final Receita receita;
        private final Agendamento agendamento;

        public EnvioReceitaResultado(Receita receita, Agendamento agendamento) {
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

    private final ReceitaDAO receitaDAO;
    private final AgendamentoDAO agendamentoDAO;
    private final ClienteDAO clienteDAO;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    @Value("${spring.mail.password:}")
    private String mailPassword;

    @Value("${app.mail.from:}")
    private String mailFrom;

    public ReceitaService(
            ReceitaDAO receitaDAO,
            AgendamentoDAO agendamentoDAO,
            ClienteDAO clienteDAO,
            JavaMailSender mailSender) {
        this.receitaDAO = receitaDAO;
        this.agendamentoDAO = agendamentoDAO;
        this.clienteDAO = clienteDAO;
        this.mailSender = mailSender;
    }

    @Transactional
    public EnvioReceitaResultado enviarReceitaPorEmail(Long agendamentoId, String texto) {
        if (agendamentoId == null || agendamentoId <= 0) {
            throw new RuntimeException("ID do agendamento e obrigatorio");
        }

        String textoNormalizado = texto == null ? "" : texto.trim();
        if (textoNormalizado.isEmpty()) {
            throw new RuntimeException("Texto da receita e obrigatorio");
        }

        Agendamento agendamento = agendamentoDAO.buscarPorId(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento nao encontrado"));

        Cliente cliente = clienteDAO.buscarPorId(agendamento.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        String emailDestino = cliente.getEmail();
        if (emailDestino == null || emailDestino.isBlank()) {
            throw new RuntimeException("Cliente sem email cadastrado");
        }

        enviarEmail(emailDestino.trim(), agendamento, textoNormalizado);

        Receita receita = new Receita.Builder()
                .agendamentoId(agendamento.getId())
                .petId(agendamento.getPetId())
                .clienteId(agendamento.getClienteId())
                .texto(textoNormalizado)
                .emailDestino(emailDestino.trim())
                .criadaEm(LocalDateTime.now())
                .build();

        Receita receitaSalva = receitaDAO.salvar(receita);
        Agendamento agendamentoAtualizado = agendamentoDAO.atualizarStatus(agendamento.getId(), "concluido");

        return new EnvioReceitaResultado(receitaSalva, agendamentoAtualizado);
    }

    private void enviarEmail(String emailDestino, Agendamento agendamento, String textoReceita) {
        if (mailUsername == null || mailUsername.isBlank() || mailPassword == null || mailPassword.isBlank()) {
            throw new RuntimeException("Configuracao SMTP nao encontrada. Defina APP_MAIL_USERNAME e APP_MAIL_PASSWORD");
        }

        SimpleMailMessage message = new SimpleMailMessage();

        if (mailFrom != null && !mailFrom.isBlank()) {
            message.setFrom(mailFrom.trim());
        }

        message.setTo(emailDestino);
        message.setSubject("Receita medica - Consulta #" + agendamento.getId());
        message.setText(montarCorpoEmail(agendamento, textoReceita));

        try {
            mailSender.send(message);
        } catch (MailAuthenticationException e) {
            throw new RuntimeException(
                    "Falha de autenticacao SMTP. Para Gmail, use APP_MAIL_PASSWORD com senha de app de 16 caracteres");
        } catch (MailException e) {
            throw new RuntimeException("Erro ao enviar email: " + e.getMessage());
        }
    }

    private String montarCorpoEmail(Agendamento agendamento, String textoReceita) {
        StringBuilder body = new StringBuilder();

        body.append("Ola,\n\n");
        body.append("Segue a receita medica referente a consulta #").append(agendamento.getId()).append(".\n\n");
        body.append(textoReceita).append("\n\n");
        body.append("Detalhes da consulta:\n");
        body.append("- Status atual: Concluido\n");
        body.append("- Tipo: ").append(agendamento.getTipo()).append("\n");
        body.append("- Data: ").append(agendamento.getData()).append("\n");
        body.append("- Hora: ").append(agendamento.getHora()).append("\n");
        body.append("- Veterinario: ").append(agendamento.getVeterinario()).append("\n\n");
        body.append("Atenciosamente,\nClinica Veterinaria");

        return body.toString();
    }
}
