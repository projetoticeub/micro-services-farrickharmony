package br.com.ferrickharm.agenda.dtos.profissionaldesaude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroProfissionalDeSaudeDTO(@NotBlank String nomeCompleto,
                                                  @NotBlank String cpf,
                                                  @NotNull LocalDate dataNascimento,
                                                  @NotBlank @Email String email,
                                                  @NotBlank String telefone,
                                                  @NotBlank String registro,
                                                  String genero,
                                                  @NotBlank String cep,
                                                  @NotBlank String endereco) {

}
