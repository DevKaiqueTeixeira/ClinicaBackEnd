package com.example.demo.DAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Agendamento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AgendamentoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Agendamento salvar(Agendamento agendamento) {
        int linhasAfetadas = entityManager.createNativeQuery(
                "INSERT INTO agendamentos (pet_id, cliente_id, tipo, data, hora, observacoes, veterinario, status) " +
                        "VALUES (:petId, :clienteId, :tipo, :data, :hora, :observacoes, :veterinario, :status)")
                .setParameter("petId", agendamento.getPetId())
                .setParameter("clienteId", agendamento.getClienteId())
                .setParameter("tipo", agendamento.getTipo())
                .setParameter("data", agendamento.getData())
                .setParameter("hora", agendamento.getHora())
                .setParameter("observacoes", agendamento.getObservacoes())
                .setParameter("veterinario", agendamento.getVeterinario())
                .setParameter("status", agendamento.getStatus())
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Erro ao salvar agendamento");
        }

        return buscarUltimoInserido()
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar agendamento salvo"));
    }

    public List<Agendamento> listarPorClienteId(Long clienteId) {
        return entityManager
                .createNativeQuery("SELECT * FROM agendamentos WHERE cliente_id = :clienteId ORDER BY data ASC, hora ASC",
                        Agendamento.class)
                .setParameter("clienteId", clienteId)
                .getResultList();
    }

    public List<Agendamento> listarTodos() {
        return entityManager
                .createNativeQuery("SELECT * FROM agendamentos ORDER BY data ASC, hora ASC", Agendamento.class)
                .getResultList();
    }

    public Optional<Agendamento> buscarPorId(Long id) {
        List<Agendamento> lista = entityManager
                .createNativeQuery("SELECT * FROM agendamentos WHERE id = :id LIMIT 1", Agendamento.class)
                .setParameter("id", id)
                .getResultList();

        return lista.stream().findFirst();
    }

    public Optional<Agendamento> buscarPorIdEClienteId(Long id, Long clienteId) {
        List<Agendamento> lista = entityManager
                .createNativeQuery("SELECT * FROM agendamentos WHERE id = :id AND cliente_id = :clienteId LIMIT 1",
                        Agendamento.class)
                .setParameter("id", id)
                .setParameter("clienteId", clienteId)
                .getResultList();

        return lista.stream().findFirst();
    }

    @Transactional
    public Agendamento atualizarCampos(Long id,
            Long clienteId,
            Long petId,
            String tipo,
            LocalDate data,
            LocalTime hora,
            String observacoes,
            String veterinario,
            String status) {

        int linhasAfetadas = entityManager.createNativeQuery(
                "UPDATE agendamentos " +
                        "SET pet_id = :petId, tipo = :tipo, data = :data, hora = :hora, observacoes = :observacoes, veterinario = :veterinario, status = :status " +
                        "WHERE id = :id AND cliente_id = :clienteId")
                .setParameter("id", id)
                .setParameter("clienteId", clienteId)
                .setParameter("petId", petId)
                .setParameter("tipo", tipo)
                .setParameter("data", data)
                .setParameter("hora", hora)
                .setParameter("observacoes", observacoes)
                .setParameter("veterinario", veterinario)
                .setParameter("status", status)
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Agendamento nao encontrado");
        }

        return buscarPorIdEClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar agendamento atualizado"));
    }

    @Transactional
    public void excluir(Long id, Long clienteId) {
        int linhasAfetadas = entityManager
                .createNativeQuery("DELETE FROM agendamentos WHERE id = :id AND cliente_id = :clienteId")
                .setParameter("id", id)
                .setParameter("clienteId", clienteId)
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Agendamento nao encontrado");
        }
    }

    @Transactional
    public Agendamento atualizarStatus(Long id, String status) {
        int linhasAfetadas = entityManager
                .createNativeQuery("UPDATE agendamentos SET status = :status WHERE id = :id")
                .setParameter("id", id)
                .setParameter("status", status)
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Agendamento nao encontrado");
        }

        return buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar agendamento atualizado"));
    }

    private Optional<Agendamento> buscarUltimoInserido() {
        List<Agendamento> lista = entityManager
                .createNativeQuery("SELECT * FROM agendamentos WHERE id = LAST_INSERT_ID()", Agendamento.class)
                .getResultList();

        return lista.stream().findFirst();
    }
}
