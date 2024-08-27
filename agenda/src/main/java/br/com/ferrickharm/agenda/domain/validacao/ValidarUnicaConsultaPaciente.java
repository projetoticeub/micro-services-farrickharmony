package br.com.ferrickharm.agenda.domain.validacao;

import br.com.ferrickharm.agenda.dtos.consulta.DadosAgendamentoConsultaDTO;
import br.com.ferrickharm.agenda.infra.exceptions.ValidacaoException;
import br.com.ferrickharm.agenda.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidarUnicaConsultaPaciente implements Validador{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsultaDTO dados) {
        var primeiroHorario = dados.data().withHour(8).withMinute(0);
        var ultimoHorario = dados.data().withHour(17).withMinute(0);
        var pacienteOutraConsulta = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(),primeiroHorario, ultimoHorario);
        if(pacienteOutraConsulta) {
            throw new ValidacaoException("Paciente  já possui agedamento nesse dia! ");
        }
    }

}
