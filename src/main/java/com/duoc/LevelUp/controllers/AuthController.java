package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.dtos.RegistroUsuarioDTO;
import com.duoc.LevelUp.dtos.UsuarioResponseDTO;
import com.duoc.LevelUp.services.AuthService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody RegistroUsuarioDTO dto){
        UsuarioResponseDTO resp = authService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"message\":\"Use POST /api/v1/auth/login para autenticarse\"}");
    }

}