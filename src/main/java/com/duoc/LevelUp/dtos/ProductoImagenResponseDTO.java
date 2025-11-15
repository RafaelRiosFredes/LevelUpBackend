package com.duoc.LevelUp.dtos;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductoImagenResponseDTO {
    private Long idImagen;
    private String url;
    private String contentType;
    private Long sizeBytes;
    private String nombreArchivo;
}
