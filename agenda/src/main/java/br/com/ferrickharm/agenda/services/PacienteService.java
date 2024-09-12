package br.com.ferrickharm.agenda.services;

import br.com.ferrickharm.agenda.dtos.paciente.DadosAtualizarPacienteDTO;
import br.com.ferrickharm.agenda.dtos.paciente.DadosCadastroPacienteDTO;
import br.com.ferrickharm.agenda.dtos.paciente.DadosListagemPacienteDTO;
import br.com.ferrickharm.agenda.models.Paciente;
import br.com.ferrickharm.agenda.repositories.PacienteRepository;
import br.com.ferrickharm.agenda.specifications.PacienteSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    @Transactional
    public Paciente salvar(DadosCadastroPacienteDTO dados) {
        Optional<Paciente> pacienteOpt = repository.findByCpf(dados.cpf());
        if (pacienteOpt.isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado! ");
        }
        var paciente = new Paciente(dados);
        repository.save(paciente);
        return paciente;
    }

    public Page<Paciente> listarTodos(Pageable paginacao) {
        return repository.findAll(paginacao);
    }

    public Optional<Paciente> listarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Paciente> buscarCpf(String cpf){
        return repository.findByCpf(cpf);
    }

    public Page<Paciente> listarAtivos(Pageable pageable){
        return repository.findAllByAtivoTrue(pageable);
    }

    @Transactional
    public Paciente atualizar(DadosAtualizarPacienteDTO dados, Long id) {
        Optional<Paciente> pacienteOpt = repository.findById(id);
        if(pacienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Id não encontrado! ");
        }
        var paciente = pacienteOpt.get();
        paciente.atualizarInformacoes(dados);
        return repository.save(paciente);
    }

    @Transactional
    public Paciente deletar(Long id){
        Optional<Paciente> pacienteOpt = repository.findById(id);
        if(pacienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Id não encontrado! ");
        }
        var paciente = pacienteOpt.get();
        paciente.deletar();
        return paciente;
    }

    public Page<DadosListagemPacienteDTO> listarPorParametros(String nomeCompleto, String cpf, LocalDate dataNascimento,
                                                              String telefone, Pageable pageable) {
        if(nomeCompleto != null && !nomeCompleto.isEmpty()) {
            nomeCompleto = nomeCompleto.replace("-"," ");
        }
        Specification<Paciente> spec = PacienteSpecification.parametros(nomeCompleto, cpf, dataNascimento, telefone);
        return repository.findAll(spec, pageable).map(DadosListagemPacienteDTO::new);
    }

}
