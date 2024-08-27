package br.com.ferrickharm.agenda.controllers;

import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosAtualizarProfissionalDeSaudeDTO;
import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosCadastroProfissionalDeSaudeDTO;
import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosDetalhamentoProfissionalDeSaudeDTO;
import br.com.ferrickharm.agenda.dtos.profissionaldesaude.DadosListagemProfissionalDeSaudeDTO;
import br.com.ferrickharm.agenda.services.ProfissionalDeSaudeService;
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

@RestController
@RequestMapping("/profissionais-de-saude")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Profissional De Saúde")
public class ProfissionalDeSaudeController {

    @Autowired
    private ProfissionalDeSaudeService service;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid DadosCadastroProfissionalDeSaudeDTO dados,
                                     UriComponentsBuilder builder) {
        var profissional = service.salvar(dados);
        var uri = builder.path("/profissionais-de-saude/{id}").buildAndExpand(profissional.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoProfissionalDeSaudeDTO(profissional));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemProfissionalDeSaudeDTO>> listar(@PageableDefault(size = 10, sort = {"nomeCompleto"})
    Pageable paginacao) {
        var profissional = service.listarTodos(paginacao).map(DadosListagemProfissionalDeSaudeDTO::new);
        return ResponseEntity.ok(profissional);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id) {
        var profissional = service.listarPorId(id);
        return ResponseEntity.ok().body(profissional);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<DadosListagemProfissionalDeSaudeDTO>> listarAtivos(@PageableDefault(size = 10, sort = {"nomeCompleto"})
                                                                                      Pageable paginacao) {
        var profissional = service.listarAtivos(paginacao).map(DadosListagemProfissionalDeSaudeDTO::new);
        return ResponseEntity.ok(profissional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody DadosAtualizarProfissionalDeSaudeDTO dados) {
        try {
            var profissional = service.atualizar(dados, id);
            return ResponseEntity.ok().body(new DadosDetalhamentoProfissionalDeSaudeDTO(profissional));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            var profissional = service.deletar(id);
            return ResponseEntity.ok().body("Profissional deletado com sucesso! ");
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
