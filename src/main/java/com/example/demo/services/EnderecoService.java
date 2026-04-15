package com.example.demo.services;

import com.example.demo.DAO.EnderecoDAO;
import com.example.demo.model.Endereco;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final EnderecoDAO enderecoDAO;

    public EnderecoService(EnderecoDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
    }

    public Endereco cadastrar(Endereco endereco) {
        if (endereco.getClienteId() == null) {
            throw new RuntimeException("clienteId é obrigatório");
        }

        if (!enderecoDAO.clienteExiste(endereco.getClienteId())) {
            throw new RuntimeException("Cliente não encontrado");
        }

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
}
