package br.com.ferrickharm.agenda.dtos.paciente;

import java.time.LocalDate;

public record DadosAtualizarPacienteDTO(Long id,
                                        String nomeCompleto,
                                        String cpf,
                                        LocalDate dataNascimento,
                                        String telefone,
                                        String email,
                                        String genero,
                                        String cep,
                                        String endereco) {
}
