package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.ProductoCreateDTO;
import com.duoc.LevelUp.dtos.ProductoImagenResponseDTO;
import com.duoc.LevelUp.dtos.ProductoResponseDTO;
import com.duoc.LevelUp.dtos.ProductoUpdateDTO;
import com.duoc.LevelUp.exceptions.NotFoundException;
import com.duoc.LevelUp.models.Categoria;
import com.duoc.LevelUp.models.Producto;
import com.duoc.LevelUp.repositories.CategoriaRepository;
import com.duoc.LevelUp.repositories.ProductoImagenRepository;
import com.duoc.LevelUp.repositories.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;
    private final ProductoImagenRepository imagenRepo;

    private ProductoResponseDTO toDTO(Producto p){
        return ProductoResponseDTO.builder()
                .idProducto(p.getIdProducto())
                .nombreProducto(p.getNombreProducto())
                .descripcion(p.getDescripcion())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .categoriaId(p.getCategoria().getIdCategoria())
                .categoriaNombre(p.getCategoria().getNombreCategoria())
                .imagenes(p.getImagenes().stream().map(img->
                        ProductoImagenResponseDTO.builder()
                                .idImagen(img.getIdImagen())
                                .url("/api/imagenes/" + img.getIdImagen())
                                .contentType(img.getContentType())
                                .sizeBytes(img.getSizeBytes())
                                .nombreArchivo(img.getNombreArchivo())
                                .build()
                ).toList())
                .build();
    }

    private Producto fromCreate(ProductoCreateDTO d){
        Categoria c = categoriaRepo.findById(d.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoría " + d.getCategoriaId() + " no existe"));
        Producto p = new Producto();
        p.setNombreProducto(d.getNombreProducto());
        p.setDescripcion(d.getDescripcion());
        p.setPrecio(d.getPrecio());
        p.setStock(d.getStock());
        p.setCategoria(c);
        return p;
    }

    private void aplicarUpdate(Producto p, ProductoUpdateDTO d){
        p.setNombreProducto(d.getNombreProducto());
        p.setDescripcion(d.getDescripcion());
        p.setPrecio(d.getPrecio());
        p.setStock(d.getStock());
        Categoria categoria = categoriaRepo.findById(d.getCategoriaId())
                        .orElseThrow(() -> new NotFoundException("Categoría " + d.getCategoriaId() + " no existe"));
        p.setCategoria(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> listar(String nombre, Long categoriaId, Long min, Long max, Pageable pageable) {
        Page<Producto> page;
        if (nombre != null && !nombre.isBlank()) {
            page = productoRepo.findByNombreProductoContainingIgnoreCase(nombre, pageable);
        } else if (categoriaId != null) {
            Categoria categoria = categoriaRepo.findById(categoriaId)
                    .orElseThrow(() -> new NotFoundException("Categoría " + categoriaId + " no existe"));
            page = productoRepo.findByCategoria(categoria, pageable);
        } else if (min != null && max != null) {
            page = productoRepo.findByPrecioBetween(min, max, pageable);
        } else {
            page = productoRepo.findAll(pageable);
        }
        return page.map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtener(Long id) {
        Producto p = productoRepo.findById(id).orElseThrow(() -> new NotFoundException("Producto " + id + " no existe"));
        return toDTO(p);
    }

    @Override
    public ProductoResponseDTO crear(ProductoCreateDTO dto) {
        if (productoRepo.existsByNombreProductoIgnoreCase(dto.getNombreProducto())){

        }
        Producto saved = productoRepo.save(fromCreate(dto));
        return toDTO(saved);
    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoUpdateDTO dto) {
        Producto p = productoRepo.findById(id).orElseThrow(() -> new NotFoundException("Producto " + id + " no existe"));
        aplicarUpdate(p,dto);
        Producto saved = productoRepo.save(p);
        return toDTO(saved);
    }

    @Override
    public void eliminar(Long id) {
        Producto p = productoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto " + id + " no existe"));

        productoRepo.delete(p);
    }
}
