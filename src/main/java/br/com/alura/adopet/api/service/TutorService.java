package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDTO;
import br.com.alura.adopet.api.dto.CadastroTutorDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorService {
    @Autowired
    private TutorRepository tutorRepository;

    public void cadastrar(CadastroTutorDTO dto) {
        boolean jaCadastrado = tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email());

        if (jaCadastrado)
            throw new ValidacaoException("Dados já cadastrados para outro tutor!");

        tutorRepository.save(new Tutor(dto));
    }

    public void atualizar(AtualizacaoTutorDTO dto) {
        Tutor tutor = tutorRepository.getReferenceById(dto.id());

        tutor.atualiza(tutor);
    }
}