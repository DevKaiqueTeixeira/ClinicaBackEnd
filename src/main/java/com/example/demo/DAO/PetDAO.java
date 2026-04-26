package com.example.demo.DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Pet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PetDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Pet salvar(Pet pet) {
        int linhasAfetadas = entityManager.createNativeQuery(
                "INSERT INTO pets (nome, especie, raca, sexo, data_nascimento, peso, cor, porte, observacoes, cliente_id) " +
                        "VALUES (:nome, :especie, :raca, :sexo, :dataNascimento, :peso, :cor, :porte, :observacoes, :clienteId)")
                .setParameter("nome", pet.getNome())
                .setParameter("especie", pet.getEspecie())
                .setParameter("raca", pet.getRaca())
                .setParameter("sexo", pet.getSexo())
                .setParameter("dataNascimento", pet.getDataNascimento())
                .setParameter("peso", pet.getPeso())
                .setParameter("cor", pet.getCor())
                .setParameter("porte", pet.getPorte())
                .setParameter("observacoes", pet.getObservacoes())
                .setParameter("clienteId", pet.getClienteId())
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Erro ao salvar pet");
        }

        return buscarUltimoInserido()
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar pet salvo"));
    }

    public List<Pet> listarPorClienteId(Long clienteId) {
        return entityManager
                .createNativeQuery("SELECT * FROM pets WHERE cliente_id = :clienteId ORDER BY nome ASC", Pet.class)
                .setParameter("clienteId", clienteId)
                .getResultList();
    }

    public Optional<Pet> buscarPorIdEClienteId(Long id, Long clienteId) {
        List<Pet> lista = entityManager
                .createNativeQuery("SELECT * FROM pets WHERE id = :id AND cliente_id = :clienteId LIMIT 1", Pet.class)
                .setParameter("id", id)
                .setParameter("clienteId", clienteId)
                .getResultList();

        return lista.stream().findFirst();
    }

    @Transactional
    public Pet atualizarCampos(Long id,
            Long clienteId,
            String nome,
            String especie,
            String raca,
            String sexo,
            LocalDate dataNascimento,
            BigDecimal peso,
            String cor,
            String porte,
            String observacoes) {

        int linhasAfetadas = entityManager.createNativeQuery(
                "UPDATE pets " +
                        "SET nome = :nome, especie = :especie, raca = :raca, sexo = :sexo, data_nascimento = :dataNascimento, " +
                        "peso = :peso, cor = :cor, porte = :porte, observacoes = :observacoes " +
                        "WHERE id = :id AND cliente_id = :clienteId")
                .setParameter("id", id)
                .setParameter("clienteId", clienteId)
                .setParameter("nome", nome)
                .setParameter("especie", especie)
                .setParameter("raca", raca)
                .setParameter("sexo", sexo)
                .setParameter("dataNascimento", dataNascimento)
                .setParameter("peso", peso)
                .setParameter("cor", cor)
                .setParameter("porte", porte)
                .setParameter("observacoes", observacoes)
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Pet nao encontrado");
        }

        return buscarPorIdEClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar pet atualizado"));
    }

    @Transactional
    public void excluir(Long id, Long clienteId) {
        int linhasAfetadas = entityManager
                .createNativeQuery("DELETE FROM pets WHERE id = :id AND cliente_id = :clienteId")
                .setParameter("id", id)
                .setParameter("clienteId", clienteId)
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Pet nao encontrado");
        }
    }

    private Optional<Pet> buscarUltimoInserido() {
        List<Pet> lista = entityManager
                .createNativeQuery("SELECT * FROM pets WHERE id = LAST_INSERT_ID()", Pet.class)
                .getResultList();

        return lista.stream().findFirst();
    }
}
