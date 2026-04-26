package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DAO.AgendamentoDAO;
import com.example.demo.DAO.ClienteDAO;
import com.example.demo.DAO.PetDAO;
import com.example.demo.model.Agendamento;

@Service
public class AgendamentoService {

    private final AgendamentoDAO agendamentoDAO;
    private final ClienteDAO clienteDAO;
    private final PetDAO petDAO;
    private final NotificacaoConsultaService notificacaoConsultaService;

    public AgendamentoService(
            AgendamentoDAO agendamentoDAO,
            ClienteDAO clienteDAO,
            PetDAO petDAO,
            NotificacaoConsultaService notificacaoConsultaService) {
        this.agendamentoDAO = agendamentoDAO;
        this.clienteDAO = clienteDAO;
        this.petDAO = petDAO;
        this.notificacaoConsultaService = notificacaoConsultaService;
    }

    public Agendamento abrir(Agendamento agendamento) {
        validarCliente(agendamento.getClienteId());
        validarPetDoCliente(agendamento.getPetId(), agendamento.getClienteId());

        Agendamento novoAgendamento = new Agendamento.Builder()
                .petId(agendamento.getPetId())
                .clienteId(agendamento.getClienteId())
                .tipo(agendamento.getTipo())
                .data(agendamento.getData())
                .hora(agendamento.getHora())
                .observacoes(agendamento.getObservacoes())
                .veterinario(resolveVeterinario(agendamento.getVeterinario(), "A definir"))
                .status(normalizeStatus(agendamento.getStatus()))
                .build();

        return agendamentoDAO.salvar(novoAgendamento);
    }

    public List<Agendamento> listarPorClienteId(Long clienteId) {
        validarCliente(clienteId);
        return agendamentoDAO.listarPorClienteId(clienteId);
    }

    public List<Agendamento> listarTodos() {
        return agendamentoDAO.listarTodos();
    }

    public Agendamento buscarPorId(Long id, Long clienteId) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id do agendamento e obrigatorio");
        }

        validarCliente(clienteId);

        return agendamentoDAO.buscarPorIdEClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Agendamento nao encontrado"));
    }

    @Transactional
    public Agendamento atualizar(Long id, Agendamento dadosAtualizados) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id do agendamento e obrigatorio");
        }

        if (dadosAtualizados.getClienteId() == null || dadosAtualizados.getClienteId() <= 0) {
            throw new RuntimeException("ID do cliente e obrigatorio");
        }

        validarCliente(dadosAtualizados.getClienteId());

        Agendamento agendamentoExistente = agendamentoDAO.buscarPorIdEClienteId(id, dadosAtualizados.getClienteId())
                .orElseThrow(() -> new RuntimeException("Agendamento nao encontrado"));

        Long petIdAtualizado = dadosAtualizados.getPetId() != null
                ? dadosAtualizados.getPetId()
                : agendamentoExistente.getPetId();

        validarPetDoCliente(petIdAtualizado, agendamentoExistente.getClienteId());

        String statusAtualizado = dadosAtualizados.getStatus() != null
                ? normalizeStatus(dadosAtualizados.getStatus())
                : normalizeStatus(agendamentoExistente.getStatus());

        String statusAnterior = normalizeStatus(agendamentoExistente.getStatus());

        Agendamento agendamentoAtualizado = new Agendamento.Builder()
                .id(agendamentoExistente.getId())
                .petId(petIdAtualizado)
                .clienteId(agendamentoExistente.getClienteId())
                .tipo(resolveRequired(dadosAtualizados.getTipo(), agendamentoExistente.getTipo()))
                .data(dadosAtualizados.getData() != null ? dadosAtualizados.getData() : agendamentoExistente.getData())
                .hora(dadosAtualizados.getHora() != null ? dadosAtualizados.getHora() : agendamentoExistente.getHora())
                .observacoes(resolveOptional(dadosAtualizados.getObservacoes(), agendamentoExistente.getObservacoes()))
                .veterinario(resolveVeterinario(dadosAtualizados.getVeterinario(), agendamentoExistente.getVeterinario()))
                .status(statusAtualizado)
                .build();

        Agendamento agendamentoPersistido = agendamentoDAO.atualizarCampos(
                agendamentoAtualizado.getId(),
                agendamentoAtualizado.getClienteId(),
                agendamentoAtualizado.getPetId(),
                agendamentoAtualizado.getTipo(),
                agendamentoAtualizado.getData(),
                agendamentoAtualizado.getHora(),
                agendamentoAtualizado.getObservacoes(),
                agendamentoAtualizado.getVeterinario(),
                agendamentoAtualizado.getStatus());

        if (!statusAnterior.equals(statusAtualizado)) {
            notificacaoConsultaService.enviarAtualizacaoStatus(
                    agendamentoPersistido,
                    statusAnterior,
                    statusAtualizado);
        }

        return agendamentoPersistido;
    }

    public void excluir(Long id, Long clienteId) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id do agendamento e obrigatorio");
        }

        validarCliente(clienteId);
        agendamentoDAO.excluir(id, clienteId);
    }

    @Transactional
    public Agendamento atualizarStatus(Long id, String status) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id do agendamento e obrigatorio");
        }

        String statusNormalizado = normalizeStatus(status);

        Agendamento agendamentoExistente = agendamentoDAO.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Agendamento nao encontrado"));

        String statusAnterior = normalizeStatus(agendamentoExistente.getStatus());

        if (statusAnterior.equals(statusNormalizado)) {
            return agendamentoExistente;
        }

        Agendamento agendamentoAtualizado = agendamentoDAO.atualizarStatus(id, statusNormalizado);

        notificacaoConsultaService.enviarAtualizacaoStatus(
                agendamentoAtualizado,
                statusAnterior,
                statusNormalizado);

        return agendamentoAtualizado;
    }

    private void validarCliente(Long clienteId) {
        if (clienteId == null || clienteId <= 0) {
            throw new RuntimeException("ID do cliente e obrigatorio");
        }

        if (clienteDAO.buscarPorId(clienteId).isEmpty()) {
            throw new RuntimeException("Cliente nao encontrado");
        }
    }

    private void validarPetDoCliente(Long petId, Long clienteId) {
        if (petId == null || petId <= 0) {
            throw new RuntimeException("ID do pet e obrigatorio");
        }

        if (petDAO.buscarPorIdEClienteId(petId, clienteId).isEmpty()) {
            throw new RuntimeException("Pet nao encontrado para este cliente");
        }
    }

    private String normalizeStatus(String status) {
        if (status == null || status.isBlank()) {
            return "pendente";
        }

        String normalized = status.trim().toLowerCase();

        if ("pendente".equals(normalized)
                || "confirmado".equals(normalized)
                || "concluido".equals(normalized)
                || "cancelado".equals(normalized)) {
            return normalized;
        }

        throw new RuntimeException("Status invalido");
    }

    private String resolveRequired(String value, String fallback) {
        return value != null ? value : fallback;
    }

    private String resolveOptional(String value, String fallback) {
        return value != null ? value : fallback;
    }

    private String resolveVeterinario(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }

        return value.trim();
    }
}
