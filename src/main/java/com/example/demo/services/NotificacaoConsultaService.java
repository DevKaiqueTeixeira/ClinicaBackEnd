package com.example.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.ClienteDAO;
import com.example.demo.model.Agendamento;
import com.example.demo.model.Cliente;

@Service
public class NotificacaoConsultaService {

    private final ClienteDAO clienteDAO;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    @Value("${spring.mail.password:}")
    private String mailPassword;

    @Value("${app.mail.from:}")
    private String mailFrom;

    public NotificacaoConsultaService(ClienteDAO clienteDAO, JavaMailSender mailSender) {
        this.clienteDAO = clienteDAO;
        this.mailSender = mailSender;
    }

    public void enviarAtualizacaoStatus(Agendamento agendamento, String statusAnterior, String statusNovo) {
        if (statusAnterior == null || statusNovo == null) {
            return;
        }

        if (statusAnterior.trim().equalsIgnoreCase(statusNovo.trim())) {
            return;
        }

        Cliente cliente = clienteDAO.buscarPorId(agendamento.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        String emailDestino = cliente.getEmail();
        if (emailDestino == null || emailDestino.isBlank()) {
            throw new RuntimeException("Cliente sem email cadastrado");
        }

        validarSmtp();

        SimpleMailMessage message = new SimpleMailMessage();

        if (mailFrom != null && !mailFrom.isBlank()) {
            message.setFrom(mailFrom.trim());
        }

        message.setTo(emailDestino.trim());
        message.setSubject("Atualizacao de status da consulta #" + agendamento.getId());
        message.setText(montarMensagemStatus(agendamento, statusAnterior, statusNovo));

        try {
            mailSender.send(message);
        } catch (MailAuthenticationException e) {
            throw new RuntimeException(
                    "Falha de autenticacao SMTP. Para Gmail, use APP_MAIL_PASSWORD com senha de app de 16 caracteres");
        } catch (MailException e) {
            throw new RuntimeException("Erro ao enviar email de status: " + e.getMessage());
        }
    }

    private void validarSmtp() {
        if (mailUsername == null || mailUsername.isBlank() || mailPassword == null || mailPassword.isBlank()) {
            throw new RuntimeException("Configuracao SMTP nao encontrada. Defina APP_MAIL_PASSWORD");
        }
    }

    private String montarMensagemStatus(Agendamento agendamento, String statusAnterior, String statusNovo) {
        StringBuilder body = new StringBuilder();

        body.append("Ola,\n\n");
        body.append("A consulta #").append(agendamento.getId()).append(" teve alteracao de status.\n\n");
        body.append("Status anterior: ").append(mapStatusLabel(statusAnterior)).append("\n");
        body.append("Novo status: ").append(mapStatusLabel(statusNovo)).append("\n\n");
        body.append("Informacoes da consulta:\n");
        body.append("- Tipo: ").append(agendamento.getTipo()).append("\n");
        body.append("- Data: ").append(agendamento.getData()).append("\n");
        body.append("- Hora: ").append(agendamento.getHora()).append("\n");
        body.append("- Veterinario: ").append(agendamento.getVeterinario()).append("\n");

        if (agendamento.getObservacoes() != null && !agendamento.getObservacoes().isBlank()) {
            body.append("- Observacoes: ").append(agendamento.getObservacoes()).append("\n");
        }

        body.append("\nAtenciosamente,\nClinica Veterinaria");
        return body.toString();
    }

    private String mapStatusLabel(String status) {
        if (status == null) {
            return "Pendente";
        }

        String normalized = status.trim().toLowerCase();

        if ("confirmado".equals(normalized)) {
            return "Confirmado";
        }

        if ("concluido".equals(normalized)) {
            return "Concluido";
        }

        if ("cancelado".equals(normalized)) {
            return "Cancelado";
        }

        return "Pendente";
    }
}
