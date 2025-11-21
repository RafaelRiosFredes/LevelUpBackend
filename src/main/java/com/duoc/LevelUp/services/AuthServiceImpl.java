package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.LoginRequestDTO;
import com.duoc.LevelUp.dtos.LoginResponseDTO;
import com.duoc.LevelUp.dtos.RegistroUsuarioDTO;
import com.duoc.LevelUp.dtos.UsuarioResponseDTO;
import com.duoc.LevelUp.models.Rol;
import com.duoc.LevelUp.models.Usuario;
import com.duoc.LevelUp.repositories.RolRepository;
import com.duoc.LevelUp.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UsuarioResponseDTO registrar(RegistroUsuarioDTO dto) {
        int edad = Period.between(dto.getFechaNacimiento(), LocalDate.now()).getYears();
        if (edad < 18) {
            throw new IllegalArgumentException("Debes ser mayor de 18 años para registrarte");
        }

        if (usuarioRepo.existsByCorreoIgnoreCase(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        boolean esDuoc = dto.getCorreo().toLowerCase().endsWith("@duocuc.cl");

        Rol rolUser = rolRepo.findByNombreRol("USER")
                .orElseThrow(() -> new IllegalStateException("Rol USER no existe"));

        Usuario u = new Usuario();
        u.setNombres(dto.getNombres());
        u.setApellidos(dto.getApellidos());
        u.setCorreo(dto.getCorreo());
        u.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        u.setTelefono(dto.getTelefono());
        u.setFechaNacimiento(dto.getFechaNacimiento());
        u.setDuoc(esDuoc);
        u.setDescApl(false);
        u.getRoles().add(rolUser);

        Usuario saved = usuarioRepo.save(u);

        return UsuarioResponseDTO.builder()
                .idUsuario(saved.getIdUsuario())
                .nombres(saved.getNombres())
                .apellidos(saved.getApellidos())
                .correo(saved.getCorreo())
                .telefono(saved.getTelefono())
                .fechaNacimiento(saved.getFechaNacimiento())
                .build();
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        Usuario u = usuarioRepo.findByCorreoIgnoreCase(dto.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!passwordEncoder.matches(dto.getContrasena(), u.getContrasena())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(u); // tu método de generación de token

        return LoginResponseDTO.builder()
                .token(token)
                .username(u.getCorreo())
                .roles(u.getRoles())
                .message("Inicio de sesión exitoso")
                .build();
    }
}