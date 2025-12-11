package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.OpinionCreateDTO;
import com.duoc.LevelUp.dtos.OpinionResponseDTO;

import java.util.List;

public interface OpinionService {
    OpinionResponseDTO crearOActualizar(Long idProducto, OpinionCreateDTO dto);

    List<OpinionResponseDTO> listarPorProducto(Long idProducto);

    void eliminarOpinionActual(Long idProducto);

    void eliminarOpinionDeUsuario(Long idProducto, Long idUsuario);
}
