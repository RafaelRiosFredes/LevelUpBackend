package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.dtos.*;
import com.duoc.LevelUp.services.ProductoImagenService;
import com.duoc.LevelUp.services.ProductoService;
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

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService service;
    private final ProductoImagenService imagenService;

    //público
    @GetMapping
    public Page<ProductoResponseDTO> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long idCategoria,
            @RequestParam(required = false) Long minPrecio,
            @RequestParam(required = false) Long maxPrecio,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "idProducto,asc") String sort
    ){
        String[] s = sort.split(",");
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.fromString(s[1]), s[0]));
        return service.listar(nombre,idCategoria,minPrecio,maxPrecio,pageable);
    }

    @GetMapping("/{id}")
    public ProductoResponseDTO obtener(@PathVariable Long id){
        return service.obtener(id);
    }

    //solo admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoCreateDTO dto){
        ProductoResponseDTO creado = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductoResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody ProductoUpdateDTO dto){
        return service.actualizar(id,dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{idProducto}/imagenes")
    public ResponseEntity<ProductoImagenResponseDTO> agregarImagen(
            @PathVariable Long idProducto,
            @Valid @RequestBody ProductoImagenCreateDTO dto){
        var resp = imagenService.agregarImagenBase64(idProducto,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    //público
    @GetMapping("/{idProducto}/imagenes")
    public List<ProductoImagenResponseDTO> listarImagenes(@PathVariable Long idProducto){
        return imagenService.listarPorProducto(idProducto);
    }

}
