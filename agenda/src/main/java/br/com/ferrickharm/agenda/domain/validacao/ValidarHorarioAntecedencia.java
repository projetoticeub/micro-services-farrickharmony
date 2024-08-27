package br.com.ferrickharm.agenda.domain.validacao;

import br.com.ferrickharm.agenda.dtos.consulta.DadosAgendamentoConsultaDTO;
import br.com.ferrickharm.agenda.infra.exceptions.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarHorarioAntecedencia implements Validador{

    public void validar(DadosAgendamentoConsultaDTO dados) {
        var dataConsulta = dados.data();
        var horario = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(horario, dataConsulta).toMinutes();

        if(diferencaEmMinutos < 10) {
            throw new ValidacaoException("Marcar no mínimo com 10 minutos de antecedência! ");
        }
    }

}
