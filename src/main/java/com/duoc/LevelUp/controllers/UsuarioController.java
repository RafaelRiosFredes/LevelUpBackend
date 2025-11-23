package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.dtos.UsuarioResponseDTO;
import com.duoc.LevelUp.models.Usuario;
import com.duoc.LevelUp.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtener(@PathVariable Long id) {

        Usuario u = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioResponseDTO resp = UsuarioResponseDTO.builder()
                .idUsuario(u.getIdUsuario())
                .nombres(u.getNombres())
                .apellidos(u.getApellidos())
                .correo(u.getCorreo())
                .telefono(u.getTelefono())
                .fechaNacimiento(u.getFechaNacimiento())
                .build();

        return ResponseEntity.ok(resp);
    }
}
