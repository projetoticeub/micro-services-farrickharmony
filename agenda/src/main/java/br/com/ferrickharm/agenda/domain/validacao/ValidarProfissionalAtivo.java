package br.com.ferrickharm.agenda.domain.validacao;

import br.com.ferrickharm.agenda.dtos.consulta.DadosAgendamentoConsultaDTO;
import br.com.ferrickharm.agenda.infra.exceptions.ValidacaoException;
import br.com.ferrickharm.agenda.repositories.ProfissionalDeSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarProfissionalAtivo implements Validador{

    @Autowired
    private ProfissionalDeSaudeRepository repository;

    public void validar(DadosAgendamentoConsultaDTO dados) {
        if(dados.idProfissionalDeSaude() == null) {
            return;
        }
        var profissionalAtivo = repository.findAtivoById(dados.idProfissionalDeSaude());
        if(!profissionalAtivo) {
            throw new ValidacaoException("Profissional Indisponível");
        }
    }


}
