package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.dtos.OpinionCreateDTO;
import com.duoc.LevelUp.dtos.OpinionResponseDTO;
import com.duoc.LevelUp.services.OpinionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos/{idProducto}/opiniones")
@RequiredArgsConstructor
public class OpinionController {
    private final OpinionService opinionService;

    // Público: ver opiniones de un producto
    @GetMapping
    public List<OpinionResponseDTO> listar(@PathVariable Long idProducto) {
        return opinionService.listarPorProducto(idProducto);
    }

    // Usuario logueado crea o actualiza su opinión
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<OpinionResponseDTO> crearOActualizar(
            @PathVariable Long idProducto,
            @Valid @RequestBody OpinionCreateDTO dto
    ) {
        OpinionResponseDTO resp = opinionService.crearOActualizar(idProducto, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // Usuario logueado elimina su opinión
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> eliminarOpinionActual(@PathVariable Long idProducto) {
        opinionService.eliminarOpinionActual(idProducto);
        return ResponseEntity.noContent().build();
    }

    // Admin elimina la opinión de otro usuario (moderación)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/usuario/{idUsuario}")
    public ResponseEntity<Void> eliminarOpinionDeUsuario(
            @PathVariable Long idProducto,
            @PathVariable Long idUsuario
    ) {
        opinionService.eliminarOpinionDeUsuario(idProducto, idUsuario);
        return ResponseEntity.noContent().build();
    }
}
