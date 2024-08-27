package br.com.ferrickharm.agenda.dtos.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsultaDTO(@NotNull Long idPaciente,
                                          @NotNull Long idProfissionalDeSaude,
                                          @NotNull @Future LocalDateTime data,
                                          String descricao) {
}
