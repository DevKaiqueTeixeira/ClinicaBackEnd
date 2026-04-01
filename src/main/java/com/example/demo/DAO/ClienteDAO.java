package com.example.demo.DAO;

import com.example.demo.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        List<Cliente> lista = entityManager
                .createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class)
                .setParameter("cpf", cpf)
                .getResultList();

        return lista.stream().findFirst();
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        List<Cliente> lista = entityManager
                .createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class)
                .setParameter("email", email)
                .getResultList();

        return lista.stream().findFirst();
    }

    public List<Cliente> listarTodos() {
        return entityManager
                .createQuery("SELECT c FROM Cliente c", Cliente.class)
                .getResultList();
    }
}