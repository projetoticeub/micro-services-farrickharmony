package br.com.ferrickharm.agenda.dtos.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record DadosCadastroPacienteDTO(@NotBlank String nomeCompleto,
                                       @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
                                       @NotNull LocalDate dataNascimento,
                                       @NotBlank String telefone,
                                       @NotBlank @Email String email,
                                       String genero,
                                       @NotBlank @Pattern(regexp = "\\d{8}") String cep,
                                       @NotBlank String endereco) {
}
