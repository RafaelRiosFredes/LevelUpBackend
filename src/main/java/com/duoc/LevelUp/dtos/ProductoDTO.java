package com.duoc.LevelUp.dtos;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ProductoDTO {
    private String nombreProducto;

    private String descripcion;

    private Long precio;

    private Integer stock;

    private String imagenUrl;

    private Long idCategoria;
}
