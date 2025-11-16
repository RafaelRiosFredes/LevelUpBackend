package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.RegistroUsuarioDTO;
import com.duoc.LevelUp.dtos.UsuarioResponseDTO;

public interface AuthService {
    UsuarioResponseDTO registrar(RegistroUsuarioDTO dto);
}
