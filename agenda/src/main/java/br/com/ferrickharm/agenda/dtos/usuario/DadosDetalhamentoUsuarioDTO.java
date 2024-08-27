package br.com.ferrickharm.agenda.dtos.usuario;

import br.com.ferrickharm.agenda.models.Usuario;

public record DadosDetalhamentoUsuarioDTO(Long id,
                                          String login,
                                          String role,
                                          Boolean ativo) {

    public DadosDetalhamentoUsuarioDTO(Usuario usuario) {
        this(usuario.getId(),
                usuario.getLogin(),
                String.valueOf(usuario.getRole()),
                usuario.getAtivo());
    }
}
