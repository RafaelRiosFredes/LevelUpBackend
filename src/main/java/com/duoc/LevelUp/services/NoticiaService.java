package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.NoticiaCreateDTO;
import com.duoc.LevelUp.dtos.NoticiaResponseDTO;

import java.util.List;

public interface NoticiaService {
    NoticiaResponseDTO crear(NoticiaCreateDTO dto);

    NoticiaResponseDTO actualizar(Long id, NoticiaCreateDTO dto);

    void eliminar(Long id);

    NoticiaResponseDTO obtenerPorId(Long id);

    List<NoticiaResponseDTO> listarTodas();
}
