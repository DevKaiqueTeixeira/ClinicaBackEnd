package com.example.demo.DAO;

import com.example.demo.model.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class EnderecoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Endereco salvar(Endereco endereco) {
        entityManager.createNativeQuery(
                "INSERT INTO enderecos (cep, logradouro, numero, complemento, bairro, cidade, uf, pais, ponto_referencia, tipo_endereco, cliente_id) " +
                        "VALUES (:cep, :logradouro, :numero, :complemento, :bairro, :cidade, :uf, :pais, :pontoReferencia, :tipoEndereco, :clienteId)")
                .setParameter("cep", endereco.getCep())
                .setParameter("logradouro", endereco.getLogradouro())
                .setParameter("numero", endereco.getNumero())
                .setParameter("complemento", endereco.getComplemento())
                .setParameter("bairro", endereco.getBairro())
                .setParameter("cidade", endereco.getCidade())
                .setParameter("uf", endereco.getUf())
                .setParameter("pais", endereco.getPais())
                .setParameter("pontoReferencia", endereco.getPontoReferencia())
                .setParameter("tipoEndereco", endereco.getTipoEndereco())
                .setParameter("clienteId", endereco.getClienteId())
                .executeUpdate();

        return buscarUltimoInserido()
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar endereço salvo"));
    }

    public boolean clienteExiste(Long clienteId) {
        Number total = (Number) entityManager
                .createNativeQuery("SELECT COUNT(1) FROM clientes WHERE id = :clienteId")
                .setParameter("clienteId", clienteId)
                .getSingleResult();

        return total != null && total.longValue() > 0;
    }

    private Optional<Endereco> buscarUltimoInserido() {
        List<Endereco> lista = entityManager
                .createNativeQuery("SELECT * FROM enderecos WHERE id = LAST_INSERT_ID()", Endereco.class)
                .getResultList();

        return lista.stream().findFirst();
    }
}
