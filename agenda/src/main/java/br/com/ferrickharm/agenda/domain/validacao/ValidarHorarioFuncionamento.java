package br.com.ferrickharm.agenda.domain.validacao;

import br.com.ferrickharm.agenda.dtos.consulta.DadosAgendamentoConsultaDTO;
import br.com.ferrickharm.agenda.infra.exceptions.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidarHorarioFuncionamento implements Validador {

    public void validar(DadosAgendamentoConsultaDTO dados) {
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioNaoPermitido = dataConsulta.getHour() < 8 || dataConsulta.getHour() > 17;
        if(domingo || horarioNaoPermitido) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento");
        }
    }

}
