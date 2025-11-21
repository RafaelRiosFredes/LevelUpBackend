package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.*;

public interface AuthService {
    UsuarioResponseDTO registrar(RegistroUsuarioDTO dto);
    LoginResponseDTO login(LoginRequestDTO dto);
}
