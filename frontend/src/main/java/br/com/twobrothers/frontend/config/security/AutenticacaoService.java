package br.com.twobrothers.frontend.config.security;

import br.com.twobrothers.frontend.models.entities.user.UsuarioEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuario = repository.findByNomeUsuario(username);
        if (usuario.isPresent()) return usuario.get();
        throw new UsernameNotFoundException("Usuário não encontrado");
    }

}
