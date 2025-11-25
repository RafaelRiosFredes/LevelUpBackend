package com.duoc.LevelUp.security;

import com.duoc.LevelUp.models.Usuario;
import com.duoc.LevelUp.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Importación necesaria

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // Spring inyecta la instancia del repositorio aquí
    private final UsuarioRepository usuarioRepo;

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Usuario usuario = usuarioRepo.findByCorreoConRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new CustomUserDetails(usuario);
    }
}