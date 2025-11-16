package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.dtos.RegistroUsuarioDTO;
import com.duoc.LevelUp.dtos.UsuarioResponseDTO;
import com.duoc.LevelUp.services.AuthService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody RegistroUsuarioDTO dto){
        UsuarioResponseDTO resp = authService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}
