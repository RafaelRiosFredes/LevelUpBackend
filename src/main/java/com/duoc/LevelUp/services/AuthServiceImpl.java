package com.duoc.LevelUp.services;

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
public class AuthServiceImpl implements AuthService{
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponseDTO registrar(RegistroUsuarioDTO dto){
        // Valida edad >= 18
        int edad = Period.between(dto.getFechaNacimiento(), LocalDate.now()).getYears();
        if(edad < 18){
            throw new IllegalArgumentException("Debes ser mayor de 18 años para registrarte");
        }

        // Valida correo único
        if(usuarioRepo.existsByCorreoIgnoreCase(dto.getCorreo())){
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        // valida si es correo duoc para descuento de 20%
        boolean esDuoc = dto.getCorreo().toLowerCase().endsWith("@duocuc.cl");

        // busca rol user
        Rol rolUser = rolRepo.findByNombreRol("USER")
                .orElseThrow(()-> new IllegalStateException("Rol USER no existe en la BD"));

        // mapeo de DTO -> entidad Usuario
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

        // Armar DTO de respuesta
        return UsuarioResponseDTO.builder()
                .idUsuario(saved.getIdUsuario())
                .nombres(saved.getNombres())
                .apellidos(saved.getApellidos())
                .correo(saved.getCorreo())
                .telefono(saved.getTelefono())
                .fechaNacimiento(saved.getFechaNacimiento())
                .build();
    }
}
