package com.example.demo.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Receita;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ReceitaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Receita salvar(Receita receita) {
        int linhasAfetadas = entityManager.createNativeQuery(
                "INSERT INTO receitas (agendamento_id, pet_id, cliente_id, texto, email_destino, criada_em) " +
                        "VALUES (:agendamentoId, :petId, :clienteId, :texto, :emailDestino, :criadaEm)")
                .setParameter("agendamentoId", receita.getAgendamentoId())
                .setParameter("petId", receita.getPetId())
                .setParameter("clienteId", receita.getClienteId())
                .setParameter("texto", receita.getTexto())
                .setParameter("emailDestino", receita.getEmailDestino())
                .setParameter("criadaEm", receita.getCriadaEm())
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Erro ao salvar receita");
        }

        return buscarUltimaInserida()
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar receita salva"));
    }

    private Optional<Receita> buscarUltimaInserida() {
        List<Receita> lista = entityManager
                .createNativeQuery("SELECT * FROM receitas WHERE id = LAST_INSERT_ID()", Receita.class)
                .getResultList();

        return lista.stream().findFirst();
    }
}
