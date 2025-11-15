package com.duoc.LevelUp.services;

import com.duoc.LevelUp.dtos.ProductoImagenCreateDTO;
import com.duoc.LevelUp.dtos.ProductoImagenResponseDTO;
import com.duoc.LevelUp.exceptions.NotFoundException;
import com.duoc.LevelUp.models.Producto;
import com.duoc.LevelUp.models.ProductoImagen;
import com.duoc.LevelUp.repositories.ProductoImagenRepository;
import com.duoc.LevelUp.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class ProductoImagenServiceImpl implements ProductoImagenService{
    private final ProductoRepository productoRepo;
    private final ProductoImagenRepository imagenRepo;

    @Override
    public ProductoImagenResponseDTO agregarImagenBase64(Long idProducto, ProductoImagenCreateDTO dto){
        Producto producto = productoRepo.findById(idProducto)
                .orElseThrow(() -> new NotFoundException("Producto " + idProducto + " no existe"));
        byte[] bytes = Base64.getDecoder().decode(dto.getBase64());
        ProductoImagen img = new ProductoImagen();
        img.setProducto(producto);
        img.setNombreArchivo(dto.getNombreArchivo());
        img.setContentType(dto.getContentType());
        img.setSizeBytes((long) bytes.length);
        img.setData(bytes);

        ProductoImagen saved = imagenRepo.save(img);

        return ProductoImagenResponseDTO.builder()
                .idImagen(saved.getIdImagen())
                .url("/api/imagenes/" + saved.getIdImagen())
                .contentType(saved.getContentType())
                .sizeBytes(saved.getSizeBytes())
                .nombreArchivo(saved.getNombreArchivo())
                .build();
    }

    @Override @Transactional(readOnly = true)
    public byte[] obtenerBytes(Long idImagen){
        ProductoImagen img = imagenRepo.findById(idImagen)
                .orElseThrow(() -> new NotFoundException("Imagen " + idImagen + " no existe"));
        return img.getData();
    }

    @Override @Transactional(readOnly = true)
    public String obtenerContentType(Long idImagen){
        return imagenRepo.findById(idImagen)
                .orElseThrow(() -> new NotFoundException("Imagen " + idImagen + " no existe"))
                .getContentType();
    }

    @Override @Transactional(readOnly = true)
    public List<ProductoImagenResponseDTO> listarPorProducto(Long idProducto){
        return imagenRepo.findByProducto_IdProducto(idProducto).stream().map(img ->
                ProductoImagenResponseDTO.builder()
                        .idImagen(img.getIdImagen())
                        .url("/api/imagenes/" + img.getIdImagen())
                        .contentType(img.getContentType())
                        .sizeBytes(img.getSizeBytes())
                        .nombreArchivo(img.getNombreArchivo())
                        .build()
        ).toList();
    }

    @Override
    public void eliminar(Long idImagen){
        if (!imagenRepo.existsById(idImagen)) throw new NotFoundException("Imagen " + idImagen + " no existe");
        imagenRepo.deleteById(idImagen);
    }
}
