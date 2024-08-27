package br.com.ferrickharm.agenda.dtos.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroPacienteDTO(@NotBlank String nomeCompleto,
                                       @NotBlank String cpf,
                                       @NotNull LocalDate dataNascimento,
                                       @NotBlank String telefone,
                                       @NotBlank @Email String email,
                                       String genero,
                                       @NotBlank String cep,
                                       @NotBlank String endereco) {
}
