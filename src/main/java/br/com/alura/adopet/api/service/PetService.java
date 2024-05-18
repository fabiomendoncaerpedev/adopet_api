package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DadosDetalhesPetDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PetService {
    @Autowired
    private PetRepository petRepository;

    public List<DadosDetalhesPetDTO> listarTodosDisponiveis() {
        List<Pet> pets = petRepository.findAllByAdotado(false);
        List<DadosDetalhesPetDTO> dadosDetalhesPets = new ArrayList<>();

        pets.forEach(pet -> dadosDetalhesPets.add(new DadosDetalhesPetDTO(pet)));

        if (dadosDetalhesPets.isEmpty()) {
            throw new ValidacaoException("Nenhum Pet foi encontrado!");
        }

        return dadosDetalhesPets;
    }
}
