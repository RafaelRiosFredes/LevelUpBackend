package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.ProductoCreateDTO;
import com.duoc.LevelUp.dtos.ProductoResponseDTO;
import com.duoc.LevelUp.dtos.ProductoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {
    Page<ProductoResponseDTO> listar(String nombre, Long categoriaId, Long min, Long max, Pageable pageable);
    ProductoResponseDTO obtener(Long id);
    ProductoResponseDTO crear(ProductoCreateDTO dto);
    ProductoResponseDTO actualizar(Long id, ProductoUpdateDTO dto);

    void eliminar(Long id);
}
