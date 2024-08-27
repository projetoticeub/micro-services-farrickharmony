package br.com.ferrickharm.agenda.controllers;

import br.com.ferrickharm.agenda.dtos.atenticacao.DadosAutenticacaoDTO;
import br.com.ferrickharm.agenda.dtos.token.DadosTokenJWTDTO;
import br.com.ferrickharm.agenda.dtos.usuario.DadosCadastrarUsuarioDTO;
import br.com.ferrickharm.agenda.dtos.usuario.DadosDetalhamentoUsuarioDTO;
import br.com.ferrickharm.agenda.infra.security.TokenService;
import br.com.ferrickharm.agenda.models.Usuario;
import br.com.ferrickharm.agenda.services.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@RequestBody @Valid DadosAutenticacaoDTO dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(token);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWTDTO(tokenJWT));
    }

    @PostMapping("/registrar")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastrarUsuarioDTO dados) {
        String senhaCodificada = new BCryptPasswordEncoder().encode(dados.senha());
        if(usuarioService.buscarLogin(dados.login()) != null) {
            return ResponseEntity.badRequest().body("Nome de ususário indisponível! ");
        }
        var usuario = new Usuario(dados.login(), senhaCodificada, dados.role());
        usuarioService.salvar(usuario);
        return ResponseEntity.ok().body(new DadosDetalhamentoUsuarioDTO(usuario));
    }

}