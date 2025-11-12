package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.ProductoImagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoImagenRepository extends JpaRepository<ProductoImagen,Long> {
    List<ProductoImagen> findByProducto_idProducto(Long idProducto);
}
