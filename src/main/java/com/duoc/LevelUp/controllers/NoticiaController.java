package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.dtos.NoticiaCreateDTO;
import com.duoc.LevelUp.dtos.NoticiaImagenCreateDTO;
import com.duoc.LevelUp.dtos.NoticiaImagenResponseDTO;
import com.duoc.LevelUp.dtos.NoticiaResponseDTO;
import com.duoc.LevelUp.services.NoticiaImagenService;
import com.duoc.LevelUp.services.NoticiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/noticias")
@RequiredArgsConstructor
public class NoticiaController {
    private final NoticiaService noticiaService;
    private final NoticiaImagenService noticiaImagenService;

    // Público: listar y ver
    @GetMapping
    public List<NoticiaResponseDTO> listar() {
        return noticiaService.listarTodas();
    }

    @GetMapping("/{id}")
    public NoticiaResponseDTO obtener(@PathVariable Long id) {
        return noticiaService.obtenerPorId(id);
    }

    // Solo ADMIN crea/edita/borra
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<NoticiaResponseDTO> crear(@Valid @RequestBody NoticiaCreateDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noticiaService.crear(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public NoticiaResponseDTO actualizar(@PathVariable Long id,
                                         @Valid @RequestBody NoticiaCreateDTO dto) {
        return noticiaService.actualizar(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        noticiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idNoticia}/imagenes")
    public ResponseEntity<NoticiaImagenResponseDTO> agregarImagen(
            @PathVariable Long idNoticia,
            @Valid @RequestBody NoticiaImagenCreateDTO dto) {

        var resp = noticiaImagenService.agregarImagenBase64(idNoticia, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/{idNoticia}/imagenes")
    public List<NoticiaImagenResponseDTO> listarImagenes(@PathVariable Long idNoticia) {
        return noticiaImagenService.listarPorNoticia(idNoticia);
    }
}
