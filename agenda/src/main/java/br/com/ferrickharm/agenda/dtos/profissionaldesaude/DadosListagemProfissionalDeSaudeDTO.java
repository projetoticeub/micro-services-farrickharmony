package br.com.ferrickharm.agenda.dtos.profissionaldesaude;

import br.com.ferrickharm.agenda.models.ProfissionalDeSaude;

import java.time.LocalDate;

public record DadosListagemProfissionalDeSaudeDTO(Long id,
                                                  String nomeCompleto,
                                                  String cpf,
                                                  LocalDate dataNascimento,
                                                  String email,
                                                  String telefone,
                                                  String registro,
                                                  String genero,
                                                  String cep,
                                                  String endereco) {

    public DadosListagemProfissionalDeSaudeDTO(ProfissionalDeSaude profissionalDeSaude) {
        this(profissionalDeSaude.getId(),
                profissionalDeSaude.getNomeCompleto(),
                profissionalDeSaude.getCpf(),
                profissionalDeSaude.getDataNascimento(),
                profissionalDeSaude.getEmail(),
                profissionalDeSaude.getTelefone(),
                profissionalDeSaude.getRegistro(),
                profissionalDeSaude.getGenero(),
                profissionalDeSaude.getCep(),
                profissionalDeSaude.getEndereco());
    }

}
