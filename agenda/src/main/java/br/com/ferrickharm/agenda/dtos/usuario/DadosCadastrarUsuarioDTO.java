package br.com.ferrickharm.agenda.dtos.usuario;

import br.com.ferrickharm.agenda.enums.UsuarioRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastrarUsuarioDTO(@NotBlank String login,
                                       @NotBlank String senha,
                                       @NotNull UsuarioRole role) {



}
