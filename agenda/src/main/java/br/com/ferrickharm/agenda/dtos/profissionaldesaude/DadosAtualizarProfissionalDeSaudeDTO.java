package br.com.ferrickharm.agenda.dtos.profissionaldesaude;

import java.time.LocalDate;

public record DadosAtualizarProfissionalDeSaudeDTO(String nomeCompleto,
                                                   String cpf,
                                                   LocalDate dataNascimento,
                                                   String email,
                                                   String telefone,
                                                   String registro,
                                                   String genero,
                                                   String cep,
                                                   String endereco) {
}
