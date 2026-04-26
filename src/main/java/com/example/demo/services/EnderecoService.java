package com.example.demo.services;

import com.example.demo.DAO.EnderecoDAO;
import com.example.demo.model.Endereco;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final EnderecoDAO enderecoDAO;

    public EnderecoService(EnderecoDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
    }

    public Endereco cadastrar(Endereco endereco) {
        validarCliente(endereco.getClienteId());

        Endereco novoEndereco = new Endereco.Builder()
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .pais(endereco.getPais())
                .pontoReferencia(endereco.getPontoReferencia())
                .tipoEndereco(endereco.getTipoEndereco())
                .clienteId(endereco.getClienteId())
                .build();

        return enderecoDAO.salvar(novoEndereco);
    }

    public List<Endereco> listarPorClienteId(Long clienteId) {
        validarCliente(clienteId);
        return enderecoDAO.listarPorClienteId(clienteId);
    }

    public Endereco atualizar(Long id, Endereco dadosAtualizados) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id do endereco e obrigatorio");
        }

        validarCliente(dadosAtualizados.getClienteId());

        Endereco enderecoExistente = enderecoDAO.buscarPorIdEClienteId(id, dadosAtualizados.getClienteId())
                .orElseThrow(() -> new RuntimeException("Endereco nao encontrado"));

        Endereco enderecoAtualizado = new Endereco.Builder()
                .id(enderecoExistente.getId())
                .clienteId(enderecoExistente.getClienteId())
                .cep(resolveRequired(dadosAtualizados.getCep(), enderecoExistente.getCep()))
                .logradouro(resolveRequired(dadosAtualizados.getLogradouro(), enderecoExistente.getLogradouro()))
                .numero(resolveRequired(dadosAtualizados.getNumero(), enderecoExistente.getNumero()))
                .complemento(resolveOptional(dadosAtualizados.getComplemento(), enderecoExistente.getComplemento()))
                .bairro(resolveRequired(dadosAtualizados.getBairro(), enderecoExistente.getBairro()))
                .cidade(resolveRequired(dadosAtualizados.getCidade(), enderecoExistente.getCidade()))
                .uf(resolveRequired(dadosAtualizados.getUf(), enderecoExistente.getUf()))
                .pais(resolveRequired(dadosAtualizados.getPais(), enderecoExistente.getPais()))
                .pontoReferencia(resolveOptional(dadosAtualizados.getPontoReferencia(), enderecoExistente.getPontoReferencia()))
                .tipoEndereco(resolveRequired(dadosAtualizados.getTipoEndereco(), enderecoExistente.getTipoEndereco()))
                .build();

        return enderecoDAO.atualizarCampos(
                enderecoAtualizado.getId(),
                enderecoAtualizado.getClienteId(),
                enderecoAtualizado.getCep(),
                enderecoAtualizado.getLogradouro(),
                enderecoAtualizado.getNumero(),
                enderecoAtualizado.getComplemento(),
                enderecoAtualizado.getBairro(),
                enderecoAtualizado.getCidade(),
                enderecoAtualizado.getUf(),
                enderecoAtualizado.getPais(),
                enderecoAtualizado.getPontoReferencia(),
                enderecoAtualizado.getTipoEndereco());
    }

    public void excluir(Long id, Long clienteId) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id do endereco e obrigatorio");
        }

        validarCliente(clienteId);
        enderecoDAO.excluir(id, clienteId);
    }

    private void validarCliente(Long clienteId) {
        if (clienteId == null || clienteId <= 0) {
            throw new RuntimeException("ID do cliente e obrigatorio");
        }

        if (!enderecoDAO.clienteExiste(clienteId)) {
            throw new RuntimeException("Cliente nao encontrado");
        }
    }

    private String resolveRequired(String value, String fallback) {
        return value != null ? value : fallback;
    }

    private String resolveOptional(String value, String fallback) {
        return value != null ? value : fallback;
    }
}
