package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.services.ProductoImagenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImagenController {
    private final ProductoImagenService imagenService;

    @GetMapping("/api/imagenes/{idImagen}")
    public ResponseEntity<byte[]> verImagen(@PathVariable Long idImagen){
        byte[] data = imagenService.obtenerBytes(idImagen);
        String contentType = imagenService.obtenerContentType(idImagen);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .cacheControl(CacheControl.noCache())
                .body(data);
    }

    @DeleteMapping("/api/imagenes/{idImagen}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idImagen){
        imagenService.eliminar(idImagen);
        return ResponseEntity.noContent().build();
    }
}
