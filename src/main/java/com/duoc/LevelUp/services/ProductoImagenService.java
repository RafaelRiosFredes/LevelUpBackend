package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.ProductoImagenCreateDTO;
import com.duoc.LevelUp.dtos.ProductoImagenResponseDTO;

import java.util.List;

public interface ProductoImagenService {
    ProductoImagenResponseDTO agregarImagenBase64(Long idProducto, ProductoImagenCreateDTO dto);
    byte[] obtenerBytes(Long idImagen);
    String obtenerContentType(Long idImagen);
    List<ProductoImagenResponseDTO> listarPorProducto(Long idProducto);
    void eliminar(Long idImagen);
}
