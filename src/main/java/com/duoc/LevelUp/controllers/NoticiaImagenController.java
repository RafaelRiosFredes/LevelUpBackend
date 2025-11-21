package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.services.NoticiaImagenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/noticias/imagenes")
@RequiredArgsConstructor
public class NoticiaImagenController {
    private final NoticiaImagenService noticiaImagenService;

    @GetMapping("/{idImagen}")
    public ResponseEntity<byte[]> verImagen(@PathVariable Long idImagen) {
        byte[] data = noticiaImagenService.obtenerDataImagen(idImagen);
        String contentType = noticiaImagenService.obtenerContentType(idImagen);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .cacheControl(CacheControl.noCache())
                .body(data);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idImagen}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idImagen) {
        noticiaImagenService.eliminarImagen(idImagen);
        return ResponseEntity.noContent().build();
    }
}
