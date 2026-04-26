package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.DAO.ClienteDAO;
import com.example.demo.DAO.PetDAO;
import com.example.demo.model.Pet;

@Service
public class PetService {

    private final PetDAO petDAO;
    private final ClienteDAO clienteDAO;

    public PetService(PetDAO petDAO, ClienteDAO clienteDAO) {
        this.petDAO = petDAO;
        this.clienteDAO = clienteDAO;
    }

    public Pet cadastrar(Pet pet) {
        validarCliente(pet.getClienteId());

        Pet novoPet = new Pet.Builder()
                .nome(pet.getNome())
                .especie(pet.getEspecie())
                .raca(pet.getRaca())
                .sexo(pet.getSexo())
                .dataNascimento(pet.getDataNascimento())
                .peso(pet.getPeso())
                .cor(pet.getCor())
                .porte(pet.getPorte())
                .observacoes(pet.getObservacoes())
                .clienteId(pet.getClienteId())
                .build();

        return petDAO.salvar(novoPet);
    }

    public List<Pet> listarPorClienteId(Long clienteId) {
        validarCliente(clienteId);
        return petDAO.listarPorClienteId(clienteId);
    }

    public Pet buscarPorId(Long id, Long clienteId) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id do pet e obrigatorio");
        }

        validarCliente(clienteId);

        return petDAO.buscarPorIdEClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Pet nao encontrado"));
    }

    public Pet atualizar(Long id, Pet dadosAtualizados) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id do pet e obrigatorio");
        }

        if (dadosAtualizados.getClienteId() == null || dadosAtualizados.getClienteId() <= 0) {
            throw new RuntimeException("ID do cliente e obrigatorio");
        }

        validarCliente(dadosAtualizados.getClienteId());

        Pet petExistente = petDAO.buscarPorIdEClienteId(id, dadosAtualizados.getClienteId())
                .orElseThrow(() -> new RuntimeException("Pet nao encontrado"));

        Pet petAtualizado = new Pet.Builder()
                .id(petExistente.getId())
                .nome(resolveRequired(dadosAtualizados.getNome(), petExistente.getNome()))
                .especie(resolveRequired(dadosAtualizados.getEspecie(), petExistente.getEspecie()))
                .raca(resolveRequired(dadosAtualizados.getRaca(), petExistente.getRaca()))
                .sexo(resolveRequired(dadosAtualizados.getSexo(), petExistente.getSexo()))
                .dataNascimento(dadosAtualizados.getDataNascimento() != null
                        ? dadosAtualizados.getDataNascimento()
                        : petExistente.getDataNascimento())
                .peso(dadosAtualizados.getPeso() != null ? dadosAtualizados.getPeso() : petExistente.getPeso())
                .cor(resolveRequired(dadosAtualizados.getCor(), petExistente.getCor()))
                .porte(resolveRequired(dadosAtualizados.getPorte(), petExistente.getPorte()))
                .observacoes(resolveOptional(dadosAtualizados.getObservacoes(), petExistente.getObservacoes()))
                .clienteId(petExistente.getClienteId())
                .build();

        return petDAO.atualizarCampos(
                petExistente.getId(),
                petExistente.getClienteId(),
                petAtualizado.getNome(),
                petAtualizado.getEspecie(),
                petAtualizado.getRaca(),
                petAtualizado.getSexo(),
                petAtualizado.getDataNascimento(),
                petAtualizado.getPeso(),
                petAtualizado.getCor(),
                petAtualizado.getPorte(),
                petAtualizado.getObservacoes());
    }

    public void excluir(Long id, Long clienteId) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id do pet e obrigatorio");
        }

        validarCliente(clienteId);

        petDAO.excluir(id, clienteId);
    }

    private void validarCliente(Long clienteId) {
        if (clienteId == null || clienteId <= 0) {
            throw new RuntimeException("ID do cliente e obrigatorio");
        }

        if (clienteDAO.buscarPorId(clienteId).isEmpty()) {
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
