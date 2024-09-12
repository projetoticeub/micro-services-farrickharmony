package br.com.ferrickharm.agenda.controllers;

import br.com.ferrickharm.agenda.dtos.paciente.DadosAtualizarPacienteDTO;
import br.com.ferrickharm.agenda.dtos.paciente.DadosCadastroPacienteDTO;
import br.com.ferrickharm.agenda.dtos.paciente.DadosDetalhamentoPacienteDTO;
import br.com.ferrickharm.agenda.dtos.paciente.DadosListagemPacienteDTO;
import br.com.ferrickharm.agenda.models.Paciente;
import br.com.ferrickharm.agenda.services.PacienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Paciente")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    public ResponseEntity<?> cadastrar (@RequestBody @Valid DadosCadastroPacienteDTO dados, UriComponentsBuilder builder) {
        try {
            var paciente = service.salvar(dados);
            var uri = builder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosDetalhamentoPacienteDTO(paciente));
        }catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

//    @GetMapping
//    public ResponseEntity<Page<DadosListagemPacienteDTO>> listar(@PageableDefault(size = 10, sort = {"nomeCompleto"}) Pageable paginacao) {
//        var paciente = service.listarTodos(paginacao).map(DadosListagemPacienteDTO::new);
//        return ResponseEntity.ok(paciente);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id) {
        Optional<Paciente> pacienteOpt = service.listarPorId(id);
        if(pacienteOpt.isEmpty()){
            return ResponseEntity.badRequest().body("Paciente não encontrado");
        }
        var paciente = pacienteOpt.get();
        return ResponseEntity.ok().body(new DadosDetalhamentoPacienteDTO(paciente));
    }

    @GetMapping("/active")
    public ResponseEntity<Page<DadosListagemPacienteDTO>> listarAtivos(@PageableDefault(size = 10, sort = {"nomeCompleto"}) Pageable paginacao) {
        var paciente = service.listarAtivos(paginacao).map(DadosListagemPacienteDTO::new);
        return ResponseEntity.ok(paciente);
    }

    @GetMapping()
    public ResponseEntity<Page<DadosListagemPacienteDTO>> listarPorParametros (
            @RequestParam(required = false) String nomeCompleto,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) LocalDate dataNascimento,
            @RequestParam(required = false) String telefone,
            Pageable pageable) {
        Page<DadosListagemPacienteDTO> pacientes = service.listarPorParametros(nomeCompleto, cpf, dataNascimento, telefone, pageable);
        return ResponseEntity.ok().body(pacientes);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestBody DadosAtualizarPacienteDTO dados, @PathVariable Long id) {
        try {
            var paciente = service.atualizar(dados, id);
            return ResponseEntity.ok().body(new DadosDetalhamentoPacienteDTO(paciente));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            var paciente = service.deletar(id);
            return ResponseEntity.ok().body("Paciente deletado com sucesso!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
