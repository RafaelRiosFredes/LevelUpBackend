package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.dtos.LoginRequestDTO;
import com.duoc.LevelUp.dtos.LoginResponseDTO;
import com.duoc.LevelUp.dtos.RegistroUsuarioDTO;
import com.duoc.LevelUp.dtos.UsuarioResponseDTO;
import com.duoc.LevelUp.services.AuthService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO resp = authService.login(dto);
        return ResponseEntity.ok(resp);
    }
}