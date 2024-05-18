package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Abrigo;

public record DetalhesAbrigoDTO(
    Long id,
    String nome,
    String telefone,
    String email
) {
    public DetalhesAbrigoDTO(Abrigo abrigo) {
        this(abrigo.getId(), abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail());
    }
}
