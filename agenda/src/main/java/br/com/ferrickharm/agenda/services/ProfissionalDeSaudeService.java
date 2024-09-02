package br.com.ferrickharm.agenda.services;

import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosAtualizarProfissionalDeSaudeDTO;
import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosCadastroProfissionalDeSaudeDTO;
import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosListagemProfissionalDeSaudeDTO;
import br.com.ferrickharm.agenda.models.ProfissionalDeSaude;
import br.com.ferrickharm.agenda.repositories.ProfissionalDeSaudeRepository;
import br.com.ferrickharm.agenda.specifications.PacienteSpecification;
import br.com.ferrickharm.agenda.specifications.ProfissionalDeSaudeSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProfissionalDeSaudeService {

   @Autowired
   private ProfissionalDeSaudeRepository repository;

   @Transactional
    public ProfissionalDeSaude salvar(DadosCadastroProfissionalDeSaudeDTO dados) {
        Optional<ProfissionalDeSaude> pacienteOpt = repository.findByCpf(dados.cpf());
        if(pacienteOpt.isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado! ");
        }
        var profissionalDeSaude = new ProfissionalDeSaude(dados);
        return repository.save(profissionalDeSaude);
    }

    public Page<ProfissionalDeSaude> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<ProfissionalDeSaude> listarPorId(Long id) {
       Optional<ProfissionalDeSaude> profissionalDeSaude = repository.findById(id);
       if(profissionalDeSaude.isEmpty()) {
           throw new IllegalArgumentException("Id não encontrado! ");
       }
        return repository.findById(id);
    }

    public Optional<ProfissionalDeSaude> listarPorCpf(String cpf){
        Optional<ProfissionalDeSaude> profissionalDeSaude = repository.findByCpf(cpf);
        if(profissionalDeSaude.isEmpty()) {
            throw new IllegalArgumentException("CPF não encontrado! ");
        }
        return repository.findByCpf(cpf);
    }

    public Page<ProfissionalDeSaude> listarAtivos(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable);
    }

    @Transactional
    public ProfissionalDeSaude atualizar(DadosAtualizarProfissionalDeSaudeDTO dados, Long id) {
       Optional<ProfissionalDeSaude> profissionalDeSaudeOpt = repository.findById(id);
       if(profissionalDeSaudeOpt.isEmpty()) {
           throw new IllegalArgumentException("Id não encontrado! ");
       }
       var profissionalDeSaude = profissionalDeSaudeOpt.get();
       profissionalDeSaude.atualizarInformacoes(dados);
       return repository.save(profissionalDeSaude);
    }

    @Transactional
    public ProfissionalDeSaude deletar(Long id) {
        Optional<ProfissionalDeSaude> profissionalDeSaudeOpt = repository.findById(id);
        if(profissionalDeSaudeOpt.isEmpty()) {
            throw new IllegalArgumentException("Id não encontrado! ");
        }
        var profissionalDeSaude = profissionalDeSaudeOpt.get();
        profissionalDeSaude.deletar();
        return profissionalDeSaude;
    }

    public Page<DadosListagemProfissionalDeSaudeDTO> listarPorParametros(String nomeCompleto, String cpf, LocalDate dataNascimento,
                                                                         String telefone, String registro, Pageable pageable) {
        Specification<ProfissionalDeSaude> spec = ProfissionalDeSaudeSpecification
                .parametros(nomeCompleto, cpf, dataNascimento, telefone, registro);
        return repository.findAll(spec, pageable);
    }

}
