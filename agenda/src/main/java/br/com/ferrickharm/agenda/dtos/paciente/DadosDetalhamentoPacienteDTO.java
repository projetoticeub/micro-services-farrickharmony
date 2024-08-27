package br.com.ferrickharm.agenda.dtos.paciente;

import br.com.ferrickharm.agenda.models.Paciente;

import java.time.LocalDate;

public record DadosDetalhamentoPacienteDTO(Long id,
                                           String nomeCompleto,
                                           String cpf,
                                           LocalDate dataNascimento,
                                           String telefone,
                                           String email,
                                           String genero,
                                           String cep,
                                           String endereco) {

    public DadosDetalhamentoPacienteDTO(Paciente paciente) {
        this(paciente.getId(),
                paciente.getNomeCompleto(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getTelefone(),
                paciente.getEmail(),
                paciente.getGenero(),
                paciente.getCep(),
                paciente.getEndereco());
    }
}
