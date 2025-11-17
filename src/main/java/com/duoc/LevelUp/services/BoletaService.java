package com.duoc.LevelUp.services;
import com.duoc.LevelUp.dtos.BoletaCreateDTO;
import com.duoc.LevelUp.dtos.BoletaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface BoletaService {
    BoletaResponseDTO crearBoleta(BoletaCreateDTO dto);

    BoletaResponseDTO obtenerPorId(Long id);

    Page<BoletaResponseDTO> listarTodas(Pageable pageable);
}
