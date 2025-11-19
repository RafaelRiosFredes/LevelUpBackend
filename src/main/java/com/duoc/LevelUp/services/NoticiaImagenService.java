package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.NoticiaImagenCreateDTO;
import com.duoc.LevelUp.dtos.NoticiaImagenResponseDTO;

import java.util.List;

public interface NoticiaImagenService {
    NoticiaImagenResponseDTO agregarImagenBase64(Long idNoticia, NoticiaImagenCreateDTO dto);

    List<NoticiaImagenResponseDTO> listarPorNoticia(Long idNoticia);

    String obtenerContentType(Long idImagenNoticia);

    byte[] obtenerDataImagen(Long idImagenNoticia);

    void eliminarImagen(Long idImagenNoticia);
}
