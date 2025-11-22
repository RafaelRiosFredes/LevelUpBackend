package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.dtos.BoletaCreateDTO;
import com.duoc.LevelUp.dtos.BoletaResponseDTO;
import com.duoc.LevelUp.services.BoletaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/boletas")
@RequiredArgsConstructor
public class BoletaController {
    private final BoletaService boletaService;

    // crear boleta desde carrito
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<BoletaResponseDTO> crear(@Valid @RequestBody BoletaCreateDTO dto){
        BoletaResponseDTO resp = boletaService.crearBoleta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // ver detalle de una boleta por id
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public BoletaResponseDTO obtener(@PathVariable Long id){
        return boletaService.obtenerPorId(id);
    }


    // listar boletas con paginacion (max 20)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<BoletaResponseDTO> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        if(size > 20) size = 20;
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"fechaEmision"));
        return boletaService.listarTodas(pageable);
    }

}
