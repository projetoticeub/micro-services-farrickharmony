package br.com.ferrickharm.agenda.domain.validacao;

import br.com.ferrickharm.agenda.dtos.consulta.DadosAgendamentoConsultaDTO;
import br.com.ferrickharm.agenda.infra.exceptions.ValidacaoException;
import br.com.ferrickharm.agenda.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarProfissionalConsultaMesmoHorario implements Validador{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsultaDTO dados) {
        var profissionalConsultaMesmoHorario = repository.existsByProfissionalDeSaudeIdAndData(dados.idProfissionalDeSaude(),
                dados.data());

        if(profissionalConsultaMesmoHorario) {
            throw new ValidacaoException("Profissional já possui agendamento nesta mesmo horário");
        }
    }

}
