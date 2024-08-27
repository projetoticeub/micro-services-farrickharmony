package br.com.ferrickharm.agenda.domain.validacao;

import br.com.ferrickharm.agenda.dtos.consulta.DadosAgendamentoConsultaDTO;

public interface Validador {

    void validar(DadosAgendamentoConsultaDTO dados);

}
