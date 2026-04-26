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

    public List<Endereco> listarPorClienteId(Long clienteId) {
        return entityManager
                .createNativeQuery("SELECT * FROM enderecos WHERE cliente_id = :clienteId ORDER BY id ASC", Endereco.class)
                .setParameter("clienteId", clienteId)
                .getResultList();
    }

    public Optional<Endereco> buscarPorIdEClienteId(Long id, Long clienteId) {
        List<Endereco> lista = entityManager
                .createNativeQuery("SELECT * FROM enderecos WHERE id = :id AND cliente_id = :clienteId LIMIT 1", Endereco.class)
                .setParameter("id", id)
                .setParameter("clienteId", clienteId)
                .getResultList();

        return lista.stream().findFirst();
    }

    @Transactional
    public Endereco atualizarCampos(Long id,
            Long clienteId,
            String cep,
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String uf,
            String pais,
            String pontoReferencia,
            String tipoEndereco) {

        int linhasAfetadas = entityManager
                .createNativeQuery("UPDATE enderecos " +
                        "SET cep = :cep, logradouro = :logradouro, numero = :numero, complemento = :complemento, " +
                        "bairro = :bairro, cidade = :cidade, uf = :uf, pais = :pais, ponto_referencia = :pontoReferencia, " +
                        "tipo_endereco = :tipoEndereco WHERE id = :id AND cliente_id = :clienteId")
                .setParameter("id", id)
                .setParameter("clienteId", clienteId)
                .setParameter("cep", cep)
                .setParameter("logradouro", logradouro)
                .setParameter("numero", numero)
                .setParameter("complemento", complemento)
                .setParameter("bairro", bairro)
                .setParameter("cidade", cidade)
                .setParameter("uf", uf)
                .setParameter("pais", pais)
                .setParameter("pontoReferencia", pontoReferencia)
                .setParameter("tipoEndereco", tipoEndereco)
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Endereco nao encontrado");
        }

        return buscarPorIdEClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Erro ao recuperar endereco atualizado"));
    }

    @Transactional
    public void excluir(Long id, Long clienteId) {
        int linhasAfetadas = entityManager
                .createNativeQuery("DELETE FROM enderecos WHERE id = :id AND cliente_id = :clienteId")
                .setParameter("id", id)
                .setParameter("clienteId", clienteId)
                .executeUpdate();

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Endereco nao encontrado");
        }
    }

    private Optional<Endereco> buscarUltimoInserido() {
        List<Endereco> lista = entityManager
                .createNativeQuery("SELECT * FROM enderecos WHERE id = LAST_INSERT_ID()", Endereco.class)
                .getResultList();

        return lista.stream().findFirst();
    }
}
