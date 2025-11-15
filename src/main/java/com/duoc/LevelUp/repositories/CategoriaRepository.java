package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    boolean existsByNombreCategoriaIgnoreCase(String nombreCategoria);
}
