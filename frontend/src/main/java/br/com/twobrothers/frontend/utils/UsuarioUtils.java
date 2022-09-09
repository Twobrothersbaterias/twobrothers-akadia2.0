package br.com.twobrothers.frontend.utils;

import br.com.twobrothers.frontend.models.entities.user.UsuarioEntity;
import br.com.twobrothers.frontend.repositories.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UsuarioUtils {

    public static UsuarioEntity loggedUser(UsuarioRepository usuarioRepository){

        Object logado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nomeDeUsuario;

        if (logado instanceof UserDetails) nomeDeUsuario = ((UserDetails)logado).getUsername();
        else nomeDeUsuario = logado.toString();

        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findByNomeUsuario(nomeDeUsuario);

        if (usuarioOptional.isPresent()) return usuarioOptional.get();
        throw new UsernameNotFoundException("Usuário não encontrado: Classe UsuarioUtils");

    }
}
