package br.com.ferrickharm.agenda.dtos.profissionaldesaude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record DadosCadastroProfissionalDeSaudeDTO(@NotBlank String nomeCompleto,
                                                  @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
                                                  @NotNull LocalDate dataNascimento,
                                                  @NotBlank @Email String email,
                                                  @NotBlank String telefone,
                                                  @NotBlank @Pattern(regexp = "\\d{4,10}") String registro,
                                                  String genero,
                                                  @NotBlank @Pattern(regexp = "\\d{8}") String cep,
                                                  @NotBlank String endereco) {

}
