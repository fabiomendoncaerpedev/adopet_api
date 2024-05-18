package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDTO;
import br.com.alura.adopet.api.dto.CadastroPetDTO;
import br.com.alura.adopet.api.dto.DadosDetalhesPetDTO;
import br.com.alura.adopet.api.dto.DetalhesAbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbrigoService {
    @Autowired
    private AbrigoRepository abrigoRepository;

    public List<DetalhesAbrigoDTO> listarAbrigos() {
        return abrigoRepository.findAll().stream()
                .map(abrigo -> new DetalhesAbrigoDTO(abrigo))
                .toList();
    }

    public void cadastrarAbrigo(CadastroAbrigoDTO dto) {
        boolean jaCadastrado = abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email());

        if (jaCadastrado) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }

        abrigoRepository.save(new Abrigo(dto));
    }

    public List<DadosDetalhesPetDTO> listarPets(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            List<Pet> pets = abrigoRepository.getReferenceById(id).getPets();

            return convertePetsParaDTO(pets);
        } catch (NumberFormatException e) {
            try {
                List<Pet> pets = abrigoRepository.findByNome(idOuNome).getPets();

                return convertePetsParaDTO(pets);
            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException("Nenhum Abrigo encontrado para o ID ou NOME informado!");
            }
        }
    }

    private List<DadosDetalhesPetDTO>convertePetsParaDTO(List<Pet> pets) {
        return pets.stream()
                .map(pet -> new DadosDetalhesPetDTO(
                        pet.getId(),
                        pet.getTipo(),
                        pet.getNome(),
                        pet.getRaca(),
                        pet.getIdade()
                )).collect(Collectors.toList());
    }

    public void cadastrarPet(String idOuNome, CadastroPetDTO dto) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = abrigoRepository.getReferenceById(id);
            abrigo.cadastrarPetNoAbrigo();
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = abrigoRepository.findByNome(idOuNome);
                abrigo.cadastrarPetNoAbrigo();
            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException("Nenhum abrigo encontrado para o ID ou NOME informado!");
            }
        }
    }
}
