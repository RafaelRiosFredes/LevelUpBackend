package com.duoc.LevelUp.dtos;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
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
