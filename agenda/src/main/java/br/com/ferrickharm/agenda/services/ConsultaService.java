package br.com.ferrickharm.agenda.services;

import br.com.ferrickharm.agenda.domain.validacao.Validador;
import br.com.ferrickharm.agenda.dtos.consulta.DadosAgendamentoConsultaDTO;
import br.com.ferrickharm.agenda.dtos.consulta.DadosAtualizarConsultaDTO;
import br.com.ferrickharm.agenda.dtos.consulta.DadosDetalhamentoConsultaDTO;
import br.com.ferrickharm.agenda.infra.exceptions.ValidacaoException;
import br.com.ferrickharm.agenda.models.Consulta;
import br.com.ferrickharm.agenda.repositories.ConsultaRepository;
import br.com.ferrickharm.agenda.repositories.PacienteRepository;
import br.com.ferrickharm.agenda.repositories.ProfissionalDeSaudeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProfissionalDeSaudeRepository profissionalDeSaudeRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<Validador> validadores;

    @Transactional
    public DadosDetalhamentoConsultaDTO agendar(DadosAgendamentoConsultaDTO dados){
        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não encontrado!");
        }
        if(!profissionalDeSaudeRepository.existsById(dados.idProfissionalDeSaude())) {
            throw new ValidacaoException("Id do profissional informado não encontrado!");
        }

        validadores.forEach(validador -> validador.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var profissionalDeSaude = profissionalDeSaudeRepository.getReferenceById(dados.idProfissionalDeSaude());
        var consulta = new Consulta(null, profissionalDeSaude, paciente, dados.data(), dados.descricao());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsultaDTO(consulta);
    }

    public Page<Consulta> listarTodos(Pageable pageable) {
        return consultaRepository.findAll(pageable);
    }

    public Optional<Consulta> listarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    @Transactional
    public Consulta alterarData(DadosAtualizarConsultaDTO dados, Long id) {
        Optional<Consulta> consultaOpt = consultaRepository.findById(id);
        if(consultaOpt.isEmpty()) {
            throw new ValidacaoException("Consulta não encontrada para este paciente! ");
        }
        var consulta = consultaOpt.get();
        consulta.atualizarInformacoes(dados);
        return consulta;
    }

    @Transactional
    public void excluir(Long id) {
        Optional<Consulta> consultaOpt = consultaRepository.findById(id);
        if(consultaOpt.isEmpty()) {
            throw new ValidacaoException("Id não encontrado! ");
        }
        consultaRepository.deleteById(id);
    }

}
