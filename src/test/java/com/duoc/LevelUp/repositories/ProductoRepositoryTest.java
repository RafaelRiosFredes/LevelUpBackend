package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.Categoria;
import com.duoc.LevelUp.models.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductoRepositoryTest {
    @Autowired ProductoRepository productoRepo;

    @Autowired
    private CategoriaRepository categoriaRepo;

    @Test
    void guardarYLeer(){
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria("Consolas");
        categoria = categoriaRepo.save(categoria);

        Producto p = new Producto();
        p.setNombreProducto("PS5");
        p.setDescripcion("Consola PS5");
        p.setPrecio(549990L);
        p.setStock(5);
        p.setCategoria(categoria);

        Producto saved = productoRepo.save(p);

        Optional<Producto> encontradoOpt = productoRepo.findById(saved.getIdProducto());

        assertThat(encontradoOpt).isPresent();
        Producto encontrado = encontradoOpt.get();

        assertThat(encontrado.getNombreProducto()).isEqualTo("PS5");
        assertThat(encontrado.getCategoria()).isNotNull();
        assertThat(encontrado.getCategoria().getNombreCategoria()).isEqualTo("Consolas");
        assertThat(encontrado.getStock()).isEqualTo(5);

    }
}
