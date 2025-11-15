package com.duoc.LevelUp.dtos;

import lombok.*;

import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductoResponseDTO {
    private Long idProducto;
    private String nombreProducto;
    private String descripcion;
    private Long precio;
    private Integer stock;
    private Long categoriaId;
    private String categoriaNombre;
    private List<ProductoImagenResponseDTO> imagenes;
}
