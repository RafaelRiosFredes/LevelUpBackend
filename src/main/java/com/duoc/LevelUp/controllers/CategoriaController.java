package com.duoc.LevelUp.controllers;

import com.duoc.LevelUp.models.Categoria;
import com.duoc.LevelUp.repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepo;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepo.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        if (categoriaRepo.existsByNombreCategoriaIgnoreCase(categoria.getNombreCategoria())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }

        Categoria saved = categoriaRepo.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
