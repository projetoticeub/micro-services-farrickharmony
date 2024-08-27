package br.com.ferrickharm.agenda.dtos.consulta;

import br.com.ferrickharm.agenda.models.Consulta;
import br.com.ferrickharm.agenda.models.Paciente;
import br.com.ferrickharm.agenda.models.ProfissionalDeSaude;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsultaDTO(Long id,
                                           ProfissionalDeSaude profissionalDeSaude,
                                           Paciente paciente,
                                           LocalDateTime data,
                                           String descricao) {

    public DadosDetalhamentoConsultaDTO(Consulta consulta){
        this(consulta.getId(),
                consulta.getProfissionalDeSaude(),
                consulta.getPaciente(),
                consulta.getData(),
                consulta.getDescricao());
    }

}
