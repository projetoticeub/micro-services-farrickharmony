package br.com.ferrickharm.agenda.dtos.consulta;

import br.com.ferrickharm.agenda.models.Consulta;

import java.time.LocalDateTime;

public record DadosListagemConsultaDTO(Long id,
                                       String profissinalDeSaude,
                                       String paciente,
                                       LocalDateTime data) {

    public DadosListagemConsultaDTO(Consulta consulta){
        this(consulta.getId(),
                consulta.getProfissionalDeSaude().getNomeCompleto(),
                consulta.getPaciente().getNomeCompleto(),
                consulta.getData());
    }

}
