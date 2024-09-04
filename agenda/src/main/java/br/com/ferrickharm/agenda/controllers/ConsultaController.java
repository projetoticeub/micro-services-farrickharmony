package br.com.ferrickharm.agenda.controllers;

import br.com.ferrickharm.agenda.dtos.consulta.DadosAgendamentoConsultaDTO;
import br.com.ferrickharm.agenda.dtos.consulta.DadosAtualizarConsultaDTO;
import br.com.ferrickharm.agenda.dtos.consulta.DadosDetalhamentoConsultaDTO;
import br.com.ferrickharm.agenda.dtos.consulta.DadosListagemConsultaDTO;
import br.com.ferrickharm.agenda.infra.exceptions.ValidacaoException;
import br.com.ferrickharm.agenda.services.ConsultaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/consultas")
@Tag(name = "Consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody @Valid DadosAgendamentoConsultaDTO dados) {
        try{
            var consulta = service.agendar(dados);
            return ResponseEntity.ok(consulta);

        } catch (ValidacaoException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsultaDTO>> listar(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var consulta = service.listarTodos(paginacao).map(DadosListagemConsultaDTO::new);
        return ResponseEntity.ok().body(consulta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id) {
        var consulta = service.listarPorId(id);
        return ResponseEntity.ok().body(consulta);
    }

    @GetMapping("/param")
    public ResponseEntity<Page<DadosListagemConsultaDTO>> listarPorParametros(
            @RequestParam(required = false) Long profissionalDeSaudeId,
            @RequestParam(required = false) Long pacienteId,
            @RequestParam(required = false) LocalDateTime data,
            @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {

        var consultas = service.listarPorParametros(profissionalDeSaudeId, pacienteId, data, paginacao);
        return ResponseEntity.ok(consultas);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestBody DadosAtualizarConsultaDTO dados, @PathVariable Long id) {
        try {
            var consulta = service.alterarData(dados, id);
            return ResponseEntity.ok().body(new DadosDetalhamentoConsultaDTO(consulta));
        } catch (ValidacaoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.ok().body("Consulta excluída com sucesso! ");
        } catch (ValidacaoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


}
