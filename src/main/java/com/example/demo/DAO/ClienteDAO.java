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
        int linhasAfetadas = entityManager.createNativeQuery(
                "INSERT INTO clientes (nome, cpf, email, senha, telefone) VALUES (:nome, :cpf, :email, :senha, :telefone)")
                .setParameter("nome", cliente.getNome())
                .setParameter("cpf", cliente.getCpf())
                .setParameter("email", cliente.getEmail())
                .setParameter("senha", cliente.getSenha())
                .setParameter("telefone", cliente.getTelefone())
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Erro ao salvar cliente");
        }

        return buscarPorEmail(cliente.getEmail())
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar cliente salvo"));
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        List<Cliente> lista = entityManager
                .createNativeQuery("SELECT * FROM clientes WHERE cpf = :cpf LIMIT 1", Cliente.class)
                .setParameter("cpf", cpf)
                .getResultList();

        return lista.stream().findFirst();
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        List<Cliente> lista = entityManager
                .createNativeQuery("SELECT * FROM clientes WHERE email = :email LIMIT 1", Cliente.class)
                .setParameter("email", email)
                .getResultList();

        return lista.stream().findFirst();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        List<Cliente> lista = entityManager
                .createNativeQuery("SELECT * FROM clientes WHERE id = :id LIMIT 1", Cliente.class)
                .setParameter("id", id)
                .getResultList();

        return lista.stream().findFirst();
    }

    public boolean existeCpfParaOutroId(String cpf, Long id) {
        Number total = (Number) entityManager
                .createNativeQuery("SELECT COUNT(1) FROM clientes WHERE cpf = :cpf AND id <> :id")
                .setParameter("cpf", cpf)
                .setParameter("id", id)
                .getSingleResult();

        return total != null && total.longValue() > 0;
    }

    @Transactional
    public Cliente atualizarCampos(Long id, String nome, String cpf, String telefone) {
        int linhasAfetadas = entityManager
                .createNativeQuery("UPDATE clientes SET nome = :nome, cpf = :cpf, telefone = :telefone WHERE id = :id")
                .setParameter("id", id)
                .setParameter("nome", nome)
                .setParameter("cpf", cpf)
                .setParameter("telefone", telefone)
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Cliente não encontrado");
        }

        return buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar cliente atualizado"));
    }

    public List<Cliente> listarTodos() {
        return entityManager
                .createNativeQuery("SELECT * FROM clientes", Cliente.class)
                .getResultList();
    }
}
