package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.Categoria;
import com.duoc.LevelUp.models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    Page<Producto> findByNombreProductoContainingIgnoreCase(String nombre, Pageable pageable);
    Page<Producto> findByCategoria(Categoria categoria, Pageable pageable);
    Page<Producto> findByPrecioBetween(Long min, Long max, Pageable pageable);
    boolean existsByNombreProductoIgnoreCase(String nombreProducto);
}
