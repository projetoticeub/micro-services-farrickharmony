package br.com.ferrickharm.agenda.services;

import br.com.ferrickharm.agenda.models.Usuario;
import br.com.ferrickharm.agenda.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    public UserDetails buscarLogin(String login) {
        return repository.findByLogin(login);
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

}
