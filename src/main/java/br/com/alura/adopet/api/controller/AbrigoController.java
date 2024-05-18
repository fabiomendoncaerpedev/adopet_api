package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDTO;
import br.com.alura.adopet.api.dto.CadastroPetDTO;
import br.com.alura.adopet.api.dto.DadosDetalhesPetDTO;
import br.com.alura.adopet.api.dto.DetalhesAbrigoDTO;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {
    @Autowired
    private AbrigoService abrigoService;

    @GetMapping
    public ResponseEntity<List<DetalhesAbrigoDTO>> listar() {
        List<DetalhesAbrigoDTO> abrigos = abrigoService.listarAbrigos();

        return ResponseEntity.ok(abrigos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroAbrigoDTO dto) {
        try {
            abrigoService.cadastrarAbrigo(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<DadosDetalhesPetDTO>> listarPets(@PathVariable String idOuNome) {
        try {
            List<DadosDetalhesPetDTO> pets = abrigoService.listarPets(idOuNome);

            return ResponseEntity.ok(pets);
        } catch (ValidacaoException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(
            @PathVariable String idOuNome,
            @RequestBody @Valid CadastroPetDTO pet
    ) {
        try {
            abrigoService.cadastrarPet(idOuNome, pet);

            return ResponseEntity.ok().build();
        } catch (ValidacaoException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
